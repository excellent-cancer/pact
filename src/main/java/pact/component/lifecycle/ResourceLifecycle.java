package pact.component.lifecycle;

public class ResourceLifecycle extends StateHolder {

    public boolean isCreated() {
        return state == State.CREATED;
    }

    public boolean isClosed() {
        return state.isClosed();
    }

    public boolean tryCreate() {
        for (; ; ) {
            State hereState = state;
            if (hereState == State.INITIALIZED) {
                if (HANDLE_STATE.compareAndSet(this, hereState, State.CREATED)) {
                    return true;
                }
                continue;
            }
            if (hereState == State.CREATED) {
                return false;
            }

            throw new IllegalStateException("Can't make state of Process created when it's not initialized");
        }
    }

    public boolean tryClose() {
        for (; ; ) {
            State hereState = state;
            if (hereState == State.INITIALIZED) {
                return false;
            }

            if (hereState == State.CLOSED) {
                throw new IllegalStateException("Can't make state of Process terminating when it's closed");
            }

            if (hereState == State.CREATED && HANDLE_STATE.compareAndSet(this, hereState, State.CLOSED)) {
                return true;
            }
        }
    }
}
