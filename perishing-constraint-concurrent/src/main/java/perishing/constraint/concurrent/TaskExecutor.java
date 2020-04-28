package perishing.constraint.concurrent;

import java.util.concurrent.Executor;

/**
 * 定义一个与{@link Executor}等价的执行{@link Runnable}的执行器。目的是
 * 在调试时，方便找到此方面的实例，并且便于以后添加一些独特的功能
 *
 * @author XyParaCrim
 */
public interface TaskExecutor extends Executor {

    /**
     * 异步或者同步，执行给定的任务.
     *
     * @param task 可运行的任务
     */
    @Override
    void execute(Runnable task);

}
