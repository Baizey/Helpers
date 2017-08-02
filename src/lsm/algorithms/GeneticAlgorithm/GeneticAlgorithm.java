package lsm.algorithms.GeneticAlgorithm;

import lsm.helpers.Note;

import java.util.ArrayList;

public class GeneticAlgorithm {
    public static void main(String... args) {
        TestPop pop = new TestPop(100, 24, 0.05, 1);

        int i = 0;
        do {
            if (i % 100 == 0) {
                Note.writenl("Generation: " + i);
                Note.writenl("Fittest: " + pop.getFittest().getFitness());
                Note.writenl("~~~~~~~~~~~~~~~~~~~~");
            }
            pop.evolve();
            i++;
        } while (pop.getFittest().getFitness() < Integer.MAX_VALUE);

        Note.writenl(pop.getFittest().getGenes());

    }
}

/**
 * Random Breeding
 */
abstract class RB<T> extends PopulationMultiBreed<T> {

    RB(int size, int geneSize, double mutationRate, int elites) {
        super(size, geneSize, mutationRate, elites);
    }

    @Override
    ArrayList<Individual> breed(Individual a, Individual b) {
        Individual baby = new Individual(false);
        Individual twin = new Individual(false);
        double chance = (double) a.getFitness() / (double) (a.getFitness() + b.getFitness());
        for (int i = 0; i < geneSize; i++) {
            if (Math.random() < chance) {
                baby.setGene(i, a.getGene(i));
                twin.setGene(i, b.getGene(i));
            } else {
                baby.setGene(i, b.getGene(i));
                twin.setGene(i, a.getGene(i));
            }
        }
        return new ArrayList<Individual>(){{ add(baby); add(twin); }};
    }
}

/**
 * Crosspoint Breeding
 */
abstract class CB<T> extends PopulationMultiBreed<T> {

    CB(int size, int geneSize, double mutationRate, int elites) {
        super(size, geneSize, mutationRate, elites);
    }

    ArrayList<Individual> breed(Individual a, Individual b) {
        Individual baby = new Individual(false);
        Individual twin = new Individual(false);
        int split = (int) Math.floor((double) a.getFitness() / (a.getFitness() + b.getFitness()) * geneSize);
        int i;
        for (i = 0; i < split; i++) {
            baby.setGene(i, a.getGene(i));
            twin.setGene(i, b.getGene(i));
        }
        for (; i < geneSize; i++) {
            baby.setGene(i, b.getGene(i));
            twin.setGene(i, a.getGene(i));
        }
        return new ArrayList<Individual>(){{ add(baby); add(twin); }};
    }
}

/**
 * Proportionate Selection
 */
abstract class PS<T> extends RB<T> {

    PS(int size, int geneSize, double mutationRate, int elites) {
        super(size, geneSize, mutationRate, elites);
    }

    @Override
    Individual select() {
        int sum = 0;
        for (int i = 0; i < popSize; i++)
            sum += population.get(i).getFitness();

        int pick = (int) (Math.random() * sum) + 1;

        for (int i = 0, at = population.get(0).getFitness(); i < popSize; i++, at += population.get(i).getFitness())
            if (at >= pick)
                return population.get(i);

        return population.get(popSize - 1);
    }
}

class TestPop extends PS<Integer> {

    TestPop(int size, int geneSize, double mutationRate, int elites) {
        super(size, geneSize, mutationRate, elites);
    }

    @Override
    public void evolve() {
        ArrayList<Individual> nextGen = new ArrayList<>();

        // Sort population
        population.sort((o1, o2) -> o2.getFitness() - o1.getFitness());

        // Save the elite!
        for (int i = 0; i < elites; i++)
            nextGen.add(population.get(i));

        // Breed new peasents
        while (nextGen.size() < popSize)
            breed(select(), select(), nextGen);

        // Mutate EVERYBODY
        for (int i = elites; i < popSize; i++)
            mutate(nextGen.get(i));

        this.population = nextGen;
    }

    @Override
    Integer generateGene() {
        return (int) Math.round(Math.random());
    }

    @Override
    int calcFitness(ArrayList<Integer> solution) {
        StringBuilder sb = new StringBuilder();
        for (int i : solution)
            sb.append(i);
        return Integer.MAX_VALUE - Math.abs(Integer.parseInt(sb.toString(), 2) - 48642);
    }
}