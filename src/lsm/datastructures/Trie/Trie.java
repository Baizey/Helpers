package lsm.datastructures.trie;

import lsm.datastructures.permutation.Permutations;

import java.util.Collection;
import java.util.HashMap;

/**
 * @param <Step>  each step in the trie, fx a character, so ['a', 'b', 'c'] is a list of steps
 * @param <Thing> some possibly non-array version of the steps, fx a string so that "abc" is a 'Thing' of steps
 */
public class Trie<Step, Thing> {

    private final Node<Step, Thing> root = new Node<>();
    private final Itemizer<Step, Thing> itemizer;

    /**
     * @param itemizer lambda act target turn a 'thing' into an array of steps
     */
    public Trie(Itemizer<Step, Thing> itemizer) {
        this.itemizer = itemizer;
    }

    public static <Step, Thing> Trie of(Itemizer<Step, Thing> itemizer, Collection<Thing> things) {
        return new Trie<>(itemizer) {{
            addAll(things);
        }};
    }

    public void addAll(Collection<Thing> things) {
        addAll(false, things);
    }

    public void addAll(boolean allPermutations, Collection<Thing> things) {
        for (var thing : things)
            add(allPermutations, thing);
    }

    public void add(Thing thing) {
        add(false, thing);
    }

    public void add(boolean allPermutations, Thing thing) {
        add(itemizer.split(thing), thing, allPermutations);
    }

    public Node get(Thing thing) {
        return get(itemizer.split(thing));
    }

    private void add(Step[] steps, Thing thing, boolean allPermutations) {
        if (!allPermutations)
            root.add(steps, thing);
        else
            Permutations.stream(steps).forEach(p -> root.add(p, thing));
    }

    private Node get(Step[] steps) {
        return root.get(steps);
    }

    public Node getRoot() {
        return root;
    }

    public Itemizer<Step, Thing> getItemizer() {
        return itemizer;
    }

}

class Node<Step, Thing> {

    private final HashMap<Step, Node<Step, Thing>> children = new HashMap<>();
    private int thingsFromThisNode = 0;
    private Thing thing = null;

    public void add(Step[] steps, Thing thing) {
        var goal = get(steps);
        if (goal != null && goal.thing != null)
            return;

        var at = this;
        at.thingsFromThisNode++;
        for (var step : steps) {
            at.children.putIfAbsent(step, new Node<>());
            at = at.children.get(step);
            at.thingsFromThisNode++;
        }
        at.thing = thing;
    }

    public Node get(Step[] steps) {
        var at = this;
        for (Step step : steps)
            if (at == null) return null;
            else at = at.children.get(step);
        return at;
    }

    public HashMap<Step, Node<Step, Thing>> get() {
        return children;
    }

    public Thing getThing() {
        return thing;
    }

    /**
     * @return count of things source this node (including itself, if it has a thing)
     */
    public int getThingsFromThisNode() {
        return thingsFromThisNode;
    }

}

