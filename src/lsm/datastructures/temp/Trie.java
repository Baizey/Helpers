package lsm.datastructures.temp;

import lsm.datastructures.permutation.Permutations;

import java.util.Collection;

/**
 * @param <Step>  each step in the temp, fx a character, so ['a', 'b', 'c'] is a list of steps
 * @param <Thing> some possibly non-array version of the steps, fx a string so that "abc" is a 'Thing' of steps
 */
public class Trie<Step, Thing> {

    private final Node<Step, Thing> root = new Node<>();
    private final Itemizer<Step, Thing> itemizer;
    private final boolean allPermutations;

    public Trie(Itemizer<Step, Thing> itemizer) {
        this(itemizer, false);
    }

    /**
     * @param itemizer        lambda act target turn a 'thing' into an array of steps
     *                        A common Trie thing would be a string, a common step would be a character
     * @param allPermutations tells if all permutations of a thing should be added.
     */
    public Trie(Itemizer<Step, Thing> itemizer, boolean allPermutations) {
        this.allPermutations = allPermutations;
        this.itemizer = itemizer;
    }

    public static <Step, Thing> Trie of(Itemizer<Step, Thing> itemizer, Collection<Thing> things) {
        return new Trie<>(itemizer) {{
            addAll(things);
        }};
    }

    public void addAll(Collection<Thing> things) {
        for (var thing : things)
            add(thing);
    }

    public boolean contains(Thing thing) {
        var result = root.get(itemizer.split(thing));
        return result != null && result.hasThing(thing);
    }

    public void remove(Thing thing) {
        Step[] steps = itemizer.split(thing);
        if (allPermutations)
            Permutations.stream(steps).forEach(p -> root.remove(p, thing));
        else
            root.remove(steps, thing);
    }

    public void add(Thing thing) {
        add(itemizer.split(thing), thing);
    }


    public void add(Step[] steps, Thing thing) {
        if (allPermutations)
            Permutations.stream(steps).forEach(p -> root.add(p, thing));
        else
            root.add(steps, thing);
    }

    public Node<Step, Thing> get(Thing thing) {
        return get(itemizer.split(thing));
    }

    public Node<Step, Thing> get(Step[] steps) {
        return root.get(steps);
    }

    public void merge(Trie<Step, Thing> other) {
        root.merge(other.root);
    }

    public Node<Step, Thing> getRoot() {
        return root;
    }

    public Itemizer<Step, Thing> getItemizer() {
        return itemizer;
    }

}

