package lsm.helpers.IO.write.text.console;

@SuppressWarnings({"unused", "WeakerAccess"})
public class PrimititveArrays {

    static Object[][] primitiveToObjectArray(char[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(char[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

    static Object[][] primitiveToObjectArray(byte[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(byte[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

    static Object[][] primitiveToObjectArray(int[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(int[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

    static Object[][] primitiveToObjectArray(long[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(long[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

    static Object[][] primitiveToObjectArray(double[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(double[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

    static Object[][] primitiveToObjectArray(boolean[][] arr) {
        if (arr == null) return null;
        Object[][] res = new Object[arr.length][];
        for (int i = 0; i < arr.length; i++) res[i] = primitiveToObjectArray(arr[i]);
        return res;
    }

    static Object[] primitiveToObjectArray(boolean[] arr) {
        if (arr == null) return null;
        Object[] res = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[i];
        return res;
    }

}
