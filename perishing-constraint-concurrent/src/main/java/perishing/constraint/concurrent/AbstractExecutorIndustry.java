package perishing.constraint.concurrent;

public abstract class AbstractExecutorIndustry implements ExecutorIndustry {

    protected final ExecutorIndustryOptions options;

    public AbstractExecutorIndustry(ExecutorIndustryOptions options) {
        this.options = options;
    }

    @Override
    public ExecutorIndustryOptions options() {
        return options;
    }
}
