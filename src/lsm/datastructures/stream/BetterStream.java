package lsm.datastructures.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static lsm.helpers.utils.StreamUtils.distinctBy;
import static lsm.helpers.utils.StreamUtils.listsOfSize;

@SuppressWarnings({"unused", "WeakerAccess"})
public class BetterStream<E> {

    private Stream<E> stream;

    private BetterStream(Stream<E> stream) {
        this.stream = stream;
    }

    public static <E> BetterStream<E> of(Collection<E> collection) {
        return of(collection.stream());
    }

    public static BetterStream<Integer> of(IntStream stream) {
        return of(stream.boxed());
    }

    public static BetterStream<Long> of(LongStream stream) {
        return of(stream.boxed());
    }

    public static BetterStream<Double> of(DoubleStream stream) {
        return of(stream.boxed());
    }

    public static <E> BetterStream<E> of(Stream<E> stream) {
        return new BetterStream<>(stream);
    }

    public static <E> BetterStream<E> iterate(E seed, Predicate<? super E> hasNext, UnaryOperator<E> next) {
        return of(Stream.iterate(seed, hasNext, next));
    }

    public static <E> BetterStream<E> iterate(E seed, UnaryOperator<E> function) {
        return of(Stream.iterate(seed, function));
    }

    public static <E> BetterStream<E> generate(Supplier<? extends E> supplier) {
        return of(Stream.generate(supplier));
    }

    public static <E> BetterStream<E> empty() {
        return of(Stream.empty());
    }

    public static <E> BetterStream<E> of(E element) {
        return of(Stream.of(element));
    }

    public static <E> BetterStream<E> of(E... elements) {
        return of(Stream.of(elements));
    }

    public BetterStream<E> concat(BetterStream<E> other) {
        return concat(other.stream);
    }

    public BetterStream<E> concat(Stream<E> other) {
        stream = Stream.concat(stream, other);
        return this;
    }

    public BetterStream<E> prefix(BetterStream<E> other) {
        return prefix(other.stream);
    }

    public BetterStream<E> prefix(Stream<E> other) {
        stream = Stream.concat(other, stream);
        return this;
    }

    public <O> BetterStream<O> alter(Function<? super E, ? extends O> mapper) {
        return new BetterStream<>(stream.map(mapper));
    }

    public IntStream alterToInt() {
        return alterToInt(i -> (int) i);
    }

    public IntStream alterToInt(ToIntFunction<? super E> mapper) {
        return stream.mapToInt(mapper);
    }

    public LongStream alterToLong() {
        return alterToLong(i -> (long) i);
    }

    public LongStream alterToLong(ToLongFunction<? super E> mapper) {
        return stream.mapToLong(mapper);
    }

    public DoubleStream alterToDouble() {
        return alterToDouble(i -> (double) i);
    }

    public DoubleStream alterToDouble(ToDoubleFunction<? super E> mapper) {
        return stream.mapToDouble(mapper);
    }

    public <O> BetterStream<O> flatMap(Function<? super E, ? extends Stream<? extends O>> mapper) {
        return new BetterStream<>(stream.flatMap(mapper));
    }

    public BetterStream<E> distinct() {
        stream = stream.distinct();
        return this;
    }

    public BetterStream<E> distinct(Function<? super E, ?> keyExtractor) {
        return distinct(keyExtractor, true);
    }

    ;

    public BetterStream<E> distinct(Function<? super E, ?> keyExtractor, boolean keepNull) {
        return keep(distinctBy(keyExtractor, keepNull));
    }

    public BetterStream<E> keep(Predicate<E> predicate) {
        stream = stream.filter(predicate);
        return this;
    }

    public BetterStream<E> skip(Predicate<E> predicate) {
        return keep(predicate.negate());
    }

    public BetterStream<E> skipNull(Function<? super E, ?> keyExtractor) {
        return keep(e -> Objects.nonNull(keyExtractor.apply(e)));
    }

    public BetterStream<E> skipNull() {
        return keep(Objects::nonNull);
    }

    public BetterStream<E> peek(Consumer<? super E> action) {
        stream = stream.peek(action);
        return this;
    }

    public BetterStream<E> limit(long maxSize) {
        stream = stream.limit(maxSize);
        return this;
    }

    public BetterStream<E> skip(long amount) {
        stream = stream.skip(amount);
        return this;
    }

