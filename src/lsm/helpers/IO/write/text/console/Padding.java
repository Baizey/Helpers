package lsm.helpers.IO.write.text.console;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Padding {
    public static final int
            NONE = -1,
            LEFT = 0,
            RIGHT = 1,
            CENTER_LEFTWEIGHT = 2,
            CENTER_RIGHTWEIGHT = 3;
    private static final int
            minPadMode = LEFT,
            maxPadMode = CENTER_RIGHTWEIGHT;
    public static final int
            VERTICAL = 0,
            EVERYTHING = 1;
    private static final int
            minCoverMode = VERTICAL,
            maxCoverMode = EVERYTHING;

    private int[] minSize;
    private int mode;

    <T> Padding(Settings settings, T[] array) {
        if(array == null) return;
        mode = settings.paddingMode;
        int compare = settings.compareMode;
        if (mode < minPadMode || mode > maxPadMode) {
            this.mode = -1;
            return;
        }

        minSize = new int[array.length];
        if (compare == EVERYTHING)
            Arrays.fill(minSize, minLength(array));
    }

    <T> Padding(Settings settings, T[][] array) {
        if(array == null) return;
        mode = settings.paddingMode;
        int compare = settings.compareMode;

        if (mode < minPadMode || mode > maxPadMode) {
            this.mode = -1;
            return;
        }
        if (compare < minCoverMode || compare > maxCoverMode)
            compare = VERTICAL;

        minSize = new int[Arrays.stream(array).filter(Objects::nonNull).mapToInt(i -> i.length).max().orElse(0)];
        if (compare == EVERYTHING)
            Arrays.fill(minSize,
                    Arrays.stream(array).filter(Objects::nonNull)
                            .mapToInt(arr -> Arrays.stream(arr)
                                    .map(String::valueOf)
                                    .mapToInt(String::length)
                                    .max().orElse(0))
                            .max().orElse(0));
        else
            for (T[] arr : array)
                if(arr != null)
                    for (int i = 0; i < arr.length; i++)
                        minSize[i] = Math.max(minSize[i], String.valueOf(arr[i]).length());
    }

    private <T> int minLength(T[] array){
        return Arrays.stream(array).map(String::valueOf).mapToInt(String::length).max().orElse(0);
    }

    <T> String pad(T element, int i) {
        String str = String.valueOf(element);
        if (mode == -1) return str;
        int toFill = minSize[i] - str.length();
        if(toFill <= 0) return str;

        String extra = "";
        String pad = "";
        char[] t;

        switch (mode) {
            case CENTER_LEFTWEIGHT:
            case CENTER_RIGHTWEIGHT:
                if ((toFill & 1) == 1)
                    extra = " ";
                toFill /= 2;
            case LEFT:
            case RIGHT:
                t = new char[toFill];
                Arrays.fill(t, ' ');
                pad = new String(t);
        }

        StringBuilder sb = new StringBuilder(minSize[i]);
        switch (mode) {
            case LEFT:
                return sb.append(str).append(pad).toString();
            case RIGHT:
                return sb.append(pad).append(str).toString();
            case CENTER_LEFTWEIGHT:
                return sb.append(pad).append(extra).append(str).append(pad).toString();
            case CENTER_RIGHTWEIGHT:
                return sb.append(pad).append(str).append(extra).append(pad).toString();
            default:
                return str;
        }
    }
}
