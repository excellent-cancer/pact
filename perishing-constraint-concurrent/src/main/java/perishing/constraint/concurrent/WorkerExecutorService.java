package perishing.constraint.concurrent;

import java.util.concurrent.BlockingQueue;

public interface WorkerExecutorService {

    BlockingQueue<Runnable> tasks();

    boolean removeTask(Runnable task);

    void interruptIdleWorkers(boolean onlyOne);

    Iterable<? extends AbstractWorker> workers();

    boolean addWorker(Runnable firstTask, boolean core);
}