    /*
     * Stream terminating functions
     */
    public <O> O collect(Supplier<O> supplier, BiConsumer<O, ? super E> accumulator, BiConsumer<O, O> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    public <O, A> O collect(Collector<? super E, A, O> collector) {
        return stream.collect(collector);
    }

    public Optional<E> reduce(BinaryOperator<E> operator) {
        return stream.reduce(operator);
    }

    public <O> O reduce(O identity, BiFunction<O, ? super E, O> accumulator, BinaryOperator<O> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    public E reduce(E identity, BinaryOperator<E> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    public Optional<E> min(Comparator<? super E> comparator) {
        return stream.min(comparator);
    }

    public Optional<E> max(Comparator<? super E> comparator) {
        return stream.max(comparator);
    }

    public Optional<E> findFirst() {
        return stream.findFirst();
    }

    public Optional<E> findAny() {
        return stream.findAny();
    }

    public void forEach(Consumer<? super E> action) {
        stream.forEach(action);
    }

    public void forEachOrdered(Consumer<? super E> action) {
        stream.forEachOrdered(action);
    }

    public long count() {
        return stream.count();
    }

    public boolean anyMatch(Predicate<? super E> predicate) {
        return stream.anyMatch(predicate);
    }

    public boolean allMatch(Predicate<? super E> predicate) {
        return stream.allMatch(predicate);
    }

    public boolean noneMatch(Predicate<? super E> predicate) {
        return stream.noneMatch(predicate);
    }

    /*
     * Sorting related functions
     */
    public BetterStream<E> sorted() {
        stream = stream.sorted();
        return this;
    }

    public BetterStream<E> sorted(Comparator<? super E> comparator) {
        stream = stream.sorted(comparator);
        return this;
    }

    public BetterStream<E> unordered() {
        stream = stream.unordered();
        return this;
    }

    public BetterStream<List<E>> collectInLists(int listSizes) {
        return BetterStream.of(collect(listsOfSize(listSizes)));
    }

    public String toString() {
        return toString(", ");
    }

    public String toString(CharSequence seperator) {
        return toString(seperator, "", "");
    }

    public String toString(CharSequence seperator, CharSequence prefix, CharSequence suffix) {
        return stream.map(String::valueOf).collect(Collectors.joining(seperator, prefix, suffix));
    }

    /*
     * Transformations to other structures
     */
    public E[] toArray(IntFunction<E[]> function) {
        return stream.toArray(function);
    }

    public Object[] toArray() {
        return stream.toArray();
    }

    public Iterator<E> iterator() {
        return stream.iterator();
    }

    public Spliterator<E> spliterator() {
        return stream.spliterator();
    }

    public <K> Map<? extends K, List<E>> groupBy(Function<? super E, ? extends K> keyMapper) {
        return collect(Collectors.groupingBy(keyMapper));
    }

    public <K> Map<K, E> toMap(Function<? super E, ? extends K> keyMapper) {
        return toMap(keyMapper, e -> e);
    }

    public <K, V> Map<K, V> toMap(Function<? super E, ? extends K> keyMapper,
                                  Function<? super E, ? extends V> valueMapper) {
        return collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public <K> Map<K, E> toConcurrentMap(Function<? super E, ? extends K> keyMapper) {
        return toConcurrentMap(keyMapper, e -> e);
    }

    public <K, V> Map<K, V> toConcurrentMap(Function<? super E, ? extends K> keyMapper,
                                            Function<? super E, ? extends V> valueMapper) {
        return collect(Collectors.toConcurrentMap(keyMapper, valueMapper));
    }

    public Set<E> toSet() {
        return collect(Collectors.toSet());
    }

    public List<E> toList() {
        return collect(Collectors.toList());
    }

    public Stream<E> getRaw() {
        return stream;
    }

    /*
     * Parallel functions
     */
    public boolean isParallel() {
        return stream.isParallel();
    }

    public BetterStream<E> sequential() {
        stream = stream.sequential();
        return this;
    }

    public BetterStream<E> parallel() {
        stream = stream.parallel();
        return this;
    }

    /*
     * Close functionality
     */
    public BetterStream<E> onClose(Runnable closeHandler) {
        stream = stream.onClose(closeHandler);
        return this;
    }

    public void close() {
        stream.close();
    }
}
