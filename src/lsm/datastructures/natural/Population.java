package lsm.datastructures.natural;

import lsm.datastructures.natural.interfaces.Fitness;
import lsm.helpers.interfaces.Convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Population<T> {

    private int generation = 0;
    private boolean isEvolving = false;
    private final int threads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(threads);
    private final boolean useElitism;
    private final int startIndex;
    private final Convert<ArrayList<Solution<T>>, Solution<T>> evolver;
    private final Fitness<T> fitness;
    private ArrayList<Solution<T>> population;

    /*
     * Given population supplier and upper population limit
     */
    public Population(int populationSize,
                      Supplier<Solution<T>> supplier,
                      Convert<ArrayList<Solution<T>>, Solution<T>> evolver,
                      Fitness<T> fitness,
                      boolean useElitism) {
        this(Stream.generate(supplier).limit(populationSize), evolver, fitness, useElitism);
    }

    /*
     * Given population generation stream
     */
    public Population(Stream<Solution<T>> generator,
                      Convert<ArrayList<Solution<T>>, Solution<T>> evolver,
                      Fitness<T> fitness,
                      boolean useElitism) {
        this((ArrayList<Solution<T>>) generator.takeWhile(Objects::nonNull).collect(Collectors.toList()),
                evolver,
                fitness,
                useElitism);
    }

    /*
     * Given population
     */
    public Population(ArrayList<Solution<T>> population,
                      Convert<ArrayList<Solution<T>>, Solution<T>> evolver,
                      Fitness<T> fitness,
                      boolean useElitism) {
        this.population = population;
        this.useElitism = useElitism;
        this.startIndex = useElitism ? 1 : 0;
        this.evolver = evolver;
        this.fitness = fitness;
    }

    public void evolve() throws Exception {
        var size = size();
        var nextGen = new ArrayList<Solution<T>>(size);
        startEvolving(nextGen);
        nextGen.addAll(evolve( - startIndex));
        stopEvolving(nextGen);
    }

    public void evolveParallel() throws Exception {
        var size = size();
        var nextGen = new ArrayList<Solution<T>>(size);
        startEvolving(nextGen);

        var work = getThreadWork();
        var lock = new CountDownLatch(threads);
        var threadResults = new ArrayList[threads];

        for (var i = 0; i < threads; i++) {
            int min = startIndex + i * work,
                    max = Math.min(size, min + work);
            int finalI = i;
            threadPool.submit(() -> {
                threadResults[finalI] = evolve(max - min);
                lock.countDown();
            });
        }

        while (lock.getCount() > 0)
            lock.await();

        for(int i = 0; i < threads; i++)
            nextGen.addAll(threadResults[i]);

        stopEvolving(nextGen);
    }

    private void startEvolving(ArrayList<Solution<T>> nextGen) throws Exception {
        if (isEvolving)
            throw new Exception("Already evolving");
        isEvolving = true;
        if (useElitism)
            nextGen.add(population.get(0));
        generation++;
    }

    private ArrayList<Solution<T>> evolve(int amount) {
        var nextGen = new ArrayList<Solution<T>>(amount);
        for (int i = 0; i < amount; i++) {
            var newPop = evolver.convert(population);
            newPop.setFitness(fitness.convert(newPop));
            nextGen.add(newPop);
        }
        return nextGen;
    }

    private void stopEvolving(ArrayList<Solution<T>> nextGen) {
        var best = 0;
        for (var i = 1; i < nextGen.size(); i++)
            if (nextGen.get(best).getFitness() < nextGen.get(i).getFitness())
                best = i;
        if (best != 0)
            Collections.swap(nextGen, best, 0);
        population = nextGen;
        isEvolving = false;
    }

    private int getThreadWork() {
        int popPerThread = generationSize() / threads;
        while (popPerThread * threads < generationSize())
            popPerThread++;
        return popPerThread;
    }

    private int generationSize() {
        return size() - startIndex;
    }

    public int size() {
        return population.size();
    }

    public int getGeneration() {
        return generation;
    }
}
