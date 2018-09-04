package lsm.datastructures.natural.interfaces;

import lsm.datastructures.natural.Solution;

public interface Crossover<T> {
    Solution<T> create(Solution<T> male, Solution<T> female);
}
