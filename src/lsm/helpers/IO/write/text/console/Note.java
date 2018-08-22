package lsm.helpers.IO.write.text.console;

import java.util.List;
import java.util.Set;

/**
 * Static wrapper for Printer
 * Suggested target do .toArray() on Lists/Sets if you want them stylized too
 * Who will even read these comments?
 */
@SuppressWarnings("unused")
public class Note {

    public static void main(String... args){
        Note.tags("<", ">").padding(Padding.CENTER_RIGHTWEIGHT, Padding.VERTICAL);
    }

    private static final Printer printer = new Printer();

    public static Printer mapKeySeparator(String separator) { return printer.mapKeySeparator(separator); }

    public static Printer separator(String separator) { return printer.separator(separator); }

    public static Printer tags(String startTag, String endTag) { return printer.tags(startTag, endTag); }

    public static Printer padding(int mode, int compare) { return printer.padding(mode, compare); }

    public static Printer nl() { return printer.nl(); }

    public static Printer writenl(long[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(double[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(int[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(char[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(byte[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(boolean[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static <T> Printer writenl(T[][] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(long[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(double[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(int[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(char[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(byte[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer writenl(boolean[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static <T> Printer writenl(T[] toWrite) {
        return printer.writenl(toWrite);
    }

    public static <T> Printer writenl(T toWrite) {
        return printer.writenl(toWrite);
    }

    public static Printer write(long[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(double[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(int[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(char[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(byte[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(boolean[][] toWrite) {
        return printer.write(toWrite);
    }

    public static <T> Printer write(T[][] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(long[] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(double[] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(int[] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(char[] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(byte[] toWrite) {
        return printer.write(toWrite);
    }

    public static Printer write(boolean[] toWrite) {
        return printer.write(toWrite);
    }

    public static <T> Printer write(T[] toWrite) {
        return printer.write(toWrite);
    }

    public static <T> Printer write(Set<T> toWrite) {
        return printer.write(toWrite);
    }

    public static <T> Printer write(List<T> toWrite) {
        return printer.write(toWrite);
    }

    public static <T> Printer write(T toWrite) {
        return printer.write(toWrite);
    }
}
