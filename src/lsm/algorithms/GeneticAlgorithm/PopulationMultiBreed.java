package lsm.algorithms.GeneticAlgorithm;

import java.util.ArrayList;

abstract public class PopulationMultiBreed<T> extends Population<T> {

    PopulationMultiBreed(int popSize, int geneSize, double mutationRate, int elites) {
        super(popSize, geneSize, mutationRate, elites);
    }

    /**
     * Function to implement
     * Creating a new population from 2 older pops
     *
     * @param a first individual
     * @param b second individual
     * @return 2 children of a and b
     */
    abstract ArrayList<Individual> breed(Individual a, Individual b);

    /**
     * Same as breed(a, b)
     * Simply adds the new babies to the given population immediatly
     *
     * @param a   first individual
     * @param b   second individual
     * @param pop population to add new born to
     */
    void breed(Individual a, Individual b, ArrayList<Individual> pop) {
        pop.addAll(breed(a, b));
    }
}
