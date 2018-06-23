package lsm.helpers.utils;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.interfaces.Comparator;
import lsm.helpers.interfaces.Identifier;

import java.util.Arrays;


@SuppressWarnings("WeakerAccess")
public class ArrayUtils {

    public static void main(String... args) {
        Integer[] array = {1, 2, 3, 4, 5};
        Note.writenl(getIndexByValue(array, (e) -> e == 4));
        Note.writenl(getIndexBy(array, (a, b) -> a - b));
    }

    public static <T> int getIndexByValue(T[] array, Identifier<T> identifier) {
        if (array == null) return -1;
        for (int i = 0; i < array.length; i++)
            if (identifier.identify(array[i]))
                return i;
        return -1;
    }

    public static <T> int sortedGetIndexByValue(T[] haystack, T needle, Comparator<T> compare) {
        int     min = 0,
                max = haystack.length,
                at;
        while (min != max) {
            at = (max - min) / 2 + min;
            int r = compare.compareTo(needle, haystack[at]);
            if (r == 0) return at;
            if (r < 0) max = at;
            else min = at;
        }
        return -1;
    }

    public static int getIndexBy(int[] array, Comparator<Integer> comparator) {
        return getIndexBy(Arrays.stream(array).boxed().toArray(Integer[]::new), comparator);
    }

    public static <T> int getIndexBy(T[] array, Comparator<T> comparator) {
        if (array == null || array.length == 0) return -1;
        int result = 0;
        for (int i = 1; i < array.length; i++)
            if (comparator.compareTo(array[result], array[i]) > 0)
                result = i;
        return result;
    }

}
