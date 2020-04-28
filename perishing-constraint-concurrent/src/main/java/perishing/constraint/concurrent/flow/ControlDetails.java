package perishing.constraint.concurrent.flow;

import perishing.constraint.concurrent.StateSnapshoot;

import static perishing.constraint.concurrent.flow.Constants.COUNT_MAST;
import static perishing.constraint.concurrent.flow.RunState.*;

public class ControlDetails implements StateSnapshoot {

    final int code;

    final Control control;

    ControlDetails(Control control) {
        this.control = control;
        this.code = control.code().intValue();
    }

    Control control() {
        return control;
    }

    @Override
    public int workers() {
        return code & COUNT_MAST;
    }

    @Override
    public boolean isRunning() {
        return code < SHUTDOWN.code;
    }

    @Override
    public boolean isShutdown() {
        return alreadyShutdown();
    }

    @Override
    public boolean isTerminating() {
        return alreadyShutdown() && notTerminated();
    }

    @Override
    public boolean isTerminated() {
        return alreadyTerminated();
    }

    @Override
    public boolean isStopped() {
        return alreadyStopped();
    }

    boolean notStopped() {
        return code < STOP.code;
    }

    boolean notShutdown() {
        return code < SHUTDOWN.code;
    }

    boolean notTerminated() {
        return code < TERMINATED.code;
    }

    boolean alreadyTidying() {
        return code >= TIDYING.code;
    }

    boolean alreadyShutdown() {
        return code >= SHUTDOWN.code;
    }

    boolean alreadyStopped() {
        return code >= STOP.code;
    }

    boolean alreadyTerminated() {
        return code >= TERMINATED.code;
    }

    boolean existsWorker() {
        return workers() > 0;
    }

    boolean tryAddWorker() {
        return control.compareAndSet(code, code + 1);
    }

    boolean tryReduceWorker() {
        return control.compareAndSet(code, code - 1);
    }

    boolean isRefuseToReceiveTask() {
        return alreadyShutdown();
    }

    boolean isRefuseToProcessTask() {
        return alreadyStopped();
    }

    boolean isContinueToProcessTask() {
        return notStopped();
    }

    boolean isContinueToReceiveTask() {
        return notShutdown();
    }

}
