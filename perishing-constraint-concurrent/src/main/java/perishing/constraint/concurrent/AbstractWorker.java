package perishing.constraint.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public abstract class AbstractWorker extends AbstractQueuedSynchronizer {

    public void lock() {
        acquire(1);
    }

    public void unlock() {
        release(1);
    }


    public boolean tryLock() {
        return tryAcquire(1);
    }

    public boolean isLocked() {
        return isHeldExclusively();
    }

    public abstract Thread getThread();

}
