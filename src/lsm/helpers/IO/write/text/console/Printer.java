package lsm.helpers.IO.write.text.console;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public class Printer {
    public static void main(String... args) {
        Note.setTags("[", "]")
                .setSeperator(", ")
                .setPadding(Padding.RIGHT, Padding.VERTICAL)
                .writenl(new int[][]{{1, 2, 3},{11, 22222, 33},{111, 222, 33}});
    }
    private final Stringify stringify = new Stringify();

    public Printer setSeperator(String seperator) {
        this.stringify.setSeparator(seperator);
        return this;
    }
    public Printer setTags(String startTag, String endTag) {
        this.stringify.setTags(startTag, endTag);
        return this;
    }
    public Printer setPadding(int mode, int compare) {
        this.stringify.setPadding(mode, compare);
        return this;
    }
    public Printer nl() {
        System.out.println();
        return this;
    }
    public Printer writenl(long[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(double[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(int[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(char[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(byte[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(boolean[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public <T> Printer writenl(T[][] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(long[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(double[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(int[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(char[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(byte[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public Printer writenl(boolean[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public <T> Printer writenl(T[] toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public <T> Printer writenl(List<T> toWrite) { return writenl(stringify.asString(toWrite)); }
    public <T> Printer writenl(Set<T> toWrite) {
        return writenl(stringify.asString(toWrite));
    }
    public <T> Printer writenl(T toWrite) {
        System.out.println(toWrite);
        return this;
    }
    public Printer write(long[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(double[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(int[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(char[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(byte[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(boolean[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public <T> Printer write(T[][] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(long[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(double[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(int[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(char[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(byte[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public Printer write(boolean[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public <T> Printer write(T[] toWrite) {
        return write(stringify.asString(toWrite));
    }
    public <T> Printer write(List<T> toWrite) { return write(stringify.asString(toWrite)); }
    public <T> Printer write(Set<T> toWrite) {
        return write(stringify.asString(toWrite));
    }
    public <T> Printer write(T toWrite) {
        System.out.print(toWrite);
        return this;
    }
}
