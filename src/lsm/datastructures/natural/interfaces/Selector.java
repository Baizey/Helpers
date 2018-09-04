package lsm.datastructures.natural.interfaces;

import lsm.datastructures.natural.Solution;
import lsm.helpers.interfaces.Convert;

import java.util.ArrayList;

public interface Selector<T> extends Convert<ArrayList<Solution<T>>, Solution<T>> {
}
