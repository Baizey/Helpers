package lsm.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Note {

    private static final String
        depth = "\t",
        startTag = "[",
        endTag = "]",
        seperator = ", ";
    public static final int
        PADDING_NONE = -1,
        PADDING_LEFT = 0,
        PADDING_RIGHT = 1,
        PADDING_CENTER_LEFTWEIGHT = 2,
        PADDING_CENTER = PADDING_CENTER_LEFTWEIGHT,
        PADDING_CENTER_RIGHTWEIGHT = 3;
    private static final int
        maxMode = PADDING_CENTER_RIGHTWEIGHT;

    public static <T> void write(T write){ System.out.print(write); }
    public static <T> void writenl(T write){ System.out.println(write); }
    public static void nl(){ System.out.println(); }

    // Standard array printing
    public static <T> void writenl(T[][][] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void writenl(T[][] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void writenl(T[] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void write(T[][][] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void write(T[][] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void write(T[] arr){
        writenl(arr, seperator, startTag, endTag, PADDING_NONE);
    }

    // Custom array printing
    public static <T> void writenl(T[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }
    public static <T> void writenl(T[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }
    public static <T> void writenl(T[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }
    public static <T> void write(T[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }
    public static <T> void write(T[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }
    public static <T> void write(T[] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(arr, seperator, startTag, endTag, paddingMode, 0, 0);
    }

    // Printing Lists
    public static <T> void write(List<T> arr){
        write(arr.toArray(), seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void writenl(List<T> arr){
        writenl(arr.toArray(), seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void write(List<T> arr, String seperator, String startTag, String endTag, int paddingMode){
        write(arr.toArray(), seperator, startTag, endTag, paddingMode);
    }
    public static <T> void writenl(List<T> arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(arr.toArray(), seperator, startTag, endTag, paddingMode);
    }

    // Printing Sets
    public static <T> void write(Set<T> arr){
        write(arr.toArray(), seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void writenl(Set<T> arr){
        writenl(arr.toArray(), seperator, startTag, endTag, PADDING_NONE);
    }
    public static <T> void write(Set<T> arr, String seperator, String startTag, String endTag, int paddingMode){
        write(arr.toArray(), seperator, startTag, endTag, paddingMode);
    }
    public static <T> void writenl(Set<T> arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(arr.toArray(), seperator, startTag, endTag, paddingMode);
    }

    // Private array printing for depth and width handling
    private static <T> void writenl(T[][][] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        write(arr, seperator, startTag, endTag, paddingMode, minSize, depth);
        nl();
    }
    private static <T> void writenl(T[][] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        write(arr, seperator, startTag, endTag, paddingMode, minSize, depth);
        nl();
    }
    private static <T> void writenl(T[] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        write(arr, seperator, startTag, endTag, paddingMode, minSize, depth);
        nl();
    }
    private static <T> void write(T[][][] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        if(depth == 0 && Numbers.inRange(paddingMode, 0, maxMode)) minSize = minSize(arr);
        tagwrite(startTag, depth);
        nl();
        int newDepth = depth + 1;
        for(T[][] a : arr)
            writenl(a, seperator, startTag, endTag, paddingMode, minSize, newDepth);
        tagwrite(endTag, depth);
    }
    private static <T> void write(T[][] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        if(depth == 0 && Numbers.inRange(paddingMode, 0, maxMode)) minSize = minSize(arr);
        tagwrite(startTag, depth); nl();
        int newDepth = depth + 1;
        for(T[] a : arr)
            writenl(a, seperator, startTag, endTag, paddingMode, minSize, newDepth);
        tagwrite(endTag, depth);
    }
    private static <T> void write(T[] arr, String seperator, String startTag, String endTag, int paddingMode, int minSize, int depth){
        if(depth == 0 && Numbers.inRange(paddingMode, 0, maxMode)) minSize = minSize(arr);
        tagwrite(startTag, depth);
        for(int i = 0; i < arr.length; i++)
            write((i > 0 ? seperator : "") + padding(paddingMode, minSize, arr[i]));
        write(endTag);
    }

    // Primitive array printing
    public static void writenl(char[][][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(char[][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(char[] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(char[][][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(char[][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(char[] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(char[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(char[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(char[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(char[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(char[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(char[] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode); }

    public static void writenl(int[][][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(int[][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(int[] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(int[][][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(int[][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(int[] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(int[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(int[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(int[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(int[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(int[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(int[] arr, String seperator, String startTag, String endTag, int paddingMode){ write(primitive(arr), seperator, startTag, endTag, paddingMode); }

    public static void writenl(long[][][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(long[][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(long[] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(long[][][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(long[][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(long[] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(long[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(long[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(long[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(long[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(long[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(long[] arr, String seperator, String startTag, String endTag, int paddingMode){ write(primitive(arr), seperator, startTag, endTag, paddingMode); }

    public static void writenl(double[][][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(double[][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(double[] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(double[][][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(double[][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(double[] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(double[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(double[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(double[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(double[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(double[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(double[] arr, String seperator, String startTag, String endTag, int paddingMode){ write(primitive(arr), seperator, startTag, endTag, paddingMode); }

    public static void writenl(boolean[][][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(boolean[][] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(boolean[] arr){
        writenl(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(boolean[][][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(boolean[][] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void write(boolean[] arr){
        write(primitive(arr), seperator, startTag, endTag, PADDING_NONE);
    }
    public static void writenl(boolean[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(boolean[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void writenl(boolean[] arr, String seperator, String startTag, String endTag, int paddingMode){
        writenl(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(boolean[][][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(boolean[][] arr, String seperator, String startTag, String endTag, int paddingMode){
        write(primitive(arr), seperator, startTag, endTag, paddingMode);
    }
    public static void write(boolean[] arr, String seperator, String startTag, String endTag, int paddingMode){ write(primitive(arr), seperator, startTag, endTag, paddingMode); }

    // Private utility functions
    private static <T> String padding(int mode, int minSize, T o){
        String str = String.valueOf(o);
        if(!Numbers.inRange(mode, 0, maxMode))
            return str;

        int toFill = minSize - str.length();
        if(toFill <= 0)
            return str;

        String extra = "";
        String pad = "";
        char[] t;
        // Figure out the padding
        switch (mode) {
            case PADDING_CENTER_LEFTWEIGHT: case PADDING_CENTER_RIGHTWEIGHT:
                if (Numbers.isEven(toFill)) extra = " ";
                toFill /= 2;
            case PADDING_LEFT: case PADDING_RIGHT:
                t = new char[toFill];
                Arrays.fill(t, ' ');
                pad = new String(t);
        }
        // Apply padding
        switch (mode) {
            case PADDING_LEFT:                  return str + pad;
            case PADDING_RIGHT:                 return pad + str;
            case PADDING_CENTER_LEFTWEIGHT:     return pad + extra + str + pad; // Same as PADDING_CENTER
            case PADDING_CENTER_RIGHTWEIGHT:    return pad + str + extra + pad;
            default:                            return str;
        }
    }
    private static void tagwrite(String tag, int depth){
        for(int i = 0; i < depth; i++) write(Note.depth); write(tag);
    }
    private static <T> int minSize(T[][][] arr){
        if(arr == null) return 4;
        int min = 0, t;
        for( T[][] a : arr){
            t = minSize(a);
            min = min < t ? t : min;
        }
        return min;
    }
    private static <T> int minSize(T[][] arr){
        if(arr == null) return 4;
        int min = 0, t;
        for( T[] a : arr){
            t = minSize(a);
            min = min < t ? t : min;
        }
        return min;
    }
    private static <T> int minSize(T[] arr){
        if(arr == null) return 4;
        int min = 0, t;
        for( T a : arr){
            t = String.valueOf(a).length();
            min = min < t ? t : min;
        }
        return min;
    }

    private static Character[][][] primitive(char[][][] arr){
        if(arr == null) return null;
        Character[][][] res = new Character[arr.length][][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Character[][] primitive(char[][] arr){
        if(arr == null) return null;
        Character[][] res = new Character[arr.length][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Character[] primitive(char[] arr){
        if(arr == null) return null;
        Character[] res = new Character[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    private static Object[][][] primitive(int[][][] arr){
        if(arr == null) return null;
        Object[][][] res = new Object[arr.length][][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[][] primitive(int[][] arr){
        if(arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[] primitive(int[] arr){
        if(arr == null) return null;
        Object[] res = new Object[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    private static Object[][][] primitive(long[][][] arr){
        if(arr == null) return null;
        Object[][][] res = new Object[arr.length][][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[][] primitive(long[][] arr){
        if(arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[] primitive(long[] arr){
        if(arr == null) return null;
        Object[] res = new Object[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    private static Object[][][] primitive(double[][][] arr){
        if(arr == null) return null;
        Object[][][] res = new Object[arr.length][][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[][] primitive(double[][] arr){
        if(arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[] primitive(double[] arr){
        if(arr == null) return null;
        Object[] res = new Object[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }
    private static Object[][][] primitive(boolean[][][] arr){
        if(arr == null) return null;
        Object[][][] res = new Object[arr.length][][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[][] primitive(boolean[][] arr){
        if(arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for(int i = 0; i < arr.length; i++) res[i] = primitive(arr[i]);
        return res;
    }
    private static Object[] primitive(boolean[] arr){
        if(arr == null) return null;
        Object[] res = new Object[arr.length];
        for(int i = 0; i < arr.length; i++) res[i] = arr[i] ? '1' : '0';
        return res;
    }
}
