package lsm.helpers.IO;

import java.awt.*;

public class Utils {

    public static boolean isWebsite(String str) {
        return str != null && str.matches("^https?://.*");
    }

    public static Color[][][] greyScale(int[][][] input) {
        Color[][][] colors = new Color[input.length][][];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color[][] greyScale(int[][] input) {
        Color[][] colors = new Color[input.length][];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color[] greyScale(int[] input) {
        Color[] colors = new Color[input.length];
        for (int i = 0; i < colors.length; i++)
            colors[i] = greyScale(input[i]);
        return colors;
    }

    public static Color greyScale(int i) {
        return new Color(i, i, i);
    }

}
