package pact.language.support.errors;

import java.util.Locale;

/**
 * 关于国际化语言的异常
 *
 * @author XyParaCrim
 */
@SuppressWarnings("unused")
public class TranslationBundleException extends RuntimeException {

    private final Class<?> bundleClass;

    private final Locale locale;

    public TranslationBundleException(Throwable cause, Class<?> bundleClass, Locale locale) {
        super(cause);
        this.bundleClass = bundleClass;
        this.locale = locale;
    }

    public final Class<?> getBundleClass() {
        return bundleClass;
    }

    public Locale getLocale() {
        return locale;
    }
}
