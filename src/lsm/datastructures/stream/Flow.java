package lsm.datastructures.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static lsm.helpers.utils.StreamUtils.distinctBy;
import static lsm.helpers.utils.StreamUtils.listsOfSize;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Flow<E> {

    private final Stream<E> stream;

    private Flow(Stream<E> stream) {
        this.stream = stream;
    }

    public static <E> Flow<E> of(Collection<E> collection) {
        return of(collection.stream());
    }

    public static Flow<Integer> of(IntStream stream) {
        return of(stream.boxed());
    }

    public static Flow<Long> of(LongStream stream) {
        return of(stream.boxed());
    }

    public static Flow<Double> of(DoubleStream stream) {
        return of(stream.boxed());
    }

    public static <E> Flow<E> of(Stream<E> stream) {
        return new Flow<>(stream);
    }

    public static <E> Flow<E> iterate(E seed, Predicate<? super E> hasNext, UnaryOperator<E> next) {
        return of(Stream.iterate(seed, hasNext, next));
    }

    public static <E> Flow<E> iterate(E seed, UnaryOperator<E> function) {
        return of(Stream.iterate(seed, function));
    }

    public static <E> Flow<E> generate(Supplier<? extends E> supplier) {
        return of(Stream.generate(supplier));
    }

    public static <E> Flow<E> empty() {
        return of(Stream.empty());
    }

    public static <E> Flow<E> of(E element) {
        return of(Stream.of(element));
    }

    public static <E> Flow<E> of(E... elements) {
        return of(Stream.of(elements));
    }

    public Flow<E> concat(Flow<E> other) {
        return concat(other.stream);
    }

    public Flow<E> concat(Stream<E> other) {
        return Flow.of(Stream.concat(stream, other));
    }

    public Flow<E> prefix(Flow<E> other) {
        return prefix(other.stream);
    }

    public Flow<E> prefix(Stream<E> other) {
        return Flow.of(Stream.concat(other, stream));
    }

    public <O> Flow<O> modify(Function<? super E, ? extends O> mapper) {
        return Flow.of(stream.map(mapper));
    }

    public IntStream asIntStream() {
        return asIntStream(i -> (int) i);
    }

    public IntStream asIntStream(ToIntFunction<? super E> mapper) {
        return stream.mapToInt(mapper);
    }

    public LongStream asLongStream() {
        return asLongStream(i -> (long) i);
    }

    public LongStream asLongStream(ToLongFunction<? super E> mapper) {
        return stream.mapToLong(mapper);
    }

    public DoubleStream asDoubleStream() {
        return asDoubleStream(i -> (double) i);
    }

    public DoubleStream asDoubleStream(ToDoubleFunction<? super E> mapper) {
        return stream.mapToDouble(mapper);
    }

    public <O> Flow<O> flatMap(Function<? super E, ? extends Stream<? extends O>> mapper) {
        return new Flow<>(stream.flatMap(mapper));
    }

    public Flow<E> distinct() {
        return Flow.of(stream.distinct());
    }

    public Flow<E> distinct(Function<? super E, ?> keyExtractor) {
        return distinct(keyExtractor, true);
    }

    public Flow<E> distinct(Function<? super E, ?> keyExtractor, boolean keepNull) {
        return keep(distinctBy(keyExtractor, keepNull));
    }

    public Flow<E> keep(Predicate<E> predicate) {
        return Flow.of(stream.filter(predicate));
    }

    public Flow<E> skip(Predicate<E> predicate) {
        return keep(predicate.negate());
    }

    public Flow<E> skipNull(Function<? super E, ?> keyExtractor) {
        return keep(e -> Objects.nonNull(keyExtractor.apply(e)));
    }

    public Flow<E> skipNull() {
        return keep(Objects::nonNull);
    }

    public Flow<E> peek(Consumer<? super E> action) {
        return Flow.of(stream.peek(action));
    }

    public Flow<E> limit(long maxSize) {
        return Flow.of(stream.limit(maxSize));
    }

    public Flow<E> skip(long amount) {
        return Flow.of(stream.skip(amount));
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
    public Flow<E> sorted() {
        return Flow.of(stream.sorted());
    }

    public Flow<E> sorted(Comparator<? super E> comparator) {
        return Flow.of(stream.sorted(comparator));
    }

    public Flow<E> unordered() {
        return Flow.of(stream.unordered());
    }

    public Flow<List<E>> collectInLists(int listSizes) {
        return Flow.of(collect(listsOfSize(listSizes)));
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

    public Stream<E> asStream() {
        return stream;
    }

    /*
     * Parallel functions
     */
    public boolean isParallel() {
        return stream.isParallel();
    }

    public Flow<E> sequential() {
        return Flow.of(stream.sequential());
    }

    public Flow<E> parallel() {
        return Flow.of(stream.parallel());
    }

    /*
     * Close functionality
     */
    public Flow<E> onClose(Runnable closeHandler) {
        return Flow.of(stream.onClose(closeHandler));
    }

    public void close() {
        stream.close();
    }
}
