package lsm.algorithms.GeneticAlgorithm;

import java.util.ArrayList;

public abstract class Population<T> {
    // Codes not to be borked with
    // NO fitness evaluation should result in negative numbers!
    public static final int UNKNOWN_FITNESS = -1;
    final int geneSize;

    // These values can be changed at any point without any real consequenses
    double mutationRate;
    int popSize, elites;

    public ArrayList<Individual> population = new ArrayList<>();

    Population(int popSize, int geneSize, double mutationRate, int elites) {
        if (popSize < 2)
            throw new IllegalArgumentException("Must atleast have 2 population");
        if (geneSize < 2)
            throw new IllegalArgumentException("Must have at least 2 gene per individual");
        if (elites < 0)
            throw new IllegalArgumentException("Cannot have negative elites");
        if (elites > popSize)
            throw new IllegalArgumentException("Cannot have more elites than population");

        this.elites = elites;
        this.popSize = popSize;
        this.geneSize = geneSize;
        for (int i = 0; i < popSize; i++)
            population.add(new Individual(true));
        this.mutationRate = mutationRate;
    }

    /**
     * Function to implement
     * function to call to make a new generation
     */
    public abstract void evolve();

    /**
     * Function to implement
     * Function to select individual for breeding
     *
     * @return individual fit for breeding
     */
    abstract Individual select();

    /**
     * Function to implement
     * Used to generate genes for individuals
     *
     * @return random gene of the given gene-pool
     */
    abstract T generateGene();

    /**
     * Function to implement
     *
     * @param solution set of genes from an individual
     * @return integer scoring for individuals fitness
     */
    abstract int calcFitness(ArrayList<T> solution);

    /**
     * Mutates an individual
     *
     * @param individual to be mutated
     */
    void mutate(Individual individual) {
        individual.resetFitness();
        for (int i = 0; i < geneSize; i++)
            if (Math.random() < mutationRate)
                individual.setGene(i, generateGene());
    }

    /**
     * Returns fittest person in population
     *
     * @return individual whoose fittest
     */
    Individual getFittest() {
        Individual fittest = null;
        int best = Integer.MIN_VALUE, curr;
        for (Individual individual : population) {
            if ((curr = individual.getFitness()) > best) {
                best = curr;
                fittest = individual;
            }
        }
        return fittest;
    }

    private static int nextID = 0;

    class Individual {
        final int id = nextID++;
        private int fitness = UNKNOWN_FITNESS;
        private ArrayList<T> genes = new ArrayList<>();

        Individual(boolean generate) {
            for (int i = 0; i < geneSize; i++)
                genes.add(generate ? generateGene() : null);
        }

        public void setGene(int index, T gene) {
            genes.set(index, gene);
        }

        public ArrayList<T> getGenes() {
            return genes;
        }

        public T getGene(int index) {
            return genes.get(index);
        }

        public int getFitness() {
            if (fitness == UNKNOWN_FITNESS)
                fitness = calcFitness(genes);
            return fitness;
        }

        public void resetFitness() {
            this.fitness = UNKNOWN_FITNESS;
        }
    }
}
