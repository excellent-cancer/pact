package pact.component.lifecycle;

/**
 * Common service lifecycle template, approximate order:
 * INITIALIZED -> STARTED -> CLOSED -> TERMINATING.
 *
 * @author XyParaCrim
 */
public class ServiceLifecycle extends StateHolder {

    public boolean isStarted() {
        return state.isStarted();
    }

    public boolean isClose() {
        return state.isClosed();
    }

    public boolean isTerminating() {
        return state.isTerminating();
    }

    public boolean tryStarted() {
        for ( ; ; ) {
            State hereState = state;
            if (hereState == State.INITIALIZED) {
                if (HANDLE_STATE.compareAndSet(this, hereState, State.STARTED)) {
                    return true;
                }
                continue;
            }

            if (hereState == State.STARTED) {
                return false;
            }

            throw new IllegalStateException("Can't make state of Service started when it's not initialized");
        }
    }

    public boolean tryTerminating() {
        for ( ; ; ) {
            State hereState = state;
            if (hereState == State.INITIALIZED || hereState == State.TERMINATING) {
                return false;
            }

            if (hereState == State.CLOSED) {
                throw new IllegalStateException("Can't make state of Service terminating when it's closed");
            }

            if (hereState == State.STARTED && HANDLE_STATE.compareAndSet(this, hereState, State.TERMINATING)) {
                return true;
            }
        }
    }

    public boolean tryClosed() {
        for ( ; ; ) {
            State hereState = state;
            if (hereState == State.INITIALIZED || hereState == State.CLOSED) {
                return false;
            }

            if (HANDLE_STATE.compareAndSet(this, state, State.CLOSED)) {
                return true;
            }
        }
    }
}
