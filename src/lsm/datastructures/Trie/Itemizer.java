package lsm.datastructures.Trie;

public interface Itemizer<Step, Thing> {

    Step[] split(Thing thing);

}
