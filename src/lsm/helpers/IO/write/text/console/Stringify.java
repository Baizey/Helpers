package lsm.helpers.IO.write.text.console;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Stringify {
    final Settings settings = new Settings();

    public StringBuilder asString(long[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(double[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(int[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(char[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(byte[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(boolean[][] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public <T> StringBuilder asString(T[][] toWrite) {
        return asString(toWrite, new Padding(settings, toWrite));
    }

    public <T> StringBuilder asString(T[][] toWrite, Padding padding) {
        if (toWrite == null) return null;
        StringBuilder sb = new StringBuilder(settings.startTag);
        if (toWrite.length > 0)
            sb.append('\n').append('\t').append(asString(toWrite[0], padding));
        for (int i = 1; i < toWrite.length; i++)
            sb.append('\n').append('\t').append(asString(toWrite[i], padding));
        sb.append('\n').append(settings.endTag);
        return sb;
    }

    public StringBuilder asString(long[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(double[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(int[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(char[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(byte[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }

    public StringBuilder asString(boolean[] toWrite) {
        return asString(PrimititveArrays.primitiveToObjectArray(toWrite));
    }


    public <T> StringBuilder asString(T[] toWrite) {
        return asString(toWrite, new Padding(settings, toWrite));
    }

    public <T> StringBuilder asString(T[] toWrite, Padding padding) {
        if (toWrite == null) return null;
        StringBuilder sb = new StringBuilder(settings.startTag);
        if (toWrite.length > 0) sb.append(padding.pad(toWrite[0], 0));
        for (int i = 1; i < toWrite.length; i++)
            sb.append(settings.separator).append(padding.pad(toWrite[i], i));
        sb.append(settings.endTag);
        return sb;
    }

    public <T> StringBuilder asString(List<T> toWrite) {
        if (toWrite == null) return new StringBuilder("null");
        return asString(toWrite.toArray());
    }

    public <T> StringBuilder asString(T toWrite) {
        return new StringBuilder(String.valueOf(toWrite));
    }

    public Stringify setSeparator(String seperator) {
        this.settings.separator = seperator;
        return this;
    }

    public Stringify setMapKeyValue(String mapKeyValue) {
        this.settings.mapKeyValue = mapKeyValue;
        return this;
    }

    public Stringify setTags(String startTag, String endTag) {
        this.settings.startTag = startTag;
        this.settings.endTag = endTag;
        return this;
    }

    public Stringify setPadding(int mode, int compare) {
        this.settings.paddingMode = mode;
        this.settings.compareMode = compare;
        return this;
    }
}

@SuppressWarnings("WeakerAccess")
class Settings {
    String startTag, endTag, separator, mapKeyValue;
    int paddingMode;
    public int compareMode;

    Settings() {
        this.compareMode = 0;
        this.paddingMode = -1;
        this.startTag = "[";
        this.endTag = "]";
        this.separator = ", ";
        this.mapKeyValue = " = ";
    }
}