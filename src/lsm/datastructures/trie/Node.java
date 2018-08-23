package lsm.datastructures.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node<Step, Item> {

    private final HashMap<Step, Node<Step, Item>> children = new HashMap<>();
    private int totalItems = 0;
    private Set<Item> items = new HashSet<>();

    /**
     * Public functions
     */
    public Node<Step, Item> get(Step step) {
        return children.get(step);
    }

    public Node<Step, Item> get(Step[] steps) {
        return get(steps, 0);
    }

    public Node<Step, Item> get(Step[] steps, int start) {
        var at = this;
        for (int i = start; i < steps.length; i++)
            if (at == null) return null;
            else at = at.children.get(steps[i]);
        return at;
    }

    public boolean contains(Step[] steps, Item item) {
        return contains(steps, item, 0);
    }

    public boolean contains(Step[] steps, Item item, int start) {
        var goal = get(steps, start);
        return goal != null && goal.contains(item);
    }

    public boolean containsChild(Step step) {
        return children.containsKey(step);
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public boolean hasItems() {
        return items.size() > 0;
    }

    public Set<Item> getItems() {
        return new HashSet<>(items);
    }

    public ArrayList<Item> getAllItems() {
        return getAllItems(new ArrayList<>());
    }

    public Item getFirst() {
        return items.stream().findFirst().orElse(null);
    }

    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Protected functions (should only be done on root nodes)
     */
    void add(Step[] steps, Item item) {
        if (contains(steps, item))
            return;
        var at = this;
        at.totalItems++;
        for (var step : steps) {
            at.children.putIfAbsent(step, new Node<>());
            at = at.children.get(step);
            at.totalItems++;
        }
        at.items.add(item);
    }

    void remove(Step[] steps, Item item) {
        if (!contains(steps, item))
            return;
        var at = this;
        for (var step : steps) {
            at.totalItems--;
            var child = at.children.get(step);
            if (child.totalItems == 1) {
                at.children.remove(step);
                return;
            }
            at = child;
        }
    }

    void merge(Node<Step, Item> other) {
        totalItems -= items.size();
        items.addAll(other.items);
        totalItems += items.size();
        for (var key : other.children.keySet()) {
            if (children.containsKey(key)) {
                totalItems -= get(key).totalItems;
                get(key).merge(other.get(key));
                totalItems += get(key).totalItems;
            } else {
                children.put(key, other.get(key));
                totalItems += other.get(key).totalItems;
            }
        }
    }

    ArrayList<Item> getAllItems(ArrayList<Item> result) {
        result.addAll(items);
        children.values().forEach(c -> c.getAllItems(result));
        return result;
    }
}
