package perishing.constraint.concurrent;

import lombok.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 创建ExecutorIndustry的选项
 *
 * @author XyParaCrim
 */
@Data
public class ExecutorIndustryOptions {

    /**
     * 线程的数量保持在池中，即使它们是空闲的
     */
    int corePoolSize;

    /**
     * 线程池最大线程数
     */
    int maximumPoolSize;

    /**
     * 多于corePoolSize数的线程的最大存活时间
     */
    long keepAliveTime;

    /**
     * 时间单位
     */
    TimeUnit unit;

    /**
     * 是否允许核心线程超时，超时后会关闭线程
     */
    boolean allowCoreThreadTimeOut;

    RejectedIndustryHandler handler;

    BlockingQueue<Runnable> taskQueue;

    ThreadFactory threadFactory;
}
