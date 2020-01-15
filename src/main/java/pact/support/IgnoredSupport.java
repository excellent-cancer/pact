package pact.support;

/**
 * @author XyParaCrim
 */
public final class IgnoredSupport {

    public static Runnable collectIgnored(FunctionSupport.RunnableWithException<InterruptedException> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (InterruptedException ignored) {
            }
        };
    }

}
