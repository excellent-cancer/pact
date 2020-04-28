package perishing.constraint.concurrent.flow;

import perishing.constraint.concurrent.AbstractWorker;
import perishing.constraint.concurrent.WorkerExecutorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

class FlowOriginalService extends AbstractService implements ExecutorService {

    private final Delegation delegation = new Delegation();

    private class Delegation extends AbstractExecutorService {

        @Override
        public void shutdown() {
            final ReentrantLock mainLock = mainLock();
            mainLock.lock();
            try {
                checkShutdownAccess();
                advanceRunState(RunState.SHUTDOWN);
                industry.workerService().interruptIdleWorkers(false);
                // TODO: Cal hooks
            } finally {
                mainLock.unlock();
            }
            tryTerminate();
        }

        @Override
        public List<Runnable> shutdownNow() {
            List<Runnable> tasks;
            final ReentrantLock mainLock = mainLock();
            mainLock.lock();
            try {
                checkShutdownAccess();
                advanceRunState(RunState.STOP);
                industry.workerService().interruptIdleWorkers(true);
                tasks = drainQueue();
            } finally {
                mainLock.unlock();
            }
            tryTerminate();
            return tasks;
        }

        @Override
        public boolean isShutdown() {
            return industry.stateService().isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return industry.stateService().isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            long nanos = unit.toNanos(timeout);
            final ReentrantLock mainLock = industry.mainLock;

            mainLock.lock();
            try {
                while (current().notTerminated()) {
                    if (nanos <= 0L) {
                        return false;
                    }
                    nanos = industry.termination.awaitNanos(nanos);
                }
                return true;
            } finally {
                mainLock.unlock();
            }
        }

        @Override
        public void execute(Runnable task) {
            if (task == null) {
                throw new NullPointerException();
            }

            final WorkerExecutorService workerService = industry.workerService();
            ControlDetails details = current();
            if (details.workers() < options().getCorePoolSize()) {
                if (workerService.addWorker(task, true)) {
                    return;
                }
                details = current();
            }

            if (details.isRunning() && tasks().offer(task)) {
                ControlDetails recheck = current();
                if (!recheck.isRunning() && removed(task)) {
                    reject(task);
                } else if (!recheck.existsWorker()) {
                    workerService.addWorker(null, false);
                }
            } else if (!workerService.addWorker(task, false)) {
                reject(task);
            }
        }

    }

    public FlowOriginalService(FlowExecutorIndustry industry) {
        super(industry);
    }

    @Override
    public void shutdown() {
        delegation.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return delegation.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegation.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegation.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return delegation.awaitTermination(timeout, unit);
    }

    @Override
    public void execute(Runnable task) {
        delegation.execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return delegation.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return delegation.submit(task, result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return delegation.submit(task);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return delegation.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegation.invokeAny(tasks, timeout, unit);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegation.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return delegation.invokeAll(tasks, timeout, unit);
    }

    private void checkShutdownAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SHUTDOWN_PERM);

            Iterable<AbstractWorker> workers = industry.workerService().workers();
            for (AbstractWorker worker : workers) {
                security.checkAccess(worker.getThread());
            }
        }
    }

    private void advanceRunState(RunState targetState) {
        for (; ; ) {
            ControlDetails details = current();
            if (targetState.already(details) || targetState.tryUpdate(details)) {
                break;
            }
        }
    }

    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = tasks();
        ArrayList<Runnable> taskList = new ArrayList<>();
        q.drainTo(taskList);
        if (!q.isEmpty()) {
            for (Runnable r : q.toArray(new Runnable[0])) {
                if (q.remove(r)) {
                    taskList.add(r);
                }
            }
        }
        return taskList;
    }

    // 公共部分

    private static final RuntimePermission SHUTDOWN_PERM = new RuntimePermission("modifyThread");

    void reject(Runnable command) {
        options().getHandler().rejectedExecution(command, industry);
    }

    boolean removed(Runnable task) {
        return industry.workerService().removeTask(task);
    }

}

