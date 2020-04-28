package perishing.constraint.concurrent;

public interface StateSnapshoot {

    int workers();

    boolean isRunning();

    boolean isShutdown();

    boolean isTerminating();

    boolean isTerminated();

    boolean isStopped();

}
