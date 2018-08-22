package lsm.datastructures.wrap;

public class IntWrap extends Wrap<Integer> {

    public IntWrap(Integer value) {
        this((int) value);
    }

    public IntWrap(int value) {
        this.value = value;
    }

    public IntWrap() {
        this(0);
    }

    public Integer inc(){
        return add(1);
    }

    public Integer sub(int i) {
        this.value -= i;
        return value;
    }

    public Integer add(int i) {
        this.value += i;
        return value;
    }

}
