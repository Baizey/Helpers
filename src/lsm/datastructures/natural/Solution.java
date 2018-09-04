package lsm.datastructures.natural;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Solution<T>  {

    private ArrayList<T> solution;
    private double fitness = -1D;

    public Solution() {
        this(8);
    }

    public Solution(int initialLength) {
        solution = new ArrayList<>(initialLength);
    }

    public void add(T element) {
        solution.add(element);
    }

    public T get(int index) {
        return solution.get(index);
    }

    public Stream<T> stream() {
        return solution.stream();
    }

    public int size() {
        return solution.size();
    }

    public ArrayList<T> getSolution() {
        return solution;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }
}
