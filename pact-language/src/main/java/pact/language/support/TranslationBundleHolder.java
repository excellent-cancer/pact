package pact.language.support;

import pact.annotations.PatternRemark;
import pact.language.support.errors.TranslationBundleConstructError;
import pact.language.support.errors.TranslationBundleException;
import treasure.chest.TreasureChest;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 持有语言资源包的相关对象实例：
 * 1.{@code effectiveLocale} 加载资源的{@link Locale}
 * 2.{@code resourceBundle} 加载的资源包{@link ResourceBundle}
 * 3.{@code translationBundle} 将加载的文本注入的包
 */
@SuppressWarnings("unused")
@PatternRemark(aggregative = ResourceBundle.class)
final class TranslationBundleHolder {

    private final Locale effectiveLocale;

    private final ResourceBundle resourceBundle;

    private final AbstractTranslationBundle translationBundle;

    /**
     * 提供一个Locale，加载语言资源包，并将文本注入至该类的字段中
     *
     * @param locale 用于加载资源包的locale
     * @throws TranslationBundleException 在加载过程中，发生的异常
     */
    TranslationBundleHolder(Locale locale, AbstractTranslationBundle translationBundle) throws TranslationBundleException {
        Class<? extends TranslationBundleHolder> bundleClass = getClass();

        this.effectiveLocale = locale;
        this.translationBundle = translationBundle;
        this.resourceBundle = translationBundle.loadResourceBundle(locale);

        TreasureChest.fieldsQuietly(translationBundle.getClass(), ModuleSettings.translatedFieldFilter(), this::injectText);
    }

    /**
     * 将输入的字段注入文本
     *
     * @param field 注入文本的字段
     */
    protected void injectText(Field field) {
        String translatedText = resourceBundle.getString(field.getName());
        try {
            field.set(translationBundle, translatedText);
        } catch (IllegalAccessException e) {
            throw new TranslationBundleConstructError(e);
        }
    }

    // Export methods

    /**
     * @return 加载资源包的locale
     */
    public Locale effectiveLocale() {
        return effectiveLocale;
    }

    /**
     * @return 加载的资源包
     */
    public ResourceBundle resourceBundle() {
        return resourceBundle;
    }

    /**
     * @return 注入文本的资源包
     */
    public AbstractTranslationBundle translationBundle() {
        return translationBundle;
    }
}
