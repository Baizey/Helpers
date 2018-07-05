package lsm.datastructures.permutation;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Goes over every unique permutations of source given array
 * All elements in the array are considered unique
 * <p>
 * Utilizes Steinhaus–Johnson–Trotter algorithm with Even's speedup
 * https://en.wikipedia.org/wiki/Steinhaus-Johnson–Trotter_algorithm
 * Totally not c/p'ed source stack overflow & generified + made stream-able
 *
 * @param <T>
 */

public class Permutations<T> implements Iterator<T[]> {

    private final int length;
    private final T[] original;
    private T[] array;
    private int[] perm, next, dirs;

    public Permutations(Collection<T> collection) {
        this((T[]) collection.toArray());
    }

    public Permutations(T[] array) {
        length = array.length;
        if (length > 20) throw new ArrayStoreException("Permutation count exceeds long values at more than 20 elements");
        this.original = Arrays.copyOf(array, length);
        reset();
    }

    public static <T> Stream<T[]> stream(Collection<T> array) {
        return new Permutations<>(array).stream();
    }

    public static <T> Stream<T[]> stream(T[] array) {
        return new Permutations<>(array).stream();
    }

    public Stream<T[]> stream() {
        return Stream.generate(this::next).takeWhile(Objects::nonNull);
    }

    public void reset() {
        perm = IntStream.range(0, length).toArray();
        dirs = IntStream.iterate(0, i -> -1).limit(length).toArray();
        next = perm;
        array = Arrays.copyOf(original, length);
    }

    public T[] next() {
        if (!hasNext())
            return null;
        next = null;
        return array;
    }

    public boolean hasNext() {
        if (next != null)
            return true;

        var i = IntStream.range(0, length).boxed()
                .filter(index -> dirs[index] != 0)
                .max(Comparator.comparingInt(index -> perm[index])).orElse(-1);

        if (i < 0)
            return false;

        var j = i + dirs[i];
        swap(i, j, dirs);
        swap(i, j, perm);
        swap(i, j, array);

        if (j == 0 || j == length - 1 || perm[j + dirs[j]] > perm[j])
            dirs[j] = 0;

        IntStream.range(0, j)
                .filter(index -> perm[index] > perm[j])
                .forEach(index -> dirs[index] = (index < j) ? 1 : -1);

        next = perm;
        return true;
    }

    private void swap(int i, int j, T[] arr) {
        T v = arr[i];
        arr[i] = arr[j];
        arr[j] = v;
    }

    private void swap(int i, int j, int[] arr) {
        int v = arr[i];
        arr[i] = arr[j];
        arr[j] = v;
    }
}