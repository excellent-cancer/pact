package perishing.constraint.concurrent.flow;

import perishing.constraint.concurrent.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在什么场景下需要提交任务到{@link ExecutorIndustry}呢？
 * 1.克隆远程仓库的任务
 *
 * @author XyParaCrim
 */
public class FlowExecutorIndustry extends AbstractExecutorIndustry {

    final ReentrantLock mainLock = new ReentrantLock();

    final Condition termination = mainLock.newCondition();

    final Metrics metrics = new Metrics();

    final Control control = new Control();

    private final ExecutorService executorService = new FlowOriginalService(this);

    private final StateExecutorService stateService = new FlowStateExecutorService(this, control);

    private final WorkerExecutorService workerService = new FlowWorkerExecutorService(this, options.getTaskQueue());

    public FlowExecutorIndustry(ExecutorIndustryOptions options) {
        super(options);
    }

    @Override
    public ExecutorService originalService() {
        return executorService;
    }

    @Override
    public ReactiveExecutorService reactiveService() {
        return null;
    }

    @Override
    public MetricsExecutorService metricsService() {
        return null;
    }

    @Override
    public ScheduledExecutorService scheduledService() {
        return null;
    }

    @Override
    public StateExecutorService stateService() {
        return stateService;
    }

    @Override
    public WorkerExecutorService workerService() {
        return workerService;
    }

}
