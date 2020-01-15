package pact.component.lifecycle;

enum State {
    /**
     * Initial state.
     */
    INITIALIZED,
    /**
     *
     */
    STARTED,
    /**
     *
     */
    CLOSED,
    /**
     *
     */
    TERMINATING,
    /**
     *
     */
    CREATED;

    boolean isInitialized() {
        return this == INITIALIZED;
    }

    boolean isStarted() {
        return this == STARTED;
    }

    boolean isClosed() {
        return this == CLOSED;
    }

    boolean isTerminating() {
        return this == TERMINATING;
    }

    boolean isCreated() {
        return this == CREATED;
    }
}
