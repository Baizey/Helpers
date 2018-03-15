package lsm.helpers.utils;

import lsm.helpers.IO.write.text.console.Note;


@SuppressWarnings("WeakerAccess")
public class ArrayUtils {

    public static void main(String... args) {
        Integer[] array = {1, 2, 3, 4, 5};
        Note.writenl(getIndexByValue(array, (e) -> e == 4));
        Note.writenl(getIndexBy(array, (a, b) -> b > a));
    }

    public static <T> int getIndexByValue(T[] array, Identifier<T> identifier) {
        if (array == null) return -1;
        for (int i = 0; i < array.length; i++)
            if (identifier.identify(array[i]))
                return i;
        return -1;
    }

    public static <T> int getIndexBy(T[] array, Comparator<T> comparator) {
        if (array == null || array.length == 0) return -1;
        int result = 0;
        for (int i = 1; i < array.length; i++)
            if (comparator.isBetter(array[result], array[i]))
                result = i;
        return result;
    }

}
