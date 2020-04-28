package perishing.constraint.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public interface RejectedIndustryHandler extends RejectedExecutionHandler {

    void rejectedExecution(Runnable r, ExecutorIndustry industry);

    @Override
    default void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new UnsupportedOperationException();
    }

}
