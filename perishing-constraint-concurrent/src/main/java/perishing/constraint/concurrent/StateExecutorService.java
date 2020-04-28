package perishing.constraint.concurrent;

import perishing.constraint.concurrent.flow.ControlDetails;

public interface StateExecutorService extends StateSnapshoot {

    ControlDetails snapshoot();

    @Override
    default boolean isShutdown() {
        return snapshoot().isShutdown();
    }

    @Override
    default boolean isStopped() {
        return snapshoot().isStopped();
    }

    @Override
    default boolean isTerminating() {
        return snapshoot().isTerminating();
    }

    @Override
    default boolean isTerminated() {
        return snapshoot().isTerminated();
    }

    @Override
    default boolean isRunning() {
        return snapshoot().isRunning();
    }

    @Override
    default int workers() {
        return snapshoot().workers();
    }

}
