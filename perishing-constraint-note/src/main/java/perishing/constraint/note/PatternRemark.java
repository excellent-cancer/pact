package perishing.constraint.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于备注一个类是怎么设计的，即设计模式的备注
 *
 * @author XyParaCrim
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.SOURCE)
@SuppressWarnings("unused")
public @interface PatternRemark {

    // 支持的设计模式

    /**
     * 备注类主要的聚合类：
     * 表示该类其实是一组聚合类的封装，没有过多的逻辑，工作主要交给复合类。
     * 备注类 = 修饰的聚合类
     *
     * @return aggregative classes
     */
    Class<?>[] aggregative() default {};

    /**
     * 备注类是一个外观类
     *
     * @return whether it's facade pattern
     */
    boolean facade() default false;

    /**
     * 备注类是一个组合类。
     *
     * @return level of composite pattern
     */
    CompositeLevel composite() default CompositeLevel.NOT;

    /**
     * 是否是一个utilities类
     *
     * @return 是否是
     */
    boolean utils() default false;

    // 特别的模式类型

    enum CompositeLevel {
        /**
         * 表示静态类组合相同类型的变量
         */
        CLASS,

        /**
         * 表示类实现了部分与整体的一致性
         */
        INSTANCE,

        /**
         * 表示不是组合模式
         */
        NOT
    }

    // 其他组合

    /**
     * @author XyParaCrim
     * @see PatternRemark#utils()
     */
    @PatternRemark(utils = true)
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    @interface Utilities {
    }
}
