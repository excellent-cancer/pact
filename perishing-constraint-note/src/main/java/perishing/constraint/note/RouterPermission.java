package perishing.constraint.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示一个路由的可读还是可写
 *
 * @author XyParaCrim
 */
@Target({ElementType.TYPE, ElementType.MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface RouterPermission {

    boolean readOnly();

}
