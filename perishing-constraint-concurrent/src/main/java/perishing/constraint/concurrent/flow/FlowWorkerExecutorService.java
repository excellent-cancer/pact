package perishing.constraint.concurrent.flow;

import lombok.EqualsAndHashCode;
import perishing.constraint.concurrent.AbstractWorker;
import perishing.constraint.concurrent.WorkerExecutorService;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static perishing.constraint.concurrent.flow.Constants.COUNT_MAST;

public class FlowWorkerExecutorService extends AbstractService implements WorkerExecutorService {

    private final BlockingQueue<Runnable> taskQueue;

    private final HashSet<Worker> workers = new HashSet<>();

    public FlowWorkerExecutorService(FlowExecutorIndustry industry, BlockingQueue<Runnable> taskQueue) {
        super(industry);
        this.taskQueue = taskQueue;
    }

    @Override
    public BlockingQueue<Runnable> tasks() {
        return taskQueue;
    }

    @Override
    public boolean removeTask(Runnable task) {
        boolean removed = taskQueue.remove(task);
        tryTerminate();
        return removed;
    }

    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;

        w.firstTask = null;
        w.unlock();

        boolean completedAbruptly = true;

        try {
            while (task != null || (task = getTask()) != null) {
                ControlDetails details = current();
                if ((details.isRefuseToProcessTask() || (Thread.interrupted() && details.isRefuseToProcessTask())) && !wt.isInterrupted()) {
                    wt.interrupt();
                }

                try {
                    // TODO Call hooks
                    try {

                        task.run();
                        // TODO Call hooks
                    } catch (Throwable ex) {
                        // TODO Call hooks
                        throw ex;
                    }
                } finally {
                    task = null;
                    w.competedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }

    private Runnable getTask() {
        boolean inTime = true;

        for (; ; ) {
            ControlDetails details = current();
            if (details.isRefuseToReceiveTask()) {
                if (details.isRefuseToProcessTask() || taskQueue.isEmpty()) {
                    details.control().reduceWorker();
                    return null;
                }
            }

            int workerCount = details.workers();
            boolean allowTimeout = options().isAllowCoreThreadTimeOut() || workerCount > options().getCorePoolSize();
            boolean overMaximumPoolSizeOrTimeout = workerCount > options().getMaximumPoolSize() || (allowTimeout && !inTime);

            if (overMaximumPoolSizeOrTimeout) {
                if (workerCount > 1 || taskQueue.isEmpty()) {
                    if (details.tryReduceWorker()) {
                        return null;
                    }
                    continue;
                }
            }

            try {
                if (allowTimeout) {
                    Runnable r = taskQueue.poll(options().getKeepAliveTime(), options().getUnit());
                    if (inTime = (r != null)) {
                        return r;
                    }
                } else {
                    return taskQueue.take();
                }
            } catch (InterruptedException e) {
                inTime = false;
            }
        }
    }

    @Override
    public boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (ControlDetails details = current(); ; ) {

            // 如果
            // 1. 线程池不再接收任务 并且 线程池不再处理任务
            // 2. 线程池不再接收任务 但是 线程池可以继续处理任务 并且 有要处理的任务（因为不再接收任务）
            // 3. 线程池不再接收任务 但是 线程池可以继续处理任务 并且 任务队列为空 （没有需要继续处理的任务，因为不接受任务，但是可以创建线程处理剩下的任务）
            if (details.isRefuseToReceiveTask() && (details.isRefuseToProcessTask() || firstTask != null || taskQueue.isEmpty())) {
                return false;
            }

            for (; ; ) {
                // 判断worker数量是否可以增加
                if (details.workers() >= ((core ? options().getCorePoolSize() : options().getMaximumPoolSize()) & COUNT_MAST)) {
                    return false;
                }

                if (details.tryAddWorker()) {
                    break retry;
                }

                // 如果现在还可以继续接收任务，则继续尝试添加worker
                if ((details = current()).isRefuseToReceiveTask()) {
                    continue retry;
                }
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {
            w = new Worker(firstTask);
            final Thread t = w.thread;
            if (t != null) {
                final ReentrantLock mainLock = industry.mainLock;
                mainLock.lock();
                try {
                    ControlDetails details = current();

                    // 这里要求可以继续处理任务
                    if (details.isContinueToProcessTask()) {

                        // 如果任务不为空，则需要可以继续接收任务，反之则无所谓
                        if (firstTask == null || details.isContinueToReceiveTask()) {
                            if (t.isAlive()) {
                                throw new IllegalStateException();
                            }

                            workers.add(w);
                            metrics().poolSizeChanged(workers.size());
                            workerAdded = true;
                        }
                    }
                } finally {
                    mainLock.unlock();
                }
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {
            if (!workerStarted) {
                addWorkerFailed(w);
            }
        }
        return workerStarted;
    }

    private void addWorkerFailed(Worker worker) {
        final ReentrantLock mainLock = industry.mainLock;
        mainLock.lock();
        try {
            if (worker != null) {
                workers.remove(worker);
            }
            control().reduceWorker();
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }

    /**
     * 处理将要退出的线程：
     * 1. 只会从worker线程调用
     * 2. 统计执行任务，并且将worker从worker集合中移除
     * 3.
     *
     * @param worker            任务执行工人
     * @param completedAbruptly worker退出是否是因用户任务异常导致
     */
    private void processWorkerExit(Worker worker, boolean completedAbruptly) {
        // 如果是因为用户任务异常导致，工人数量不作调整
        if (completedAbruptly) {
            control().reduceWorker();
        }

        // 将worker从集合中删除
        final ReentrantLock mainLock = industry.mainLock;
        mainLock.lock();
        try {
            metrics().completedTask(worker.competedTasks);
            workers.remove(worker);
        } finally {
            mainLock.unlock();
        }

        tryTerminate();

        ControlDetails hereState = current();
        if (hereState.notStopped()) {
            if (!completedAbruptly) {
                // 计算最小线程数
                int min = options().isAllowCoreThreadTimeOut() ? 0 : options().getCorePoolSize();
                if (min == 0 && !taskQueue.isEmpty()) {
                    min = 1;
                }

                // 如果当前worker数大于最小worker数，则无需处理，worker超时后会自动关闭
                if (hereState.workers() >= min) {
                    return;
                }
            }
            addWorker(null, false);
        }

    }

    @Override
    public void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = industry.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                // w.tryLock()判断该worker是否为空闲
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne) {
                    break;
                }
            }
        } finally {
            mainLock.unlock();
        }
    }

    @Override
    public Iterable<? extends AbstractWorker> workers() {
        return workers;
    }

    @EqualsAndHashCode(callSuper = false)
    private final class Worker extends AbstractWorker implements Runnable {

        final Thread thread;

        Runnable firstTask;

        volatile long competedTasks;

        Worker(Runnable firstTask) {
            setState(-1);
            this.firstTask = firstTask;
            this.thread = options().getThreadFactory().newThread(firstTask);
        }

        @Override
        public void run() {
            runWorker(this);
        }

        @Override
        public Thread getThread() {
            return thread;
        }
    }
}
