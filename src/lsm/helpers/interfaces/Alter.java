package lsm.helpers.interfaces;

public interface Alter<T>  {
    T alter(T value) throws Exception;
}
