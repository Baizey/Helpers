package lsm.helpers;

import java.util.HashMap;

public class Counter <K> {
    private final HashMap<K, Integer> counter;

    public Counter(){
        counter = new HashMap<>();
    }
    public Counter(HashMap<K, Integer> map){
        this.counter = map;
    }

    void add (K key){
        add(key, 1);
    }
    void remove (K key){
        add(key, -1);
    }
    void add (K key, int amount) {
        counter.put(key, counter.getOrDefault(key, 0) + amount);
    }

    int get (K key) {
        return counter.getOrDefault(key, 0);
    }
    boolean contains (K key) { return counter.containsKey(key); }
}
