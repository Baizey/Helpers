package lsm.datastructures.trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Node<Step, Thing> {

    private final HashMap<Step, Node<Step, Thing>> children = new HashMap<>();
    private int thingsFromThisNode = 0;
    private Set<Thing> things = new HashSet<>();

    public void add(Step[] steps, Thing thing) {
        if (contains(steps, thing))
            return;
        var at = this;
        at.thingsFromThisNode++;
        for (var step : steps) {
            at.children.putIfAbsent(step, new Node<>());
            at = at.children.get(step);
            at.thingsFromThisNode++;
        }
        at.things.add(thing);
    }

    public Node<Step, Thing> get(Step step) {
        return children.get(step);
    }

    public Node<Step, Thing> get(Step[] steps) {
        return get(steps, 0);
    }

    public Node<Step, Thing> get(Step[] steps, int start) {
        var at = this;
        for (int i = start; i < steps.length; i++)
            if (at == null) return null;
            else at = at.children.get(steps[i]);
        return at;
    }

    public boolean hasThing(Thing thing) {
        return things.contains(thing);
    }

    public boolean hasThings() {
        return things.size() > 0;
    }

    private boolean contains(Step[] steps, Thing thing) {
        var goal = get(steps);
        return goal != null && goal.things.contains(thing);
    }

    public HashMap<Step, Node<Step, Thing>> getChildren() {
        return children;
    }

    public Thing getFirst() {
        return things.stream().findFirst().orElse(null);
    }

    public Set<Thing> getThings() {
        return things;
    }

    /**
     * @return count of things source this node (including itself, if it has a things)
     */
    public int getThingsFromThisNode() {
        return thingsFromThisNode;
    }

    public void remove(Step[] steps, Thing thing) {
        if (!contains(steps, thing)) return;
        remove(steps, 0);
    }

    private void remove(Step[] steps, int i) {
        thingsFromThisNode--;
        if (i == steps.length)
            return;
        children.get(steps[i]).remove(steps, i + 1);
        if (children.get(steps[i]).thingsFromThisNode <= 0)
            children.remove(steps[i]);
    }

    public void merge(Node<Step, Thing> other) {
        thingsFromThisNode -= things.size();
        things.addAll(other.things);
        thingsFromThisNode += things.size();
        for (var key : other.children.keySet()) {
            if (children.containsKey(key)) {
                thingsFromThisNode -= get(key).thingsFromThisNode;
                get(key).merge(other.get(key));
                thingsFromThisNode += get(key).thingsFromThisNode;
            } else {
                children.put(key, other.get(key));
                thingsFromThisNode += other.get(key).thingsFromThisNode;
            }
        }
    }

    public String toString() {
        return toString(0, '\t');
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
