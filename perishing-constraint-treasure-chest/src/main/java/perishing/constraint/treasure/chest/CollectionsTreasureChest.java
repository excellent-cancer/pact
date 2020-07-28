package perishing.constraint.treasure.chest;

import perishing.constraint.note.PatternRemark;
import perishing.constraint.treasure.chest.collection.Entry2;
import perishing.constraint.treasure.chest.collection.FinalVariables;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 提供对于集合的常用功能
 *
 * @author XyParaCrim
 */
@PatternRemark.Utilities
public final class CollectionsTreasureChest {

    /**
     * Returns a fixed-size set backed by the specified array.
     *
     * @param <T>      the class of the elements in the set
     * @param elements the array by which the set will be backed
     * @return a set view of the specified array
     */
    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        if (elements.length > 0) {
            Set<T> set = new HashSet<>(elements.length);
            Collections.addAll(set, elements);
            return set;
        }
        return Collections.emptySet();
    }

    /**
     * 将{@link Iterable<T>}类型转成{@link List <T>}
     *
     * @param elements 可迭代元素
     * @param <T>      元素类型
     * @return 返回一个List
     */
    public static <T> List<T> asList(Iterable<? extends T> elements) {
        return elements == null ?
                Collections.emptyList() :
                StreamSupport.stream(elements.spliterator(), false).collect(Collectors.toList());
    }


    /**
     * 将{@link List<V>}类型转换成{@link Map}
     *
     * @param list 链表元素
     * @param extract key提取函数
     * @param <T> key类型
     * @param <V> value类型
     * @return 返回通过key映射list元素的map
     */
    public static <T, V> Map<T, V> asMapByList(List<V> list, Function<V, T> extract) {
        Map<T, V> table = new HashMap<>(list.size());
        for (V e : list) {
            table.put(extract.apply(e), e);
        }
        return table;
    }


    /**
     * 将{@link List<V>}类型转换成{@link Map}
     *
     * @param list 链表元素
     * @param extract key提取函数
     * @param <T> key类型
     * @param <V> value类型
     * @return 返回通过key映射list元素的map
     */
    public static <T, V> Map<T, List<V>> asFlatMapByList(List<V> list, Function<V, T> extract) {
        Map<T, List<V>> table = new HashMap<>(list.size());
        for (V e : list) {
            table.computeIfAbsent(extract.apply(e), k -> new LinkedList<>()).add(e);
        }

        return table;
    }

    public static <K> FinalVariables<K> finalVariables(Map<K, Object> origin) {
        return new FinalVariables<>(origin);
    }

    public static <K, V> Entry2<K, V> entry(K k, V v) {
        return new Entry2<>(k, v);
    }

}
