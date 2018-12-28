package lsm.datastructures.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Counter<K> {

    private final HashMap<K, Integer> map = new HashMap<>();

    public void increment(K item, int amount) {
        map.putIfAbsent(item, 0);
        map.put(item, map.get(item) + amount);
    }

    public void increment(K item) {
        increment(item, 1);
    }

    public int get(K item) {
        return map.getOrDefault(item, 0);
    }

    public Stream<Map.Entry<K, Integer>> stream() {
        return map.entrySet().stream();
    }
}
