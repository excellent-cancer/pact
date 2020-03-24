package pact.language.support;

import pact.language.support.errors.TranslationBundleException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 翻译包：能够将文本注入到public的字符串字段
 *
 * @author XyParaCrim
 */
public abstract class AbstractTranslationBundle {

    /**
     * 需要实现如何加载真正的{@link ResourceBundle}资源包实例，调用位置在{@link TranslationBundleHolder#TranslationBundleHolder(Locale, AbstractTranslationBundle)}
     *
     * @param locale 用于加载资源包
     * @return 资源包实例
     * @throws TranslationBundleException 加载资源发生的异常
     */
    abstract ResourceBundle loadResourceBundle(Locale locale) throws TranslationBundleException;

}
