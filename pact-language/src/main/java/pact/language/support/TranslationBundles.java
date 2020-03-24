package pact.language.support;

import pact.annotations.PatternRemark;
import pact.language.support.errors.TranslationBundleException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 不同的{@link AbstractTranslationBundle}的实现
 *
 * @author XyParaCrim
 */
@PatternRemark(composite = PatternRemark.CompositeLevel.CLASS)
public final class TranslationBundles {

    /**
     * 加载该类文件下同级目录的资源文件
     */
    public static abstract class TranslationBundle extends AbstractTranslationBundle {

        @Override
        ResourceBundle loadResourceBundle(Locale locale) throws TranslationBundleException {
            return BundleUtils.loadResourceBundle(getClass(), locale);
        }

    }

}
