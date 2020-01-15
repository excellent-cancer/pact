package pact.support;

import pact.annotation.NotNull;

import java.util.concurrent.*;

/**
 * @author XyParaCrim
 */
public final class ExecutorSupport {

    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();


    /**
     * 只允许当单线程且keepalive时间为0，即不允许执行其他线程。
     *
     * @return 只执行唯一线程的ExecutorService
     */
    @NotNull
    public static ExecutorService singleOnlyThreadExecutor() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1), DEFAULT_THREAD_FACTORY);
    }

    public static void execute(Runnable runnable) {
        singleOnlyThreadExecutor().submit(runnable);


        // Thread thread = new Thread(runnable);
        // thread.start();
        // runnable.run();
        // sExecutors.newSingleThreadExecutor().submit(runnable);
        // singleOnlyThreadExecutor().submit(runnable);
    }
}
