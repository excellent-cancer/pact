package pact.language.support;

import pact.language.support.errors.TranslationBundleException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 关于包的帮助方法
 */
final class BundleUtils {

    /**
     * 通过指定的class和local加载资源包
     *
     * @param type   资源包名类
     * @param locale 资源定位
     * @return 指定的class和local加载资源包
     * @throws TranslationBundleException 通过class和local加载资源发生的异常
     */
    static ResourceBundle loadResourceBundle(Class<?> type, Locale locale) throws TranslationBundleException {
        try {
            String baseName = type.getName();
            ClassLoader classLoader = type.getClassLoader();

            return ResourceBundle.getBundle(baseName, locale, classLoader);
        } catch (Exception e) {
            throw new TranslationBundleException(e, type, locale);
        }
    }

}
