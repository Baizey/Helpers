package lsm.helpers.interfaces;

public interface Convert<From, To> {
    To convert(From thing);
}
