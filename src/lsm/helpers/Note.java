package lsm.helpers;

import static lsm.helpers.Padding.NONE;
import static lsm.helpers.Padding.pad;
import static lsm.helpers.PrimitiveArrays.primitiveToObjectArray;

public class Note {

    private static final String depth = "\t";
    private static final Settings settings = new Settings("[", "]", ", ");

    public static <T> void write(T write) {
        System.out.print(write);
    }

    public static <T> void writenl(T write) {
        System.out.println(write);
    }

    public static void nl() {
        System.out.println();
    }

    // Printing 2D arrays AND newline
    public static <T> void writenl(T[][] arr) {
        writenl(pad(arr, NONE), settings, 0);
    }

    public static <T> void writenl(T[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(arr, paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    private static void writenl(String[][] arr, Settings settings, int depth) {
        write(arr, settings, depth);
        nl();
    }

    // Printing 2D arrays
    public static <T> void write(T[][] arr) {
        write(pad(arr, NONE), settings, 0);
    }

    public static <T> void write(T[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(arr, paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    private static void write(String[][] arr, Settings settings, int depth) {
        if (arr == null) {
            tagwrite("null", depth);
            return;
        }
        String startTag = settings.startTag, endTag = settings.endTag, separator = settings.separator;
        tagwrite(startTag, depth);
        nl();
        int newDepth = depth + 1;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) write(separator);
            writenl(arr[i], settings, newDepth);
        }
        tagwrite(endTag, depth);
    }

    // Printing 1D arrays AND newline
    public static <T> void writenl(T[] arr) {
        writenl(pad(arr, NONE), settings, 0);
    }

    public static <T> void writenl(T[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(arr, paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    private static void writenl(String[] arr, Settings settings, int depth) {
        write(arr, settings, depth);
        nl();
    }

    // Printing 1D arrays
    public static <T> void write(T[] arr) {
        write(pad(arr, NONE), settings, 0);
    }

    public static <T> void write(T[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(arr, paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    private static void write(String[] arr, Settings settings, int depth) {
        if (arr == null) {
            tagwrite("null", depth);
            return;
        }
        String startTag = settings.startTag, endTag = settings.endTag, separator = settings.separator;
        tagwrite(startTag, depth);
        for (int i = 0; i < arr.length; i++) {
            if (i > 0)
                write(separator);
            write(arr[i]);
        }
        write(endTag);
    }

    // Primitive array printing

    public static void writenl(char[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(char[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }


    public static void write(char[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(char[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }


    public static void writenl(char[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(char[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(char[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(char[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }
    public static void writenl(byte[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(byte[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(byte[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(byte[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(byte[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(byte[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(byte[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(byte[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(int[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(int[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(int[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(int[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(int[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(int[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(int[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(int[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(long[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(long[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(long[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(long[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(long[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(long[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(long[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(long[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(double[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(double[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(double[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(double[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(double[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(double[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(double[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(double[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(boolean[][] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(boolean[] arr) {
        writenl(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(boolean[][] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void write(boolean[] arr) {
        write(pad(primitiveToObjectArray(arr), NONE), settings, 0);
    }

    public static void writenl(boolean[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void writenl(boolean[] arr, String separator, String startTag, String endTag, int paddingMode) {
        writenl(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(boolean[][] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    public static void write(boolean[] arr, String separator, String startTag, String endTag, int paddingMode) {
        write(pad(primitiveToObjectArray(arr), paddingMode), new Settings(startTag, endTag, separator), 0);
    }

    private static void tagwrite(String tag, int depth) {
        for (int i = 0; i < depth; i++) write(Note.depth);
        write(tag);
    }

    private static class Settings {
        String startTag, endTag, separator;

        Settings(String startTag, String endTag, String separator) {
            this.startTag = startTag;
            this.endTag = endTag;
            this.separator = separator;
        }
    }

}
