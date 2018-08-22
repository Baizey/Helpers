package lsm.datastructures.trie;

import lsm.datastructures.permutation.Permutations;

import java.util.Collection;

/**
 * @param <Step>  each step in the trie, fx a character, so ['a', 'b', 'c'] is a list of steps
 * @param <Item> some possibly non-array version of the steps, fx a string so that "abc" is a 'Item' of steps
 */
public class Trie<Step, Item> {

    private final Node<Step, Item> root = new Node<>();
    private final Itemizer<Step, Item> itemizer;
    private final boolean useAllPermutations;

    public Trie(Itemizer<Step, Item> itemizer) {
        this(itemizer, false);
    }

    /**
     * @param itemizer        lambda act target turn an 'tem into an array of steps
     *                        A common Trie item would be a string, a common step would be a character
     * @param useAllPermutations tells if all permutations of a thing should be added.
     */
    public Trie(Itemizer<Step, Item> itemizer, boolean useAllPermutations) {
        this.useAllPermutations = useAllPermutations;
        this.itemizer = itemizer;
    }

    public void addAll(Collection<Item> items) {
        for (var item : items)
            add(item);
    }

    public boolean contains(Item item) {
        var result = root.get(itemizer.split(item));
        return result != null && result.hasItem(item);
    }

    public void remove(Item item) {
        Step[] steps = itemizer.split(item);
        if (useAllPermutations)
            Permutations.stream(steps).forEach(p -> root.remove(p, item));
        else
            root.remove(steps, item);
    }

    public void add(Item item) {
        add(itemizer.split(item), item);
    }


    public void add(Step[] steps, Item item) {
        if (useAllPermutations)
            Permutations.stream(steps).forEach(p -> root.add(p, item));
        else
            root.add(steps, item);
    }

    public Node<Step, Item> get(Item item) {
        return get(itemizer.split(item));
    }

    public Node<Step, Item> get(Step[] steps) {
        return root.get(steps);
    }

    public void merge(Trie<Step, Item> other) {
        root.merge(other.root);
    }

    public Node<Step, Item> getRoot() {
        return root;
    }

    public Itemizer<Step, Item> getItemizer() {
        return itemizer;
    }

    public String toString(char prefix) {
        return root.toString(prefix);
    }

    public String toString() {
        return root.toString();
    }

}

