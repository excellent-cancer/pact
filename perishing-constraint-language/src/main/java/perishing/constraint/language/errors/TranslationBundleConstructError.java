package perishing.constraint.language.errors;

import perishing.constraint.language.support.AbstractTranslationBundle;

/**
 * 表示构造{@link AbstractTranslationBundle}实例时，即将字符串
 * 赋值到字段，发生的错误。
 * 单独定义是因为无法设置中文解释
 *
 * @author XyParaCrim
 */
public class TranslationBundleConstructError extends Error {

    public TranslationBundleConstructError(Throwable cause) {
        super(cause);
    }
}
