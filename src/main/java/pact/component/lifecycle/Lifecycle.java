package pact.component.lifecycle;

/**
 * @author XyParaCrim
 */
public final class Lifecycle {

    public static ResourceLifecycle resource() {
        return new ResourceLifecycle();
    }

    public static ServiceLifecycle service() {
        return new ServiceLifecycle();
    }

}
