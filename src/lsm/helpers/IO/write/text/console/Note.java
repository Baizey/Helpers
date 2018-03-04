package lsm.helpers.IO.write.text.console;

import java.util.List;
import java.util.Set;

/**
 * Static wrapper for Printer
 * Suggested to do .toArray() on Lists/Sets if you want them stylized too
 * Who will even read these comments?
 */
@SuppressWarnings("unused")
public class Note {

    public static void main(String... args){
        Note.setTags("<", ">").setPadding(Padding.CENTER_RIGHTWEIGHT, Padding.VERTICAL);
    }

    private static final Printer printer = new Printer();

    public static Printer setSeperator(String seperator) { return printer.setSeperator(seperator); }

    public static Printer setTags(String startTag, String endTag) { return printer.setTags(startTag, endTag); }

    public static Printer setPadding(int mode, int compare) { return printer.setPadding(mode, compare); }

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
