package pact.support;

import java.util.concurrent.*;

/**
 * @author XyParaCrim
 */
public final class ExecutorSupport {

    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();

    public static ExecutorService singleOnlyThreadExecutor() {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1), DEFAULT_THREAD_FACTORY);
    }
}
