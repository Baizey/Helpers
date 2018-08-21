package lsm.datastructures.temp;

public interface Itemizer<Step, Thing> {

    Step[] split(Thing thing);

}
