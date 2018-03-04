package lsm.helpers.interfaces;

import java.util.List;

public interface GetNext <T> {
    public List<T> getNext(T element);
}
