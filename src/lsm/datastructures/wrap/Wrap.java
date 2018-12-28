package lsm.datastructures.wrap;

public class Wrap<T> {
    public T value;

    public Wrap() {
        this(null);
    }

    public Wrap(T value) {
        this.value = value;
    }

    public T set(T value) {
        return (this.value = value);
    }

    public T get() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

