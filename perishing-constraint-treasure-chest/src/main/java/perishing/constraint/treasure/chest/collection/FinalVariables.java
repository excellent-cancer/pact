package perishing.constraint.treasure.chest.collection;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class FinalVariables<K> {

    private final Map<K, Object> variables;

    public <T> void set(@NonNull K type, T variable) {
        @SuppressWarnings("unchecked")
        T previous = (T) variables.putIfAbsent(type, variable);
        if (previous != null) {
            throw new IllegalArgumentException("Cannot assign a value to final variable type '" + type.getClass().getName() + "'");
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull K key) {
        return (T) Objects.requireNonNull(variables.get(key));
    }

    public boolean contains(K key) {
        return variables.containsKey(key);
    }

}
