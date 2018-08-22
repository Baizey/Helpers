package lsm.datastructures.wrap;

public class Wrap<T> {
    T value;

    public Wrap() {
        this(null);
    }

    public Wrap(T value) {
        this.value = value;
    }

    public Wrap<T> set(T value) {
        this.value = value;
        return this;
    }

    public T get() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

