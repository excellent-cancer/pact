package perishing.constraint.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开发时使用，标记哪些没有梳理好如何设计，但粗劣完成的类和方法
 *
 * @author XyParaCrim
 */
@OnDevelopment
@Target({ElementType.TYPE, ElementType.MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface Unqualified {
}
