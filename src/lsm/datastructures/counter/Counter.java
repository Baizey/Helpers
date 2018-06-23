package lsm.datastructures.counter;

import lsm.helpers.interfaces.Alter;
import lsm.helpers.interfaces.ToInt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Counter<K> extends HashMap<K, Integer> {
    private final ToInt<K> toInt;
    private final Alter<K> alter;

    public static <K> Counter<K> of(K... keys) {
        HashMap<Integer, Integer> map;
        return new Counter<K>().add(keys);
    }

    public static <K> Counter<K> of(ToInt<K> toInt, K... keys) {
        return new Counter<>(toInt).add(keys);
    }

    public static <K> Counter<K> of(Alter<K> change, K... keys) {
        return new Counter<>(change).add(keys);
    }

    public static <K> Counter<K> of(ToInt<K> toInt, Alter<K> change, K... keys) {
        return new Counter<>(change, toInt).add(keys);
    }

    public Counter() {
        this(a -> a, a -> 1);
    }

    public Counter(ToInt<K> toInt) {
        this(a -> a, toInt);
    }

    public Counter(Alter<K> change) {
        this(change, a -> 1);
    }

    public Counter(Alter<K> change, ToInt<K> toInt) {
        super();
        this.toInt = toInt;
        this.alter = change;
    }

    public Counter<K> setAs(Counter<K> other) {
        other.forEach(this::put);
        return this;
    }

    public Counter<K> add(K... keys) {
        for (K key : keys)
            change(alter.alter(key), toInt.convert(key));
        return this;
    }

    public Counter<K> sub(K... keys) {
        for (K key : keys)
            change(alter.alter(key), -toInt.convert(key));
        return this;
    }

    public void change(K key, Integer amount) {
        put(key, getOrDefault(key, 0) + amount);
    }

    public Integer get(Object key) {
        return getOrDefault(alter.alter((K) key), 0);
    }

    public Stream<Map.Entry<K, Integer>> stream() {
        return entrySet().stream();
    }
}
