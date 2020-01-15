package pact.component.lifecycle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

abstract class StateHolder {

    protected StateHolder() {
        this(State.INITIALIZED);
    }

    protected StateHolder(State state) {
        this.state = state;
    }

    protected volatile State state;

    protected static final VarHandle HANDLE_STATE;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            HANDLE_STATE = lookup.findVarHandle(StateHolder.class, "state", State.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
