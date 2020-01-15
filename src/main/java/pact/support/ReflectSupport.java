package pact.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author XyParaCrim
 */
public final class ReflectSupport {

    public static void walkMethod(Object object, Predicate<Method> filter, Consumer<Method> action) {
        Objects.requireNonNull(object);
        Objects.requireNonNull(filter);
        Objects.requireNonNull(action);
        for (Method method : object.getClass().getMethods()) {
            if (filter.test(method)) {
                action.accept(method);
            }
        }
    }

    public static <T extends Annotation> void ifAnnotated(Method method, Class<T> annotation, Consumer<T> consumer) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(annotation);
        Objects.requireNonNull(consumer);

        Optional.ofNullable(method.getAnnotation(annotation))
                .ifPresent(consumer);
    }

    public static <T extends Annotation> void ifAnnotated(Method method, Class<T> annotation, BiConsumer<T, Method> consumer) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(annotation);
        Objects.requireNonNull(consumer);

        Optional.ofNullable(method.getAnnotation(annotation))
                .ifPresent(annotationTarget -> consumer.accept(annotationTarget, method));
    }
}
