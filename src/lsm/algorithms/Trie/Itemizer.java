package lsm.algorithms.Trie;

public interface Itemizer<Step, Thing> {

    Step[] split(Thing thing);

}
