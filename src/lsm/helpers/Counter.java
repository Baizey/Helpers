package lsm.helpers;

import java.util.HashMap;
import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Counter<K> {
    private final HashMap<K, Integer> count;

    public Counter() {
        count = new HashMap<>();
    }

    @SafeVarargs //pls
    public Counter(K... keys) {
        this();
        for (K key : keys) add(key);
    }

    public Counter(HashMap<K, Integer> map) {
        this.count = map;
    }

    public Counter(Counter<K> count) {
        this(new HashMap<>(count.count));
    }

    public void add(K key) {
        add(key, 1);
    }

    public void sub(K key) {
        add(key, -1);
    }

    public void remove(K key) {
        sub(key);
        if (get(key) <= 0)
            count.remove(key);
    }

    public void add(K key, int amount) {
        count.put(key, count.getOrDefault(key, 0) + amount);
    }

    public int get(K key) {
        return count.getOrDefault(key, 0);
    }

    public HashMap<K, Integer> getMap() {
        return count;
    }

    public Set<K> getKeys() {
        return count.keySet();
    }

    public boolean contains(K key) {
        return count.containsKey(key);
    }
}
