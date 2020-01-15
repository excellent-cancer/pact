package pact.component.lifecycle;

import java.util.HashMap;
import java.util.Map;


/**
 * 多{@link AutoCloseable}集合，能够自动按照创建组件的逆序
 * 调用{@link AutoCloseable#close()}方法。
 * 特别地，一个Class只支持一个实例，且储存了的组件实例是不能替换的。
 *
 * @author XyParaCrim
 */
public abstract class AutoCloseComponent implements AutoCloseable {

    private final Map<Class<? extends AutoCloseable>, AutoCloseable> closeResources =
            new HashMap<>();


    protected <T extends AutoCloseable> void createdComponent(Class<T> resourceClass, T resource) {
        if (closeResources.containsKey(resourceClass)) {
            // TODO
            throw new UnsupportedOperationException();
        }
        closeResources.put(resourceClass, resource);
    }

    @SuppressWarnings("unchecked")
    public <T extends AutoCloseable> T component(Class<T> resourceClass) {
        return (T) closeResources.get(resourceClass);
    }

    @Override
    public void close() throws Exception {
        Object[] resourceClasses = closeResources.keySet().toArray();
        for (int i = resourceClasses.length - 1; i > -1; i--) {
            closeResources.
                    remove(resourceClasses[i]).
                    close();
        }
    }
}
