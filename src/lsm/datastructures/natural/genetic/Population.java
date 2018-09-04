package lsm.datastructures.natural.genetic;

import lsm.datastructures.natural.Solution;
import lsm.datastructures.natural.interfaces.Crossover;
import lsm.datastructures.natural.interfaces.Fitness;
import lsm.datastructures.natural.interfaces.Mutator;
import lsm.datastructures.natural.interfaces.Selector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population<T> extends lsm.datastructures.natural.Population<T> {

    /*
     * Given population supplier and upper population limit
     */
    public Population(int populationSize,
                      Supplier<Solution<T>> supplier,
                      Selector<T> selector,
                      Crossover<T> crossover,
                      Mutator<T> mutator,
                      Fitness<T> fitness,
                      boolean useElitism) {
        this(Stream.generate(supplier).limit(populationSize),
                selector, crossover, mutator,
                fitness,
                useElitism);
    }

    /*
     * Given population generation stream
     */
    public Population(Stream<Solution<T>> generator,
                      Selector<T> selector,
                      Crossover<T> crossover,
                      Mutator<T> mutator,
                      Fitness<T> fitness,
                      boolean useElitism) {
        this((ArrayList<Solution<T>>) generator.takeWhile(Objects::nonNull).collect(Collectors.toList()),
                selector, crossover, mutator,
                fitness,
                useElitism);
    }

    /*
     * Given population
     */
    public Population(ArrayList<Solution<T>> population,
                      Selector<T> selector,
                      Crossover<T> crossover,
                      Mutator<T> mutator,
                      Fitness<T> fitness,
                      boolean useElitism) {
        super(population,
                p -> mutator.alter(crossover.create(selector.convert(p), selector.convert(p))),
                fitness, useElitism);
    }
}
