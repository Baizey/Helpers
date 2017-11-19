package lsm.helpers.utils;

public interface Check<T> {

    boolean isTrue(T item, int index);

    default boolean isFalse(T item, int index) {
        return !isTrue(item, index);
    }

}