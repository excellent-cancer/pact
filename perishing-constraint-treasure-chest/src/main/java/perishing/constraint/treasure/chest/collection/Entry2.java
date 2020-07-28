package perishing.constraint.treasure.chest.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Entry2<K, V> {

    private final K k;

    @Getter
    private final V v;


    public K get1() {
        return k;
    }

    public V get2() {
        return v;
    }

}
