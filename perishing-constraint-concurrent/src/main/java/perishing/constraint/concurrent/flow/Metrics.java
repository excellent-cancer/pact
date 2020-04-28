package perishing.constraint.concurrent.flow;

class Metrics {

    private long completedTaskCount;

    private int largestPoolSize;

    void completedTask(long completed) {
        this.completedTaskCount += completed;
    }

    void poolSizeChanged(int poolSize) {
        if (poolSize > largestPoolSize) {
            largestPoolSize = poolSize;
        }
    }

}
