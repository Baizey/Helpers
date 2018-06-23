package lsm.helpers.utils;

public class Wrap<T> {
    T value;

    public Wrap() {
        this(null);
    }

    public Wrap(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean isSame(T value) {
        if (this.value == null)
            return value == null;
        return this.value.equals(value);
    }

    public String toString() {
        return String.valueOf(value);
    }
}

