package pact.support;

public final class FunctionSupport {
    public interface RunnableWithException<T extends Throwable> {
        void run() throws T;
    }

    public interface SupplierWithException<V, T extends Throwable> {
        V get() throws T;
    }
}
