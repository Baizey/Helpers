package lsm.datastructures.trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node<Step, Item> {

    private final HashMap<Step, Node<Step, Item>> children = new HashMap<>();
    private int totalChildItems = 0;
    private Set<Item> items = new HashSet<>();

    public void add(Step[] steps, Item item) {
        if (contains(steps, item))
            return;
        var at = this;
        at.totalChildItems++;
        for (var step : steps) {
            at.children.putIfAbsent(step, new Node<>());
            at = at.children.get(step);
            at.totalChildItems++;
        }
        at.items.add(item);
    }

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

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    public boolean hasItems() {
        return items.size() > 0;
    }

    public boolean isEndpoint() {
        return items.size() > 0;
    }

    public boolean contains(Step[] steps, Item item) {
        return contains(steps, item, 0);
    }

    public boolean contains(Step[] steps, Item item, int start) {
        var goal = get(steps, start);
        return goal != null && goal.hasItem(item);
    }

    public HashMap<Step, Node<Step, Item>> getChildren() {
        return children;
    }

    public Item getFirst() {
        return items.stream().findFirst().orElse(null);
    }

    public Set<Item> getItems() {
        return items;
    }

    /**
     * @return count of items source this node (including itself, if it has a items)
     */
    public int getTotalChildItems() {
        return totalChildItems;
    }

    /**
     * Only meant to be called from Trie (cannot change item count of parent(s))
     * @param steps
     * @param item
     */
    void remove(Step[] steps, Item item) {
        if (!contains(steps, item))
            return;
        var at = this;
        for(var step : steps) {
            at.totalChildItems--;
            var child = at.children.get(step);
            if (child.totalChildItems == 1) {
                at.children.remove(step);
                return;
            }
            at = child;
        }
    }

    /**
     * Only meant to be called from Trie (cannot change item count of parent(s))
     * @param other
     */
    void merge(Node<Step, Item> other) {
        totalChildItems -= items.size();
        items.addAll(other.items);
        totalChildItems += items.size();
        for (var key : other.children.keySet()) {
            if (children.containsKey(key)) {
                totalChildItems -= get(key).totalChildItems;
                get(key).merge(other.get(key));
                totalChildItems += get(key).totalChildItems;
            } else {
                children.put(key, other.get(key));
                totalChildItems += other.get(key).totalChildItems;
            }
        }
    }

    public String toString() {
        return toString(0, '-');
    }

    public String toString(char prefix) {
        return toString(0, prefix);
    }

    private String toString(int depth, char prefix) {
        var indent = new char[depth];
        Arrays.fill(indent, prefix);
        StringBuilder sb = new StringBuilder();
        children.forEach((key, value) -> sb.append(new String(indent))
                .append(key)
                .append('\n')
                .append(value.toString(depth + 1, prefix))
                .append('\n'));
        return sb.toString();
    }
}
