package perishing.constraint.language.support;

import perishing.constraint.note.PatternRemark;

import java.util.Locale;

/**
 * 语言模块的外观，提供相关语言的快捷功能
 *
 * @author XyParaCrim
 */
@PatternRemark(facade = true)
public final class LanguageSupport {

    /**
     * 输入指定语言资源包类，返回文本注入后的该类实例。默认使用{@link Locale#getDefault()}
     *
     * @param type 语言资源包类
     * @param <T>  语言资源包类型
     * @return 语言资源包实例
     */
    public static <T extends AbstractTranslationBundle> T getBundleFor(Class<T> type) {
        return BundleCache.currentBundleCache(Locale.getDefault()).get(type);
    }

    /**
     * 输入指定语言资源包类，返回文本注入后的该类实例
     *
     * @param type 语言资源包类
     * @param <T>  语言资源包类型
     * @return 语言资源包实例
     */
    public static <T extends AbstractTranslationBundle> T getBundleFor(Class<T> type, Locale locale) {
        return BundleCache.currentBundleCache(locale).get(type);
    }

}