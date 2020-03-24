package treasure.chest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 百宝箱：集中实现一些常用的过程
 *
 * @author XyParaCrim
 */
public class TreasureChest {

    /**
     * 遍历指定类的所有字段
     *
     * @param type   遍历的类
     * @param filter 字段筛选器
     * @param action 遍历动作
     */
    public static void fields(Class<?> type, Predicate<Field> filter, Consumer<Field> action) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(filter);
        Objects.requireNonNull(action);
        fieldsQuietly(type, filter, action);
    }

    /**
     * 安静模式：遍历指定类的所有字段
     *
     * @param type   遍历的类
     * @param filter 字段筛选器
     * @param action 遍历动作
     */
    public static void fieldsQuietly(Class<?> type, Predicate<Field> filter, Consumer<Field> action) {
        Stream.of(type.getFields()).filter(filter).
                forEach(action);
    }


    // 语法糖

    /**
     * 创建一个默认容量的HashMap。目的是为了避免阿里规约中，创建Map需要有指定初始化容量；
     * 还为{@link Map#computeIfAbsent(Object, Function)}方法提供一个默认输入参数
     *
     * @param k   {@link Map#computeIfAbsent(Object, Function)}的键值
     * @param <K> 外部Map键类型
     * @param <V> 内部Map键类型
     * @param <T> 内部Map值类型
     * @return 默认容量的HashMap
     */
    public static <K, V, T> Map<V, T> defaultMap(@SuppressWarnings("unused") K k) {
        return new HashMap<>(127);
    }


}
