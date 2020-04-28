package perishing.constraint.treasure.chest;

import java.util.Optional;

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


    /**
     * 不满意属性提取器：检查指定属性属性是否满意，返回反映
     * 此检查不满意的结果
     *
     * @author XyParaCrim
     */
    @FunctionalInterface
    public interface UnsatisfiedPropertyExtractor<T, V> {

        /**
         * 检查指定属性属性是否满意
         *
         * @param key        属性键值
         * @param properties 所有属性
         * @return 回反映此检查不满意的结果
         */
        Optional<T> extract(String key, V properties);

    }


}
