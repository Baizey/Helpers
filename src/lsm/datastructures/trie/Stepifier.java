package lsm.datastructures.trie;

import java.util.function.Function;

public interface Stepifier<Step, Item> extends Function<Item, Step[]> {

}
