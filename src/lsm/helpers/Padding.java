package lsm.helpers;

import lsm.helpers.utils.Numbers;

import java.util.Arrays;

public class Padding {

    @SuppressWarnings("WeakerAccess")
    public static final int
            NONE = -1;

    @SuppressWarnings("WeakerAccess")
    public static final int
        LEFT = 0,
        RIGHT = 1,
        CENTER_LEFTWEIGHT = 2,
        CENTER_RIGHTWEIGHT = 3;
    private static final int
        minPadMode = LEFT,
        maxPadMode = CENTER_RIGHTWEIGHT;

    @SuppressWarnings("WeakerAccess")
    public static final int
        VERTICAL = 0,
        HORIZONTAL = 1,
        EVERYTHING = 2;
    private static final int
        minCoverMode = VERTICAL,
        maxCoverMode = EVERYTHING;

    static <T> String[][] pad(T[][] items, int paddingMode) {
        if (items == null) return null;
        int[] minSize = Numbers.inRange(paddingMode, minPadMode, maxPadMode) ? minSize(items) : null;
        String[][] strings = new String[items.length][];
        for (int i = 0; i < items.length; i++)
            strings[i] = pad(items[i], minSize, paddingMode);
        return strings;
    }

    static <T> String[] pad(T[] items, int paddingMode) {
        return pad(items, null, paddingMode);
    }
    private static <T> String[] pad(T[] items, int[] minSize, int paddingMode) {
        if (items == null) return null;
        if(minSize == null && Numbers.inRange(paddingMode, minPadMode, maxPadMode)) minSize = minSize(items);
        String[] strings = new String[items.length];
        for (int i = 0; i < items.length; i++)
            strings[i] = minSize == null ? String.valueOf(items[i]) : pad(items[i], minSize[i], paddingMode);
        return strings;
    }

    static <T> String pad(T item, int minSize, int mode) {
        String str = String.valueOf(item);
        if (!Numbers.inRange(mode, minPadMode, maxPadMode))
            return str;
        int toFill = minSize - str.length();
        if (toFill <= 0)
            return str;

        String extra = "";
        String pad = "";
        char[] t;
        // Figure out the pad
        switch (mode) {
            case CENTER_LEFTWEIGHT:
            case CENTER_RIGHTWEIGHT:
                if (!Numbers.isEven(toFill)) extra = " ";
                toFill /= 2;
            case LEFT:
            case RIGHT:
                t = new char[toFill];
                Arrays.fill(t, ' ');
                pad = new String(t);
        }
        // Apply pad
        switch (mode) {
            case LEFT:
                return str + pad;
            case RIGHT:
                return pad + str;
            case CENTER_LEFTWEIGHT:
                return pad + extra + str + pad;
            case CENTER_RIGHTWEIGHT:
                return pad + str + extra + pad;
            default:
                return str;
        }
    }

    //////////////////////////////////
    // Utility functions
    //////////////////////////////////

    // Figure minimum size for each column (1D perspective)
    private static<T> int[] minSize(T[][] arr) {
        if (arr == null) return null;
        int[] minSize = new int[longestArray(arr)];
        minSize(arr, minSize);
        return minSize;
    }
    private static<T> void minSize(T[][] arr, int[] minSize) {
        if (arr == null) return;
        for (T[] subarr : arr)
            minSize(subarr, minSize);
    }
    private static<T> int[] minSize(T[] arr) {
        if (arr == null) return null;
        int[] minSize = new int[arr.length];
        minSize(arr, minSize);
        return minSize;
    }
    private static<T> void minSize(T[] arr, int[] minSize) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++)
            minSize[i] = Math.max(String.valueOf(arr[i]).length(), minSize[i]);
    }

    // Figures length of the longest array
    private static<T> int longestArray(T[][] arr) {
        if (arr == null) return 0;
        int max = 0;
        for (T[] subarr : arr)
            if (subarr.length > max)
                max = subarr.length;
        return max;
    }

}
