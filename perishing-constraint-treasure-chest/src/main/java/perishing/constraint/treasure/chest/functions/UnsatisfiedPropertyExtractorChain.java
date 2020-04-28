package perishing.constraint.treasure.chest.functions;

import lombok.NonNull;

import java.util.Optional;

/**
 * 不满意属性提取器的链状执行器，用于将多个执行器组合，提取
 * 不满意结果
 *
 * @param <T>
 * @param <V>
 * @author XyParaCrim
 */
public interface UnsatisfiedPropertyExtractorChain<T, V, P extends UnsatisfiedPropertyExtractor<T, V>> {


    /**
     * 执行器组合提取器
     *
     * @param properties 属性组合
     * @return 不满意结果
     */
    Optional<T> extract(@NonNull V properties);

    /**
     * 收集提取器
     *
     * @param key       属性键值
     * @param extractor 提取器
     * @return 链式调用
     */
    UnsatisfiedPropertyExtractorChain<T, V, P> chain(String key, @NonNull P extractor);

}
