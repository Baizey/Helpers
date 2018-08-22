package lsm.datastructures.trie;

public interface Itemizer<Step, Item> {

    Step[] split(Item item);

}
