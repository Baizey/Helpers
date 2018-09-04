package lsm.datastructures.wrap;

public class NumberWrap extends Wrap<Double> {

    public NumberWrap() {
        this(0D);
    }
    public NumberWrap(int value) {
        this((double) value);
    }
    public NumberWrap(long value) {
        this((double) value);
    }
    public NumberWrap(double value) {
        super(value);
    }

    public int inc() {
        return add(1);
    }

    public int set(int i){
        return set((double) i).intValue();
    }
    public long set(long i){
        return set((double) i).longValue();
    }

    public int sub(int i) {
        return (int) sub((double) i);
    }
    public long sub(long i) {
        return (long) sub((double) i);
    }
    public double sub(double i) {
        return add(-i);
    }

    public int add(int i) {
        return (int) add((double) i);
    }
    public long add(long i) {
        return (long) add((double) i);
    }
    public double add(double i) {
        return set(value + i);
    }

    public int getInt(){ return get().intValue(); }
    public long getLong(){ return get().longValue(); }

}
