package lsm.helpers.utils;

public class SynchronizedWrap<T> extends Wrap<T>{

    @Override
    public void set(T value) { setSync(value); }
    private synchronized void setSync(T v){ value = v; }

    @Override
    public T get() { return getSync(); }
    private synchronized T getSync(){ return value; }
}
