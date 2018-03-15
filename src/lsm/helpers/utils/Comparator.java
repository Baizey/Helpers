package lsm.helpers.utils;

public interface Comparator <T> {

    boolean isBetter(T a, T b);
}
