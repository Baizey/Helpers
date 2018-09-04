package lsm.helpers.interfaces;

public interface Merger<From, To> {
    To convert(From a, From b);
}
