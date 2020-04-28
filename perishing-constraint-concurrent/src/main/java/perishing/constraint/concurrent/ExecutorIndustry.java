package perishing.constraint.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 执行业，提供不同执行器的服务
 *
 * @author XyParaCrim
 */
public interface ExecutorIndustry {

    /**
     * 由JDK提供的服务入口
     *
     * @return JDK定义的执行器入服务入口
     */
    ExecutorService originalService();

    /**
     * 响应式服务，例如订阅和压背式相关功能
     *
     * @return 响应式服务
     */
    ReactiveExecutorService reactiveService();

    /**
     * 提供关于执行器的统计、性能监控、指标等功能入口
     *
     * @return 提供关于执行器的统计、性能监控、指标等功能入口
     */
    MetricsExecutorService metricsService();

    /**
     * 可安排任务以给定的延迟后运行，或定期地执行
     *
     * @return 可安排任务以给定的延迟后运行，或定期地执行
     */
    ScheduledExecutorService scheduledService();


    StateExecutorService stateService();

    WorkerExecutorService workerService();

    ExecutorIndustryOptions options();
}
