package perishing.constraint.treasure.chest.functions;

import java.util.Optional;

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