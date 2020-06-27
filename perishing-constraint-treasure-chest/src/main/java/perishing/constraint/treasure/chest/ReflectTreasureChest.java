package perishing.constraint.treasure.chest;

import lombok.NonNull;
import perishing.constraint.note.PatternRemark;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 提供关于反射的常用功能
 *
 * @author XyParaCrim
 */
@PatternRemark.Utilities
public final class ReflectTreasureChest {

    /**
     * 遍历一个实例的在类中定义的方法
     *
     * @param instance 具体遍历的实例
     * @param filter   筛选方法
     * @param action   执行action
     */
    public static void forEachMethod(@NonNull Object instance, @NonNull Predicate<Method> filter,
                                     @NonNull Consumer<Method> action) {
        for (Method method : instance.getClass().getMethods()) {
            if (filter.test(method)) {
                action.accept(method);
            }
        }

    }

    /**
     * 如果一个方法被一个指定的注解标记，则执行action
     *
     * @param method     指定的方法
     * @param annotation 指定的注解类型
     * @param consumer   消费方法
     * @param <T>注解类型
     */
    public static <T extends Annotation> void ifAnnotated(@NonNull Method method, @NonNull Class<T> annotation,
                                                          @NonNull Consumer<T> consumer) {
        Optional.ofNullable(method.getAnnotation(annotation))
                .ifPresent(consumer);
    }

    /**
     * 如果一个方法被一个指定的注解标记，则执行action
     *
     * @param method     指定的方法
     * @param annotation 指定的注解类型
     * @param consumer   消费方法
     * @param <T>注解类型
     */
    public static <T extends Annotation> void ifAnnotated(@NonNull Method method, @NonNull Class<T> annotation,
                                                          @NonNull BiConsumer<T, Method> consumer) {
        Optional.ofNullable(method.getAnnotation(annotation))
                .ifPresent(annotationTarget -> consumer.accept(annotationTarget, method));
    }

    public static <T> T newInstance(@NonNull Class<? extends T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
