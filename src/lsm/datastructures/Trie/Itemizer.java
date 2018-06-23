package lsm.datastructures.trie;

public interface Itemizer<Step, Thing> {

    Step[] split(Thing thing);

}
