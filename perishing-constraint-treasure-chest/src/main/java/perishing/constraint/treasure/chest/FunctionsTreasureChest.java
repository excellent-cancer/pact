package perishing.constraint.treasure.chest;

/**
 * 提供对于方法接口的常用功能
 *
 * @author XyParaCrim
 */
public final class FunctionsTreasureChest {

    public interface RunnableWithException<T extends Throwable> {
        void run() throws T;
    }

    public interface SupplierWithException<V, T extends Throwable> {
        V get() throws T;
    }

    public interface ConsumerWithException<V, T extends Throwable> {
        void accept(V v) throws T;
    }

}
