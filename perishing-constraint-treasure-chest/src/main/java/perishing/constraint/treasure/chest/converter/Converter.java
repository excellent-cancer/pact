package perishing.constraint.treasure.chest.converter;

/**
 * 两种类型的转换器
 *
 * @author XyParaCrim
 */
public interface Converter<T, V> {

    V to(T t);

    T from(V v);

}
