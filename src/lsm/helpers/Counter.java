package lsm.helpers;

import lsm.helpers.interfaces.KeyAlter;
import lsm.helpers.interfaces.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Counter<K> extends HashMap<K, Integer> {
    private final Rule<K> rule;
    private final KeyAlter<K> keyAlter;

    public static <K> Counter<K> of(K... keys) {
        HashMap<Integer, Integer> map;
        return new Counter<K>().add(keys);
    }

    public static <K> Counter<K> of(Rule<K> rule, K... keys) {
        return new Counter<>(rule).add(keys);
    }

    public static <K> Counter<K> of(KeyAlter<K> change, K... keys) {
        return new Counter<>(change).add(keys);
    }

    public static <K> Counter<K> of(Rule<K> rule, KeyAlter<K> change, K... keys) {
        return new Counter<>(change, rule).add(keys);
    }

    public Counter() {
        this(a -> a, a -> 1);
    }

    public Counter(Rule<K> rule) {
        this(a -> a, rule);
    }

    public Counter(KeyAlter<K> change) {
        this(change, a -> 1);
    }

    public Counter(KeyAlter<K> change, Rule<K> rule) {
        super();
        this.rule = rule;
        this.keyAlter = change;
    }

    public Counter<K> setAs(Counter<K> other) {
        other.forEach(this::put);
        return this;
    }

    public Counter<K> add(K... keys) {
        for (K key : keys)
            change(keyAlter.alter(key), rule.handle(key));
        return this;
    }

    public Counter<K> sub(K... keys) {
        for (K key : keys)
            change(keyAlter.alter(key), -rule.handle(key));
        return this;
    }

    public void change(K key, Integer amount) {
        put(key, getOrDefault(key, 0) + amount);
    }

    public Integer get(Object key) {
        return getOrDefault(keyAlter.alter((K) key), 0);
    }

    public Stream<Map.Entry<K, Integer>> stream() {
        return entrySet().stream();
    }
}
