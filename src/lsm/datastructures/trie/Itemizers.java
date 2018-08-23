package lsm.datastructures.trie;

import lsm.helpers.interfaces.ToString;

public class Itemizers {


    public static Stepifier<Character, String> charsInString() {
        return charsInString(str -> str);
    }

    public static <T> Stepifier<Character, T> charsInString(ToString<T> converter) {
        return obj -> converter.convert(obj).chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public static Stepifier<Character, String> sortedCharsInString() {
        return str -> str.chars().mapToObj(c -> (char) c).sorted().toArray(Character[]::new);
    }
}
