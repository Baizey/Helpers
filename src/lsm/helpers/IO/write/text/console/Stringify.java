package lsm.helpers.IO.write.text.console;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
            sb.append(settings.elementSeparator).append(padding.pad(toWrite[i], i));
        sb.append(settings.endTag);
        return sb;
    }

    public <T> StringBuilder asString(Set<T> toWrite) {
        if (toWrite == null) return new StringBuilder("null");
        return asString(toWrite.toArray());
    }

    public <T, K> StringBuilder asString(Map<T, K> toWrite) {
        if (toWrite == null) return new StringBuilder("null");
        return asString(toWrite.entrySet().stream()
                .map(e -> e.getKey() + settings.mapKeySeparator + e.getValue()).toArray());
    }

    public <T> StringBuilder asString(List<T> toWrite) {
        if (toWrite == null) return new StringBuilder("null");
        return asString(toWrite.toArray());
    }

    public <T> StringBuilder asString(T toWrite) {
        return new StringBuilder(String.valueOf(toWrite));
    }

    public Stringify separator(String separator) {
        this.settings.elementSeparator = separator;
        return this;
    }

    public Stringify mapKeySeparator(String mapKeySeparator) {
        this.settings.mapKeySeparator = mapKeySeparator;
        return this;
    }

    public Stringify tags(String startTag, String endTag) {
        this.settings.startTag = startTag;
        this.settings.endTag = endTag;
        return this;
    }

    public Stringify padding(int mode, int compare) {
        this.settings.paddingMode = mode;
        this.settings.compareMode = compare;
        return this;
    }
}

@SuppressWarnings("WeakerAccess")
class Settings {
    String startTag, endTag, elementSeparator, mapKeySeparator;
    int paddingMode;
    public int compareMode;

    Settings() {
        this.compareMode = Padding.VERTICAL;
        this.paddingMode = Padding.NONE;
        this.startTag = "[";
        this.endTag = "]";
        this.elementSeparator = ", ";
        this.mapKeySeparator = " = ";
    }
}