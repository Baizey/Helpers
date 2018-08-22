package lsm.datastructures.wrap;

public class SafeWrap<T> extends Wrap<T> {

    @Override
    public Wrap<T> set(T value) {
        return setSync(value);
    }

    private synchronized Wrap<T> setSync(T value) {
        return super.set(value);
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
