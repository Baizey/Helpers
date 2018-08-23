package lsm.datastructures.trie;

import lsm.datastructures.permutation.Permutations;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @param <Step> each step in the trie, fx a character, so ['a', 'b', 'c'] is a list of steps
 * @param <Item> some possibly non-array version of the steps, fx a string so that "abc" is a 'Item' of steps
 */
public class Trie<Step, Item> {

    private final Node<Step, Item> root;
    private final Stepifier<Step, Item> stepifier;
    private final boolean useAllPermutations;

    public Trie(Stepifier<Step, Item> stepifier) {
        this(stepifier, false);
    }

    public Trie(Stepifier<Step, Item> stepifier, boolean useAllPermutations) {
        this(stepifier, useAllPermutations, new Node<>());
    }

    public Trie(Stepifier<Step, Item> stepifier, boolean useAllPermutations, Node<Step, Item> root) {
        this.root = root;
        this.useAllPermutations = useAllPermutations;
        this.stepifier = stepifier;
    }

    public void addAll(Collection<Item> items) {
        items.forEach(this::add);
    }

    public boolean contains(Item item) {
        return root.contains(item);
    }

    public void remove(Item item) {
        var steps = stepifier.convert(item);
        if (useAllPermutations)
            Permutations.stream(steps).forEach(p -> root.remove(p, item));
        else
            root.remove(steps, item);
    }

    public ArrayList<Item> getItems() {
        return root.getAllItems();
    }

    public void add(Item item) {
        var steps = stepifier.convert(item);
        if (useAllPermutations)
            Permutations.stream(steps).forEach(p -> root.add(p, item));
        else
            root.add(steps, item);
    }

    public Node<Step, Item> get(Item item) {
        return root.get(stepifier.convert(item));
    }

    public void merge(Trie<Step, Item> other) {
        root.merge(other.root);
    }

    public Node<Step, Item> getRoot() {
        return root;
    }

    public Stepifier<Step, Item> getStepifier() {
        return stepifier;
    }

    public boolean isUsingPermutations() {
        return useAllPermutations;
    }
}

