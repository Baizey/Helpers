package lsm.helpers.interfaces;

public interface ToString<T> extends Convert<T, String> {
    String convert(T value);
}
