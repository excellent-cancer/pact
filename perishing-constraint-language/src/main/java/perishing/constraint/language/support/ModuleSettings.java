package perishing.constraint.language.support;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public final class ModuleSettings {

    private static final Predicate<Field> translatedFieldFilter = field -> field.getType().equals(String.class);

    public static Predicate<Field> translatedFieldFilter() {
        return translatedFieldFilter;
    }

}
