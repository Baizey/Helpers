package lsm.helpers.interfaces;

public interface Rule<T> {
    int handle(T var);
}
