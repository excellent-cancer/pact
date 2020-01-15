package pact.support;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Collections Utilities.
 *
 * @author XyParaCrim
 */
public class CollectionsSupport {

    /**
     * Returns a fixed-size set backed by the specified array.
     *
     * @param <T>      the class of the elements in the set
     * @param elements the array by which the set will be backed
     * @return a set view of the specified array
     */
    @SafeVarargs
    public static <T> Set<T> asSet(T... elements) {
        if (elements.length > 0) {
            Set<T> set = new HashSet<>(elements.length);
            Collections.addAll(set, elements);
            return set;
        }
        return Collections.emptySet();
    }

}
