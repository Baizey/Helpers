package lsm.datastructures.wrap;

public class SafeWrap<T> extends Wrap<T> {

    @Override
    public void set(T value) {
        setSync(value);
    }

    private synchronized void setSync(T value) {
        super.set(value);
    }

    @Override
    public T get() {
        return getSync();
    }

    private synchronized T getSync() {
        return super.get();
    }

    @Override
    public String toString() {
        return toStringSync();
    }

    private synchronized String toStringSync(){
        return super.toString();
    }
}
