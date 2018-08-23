package lsm.datastructures.trie;

import lsm.helpers.interfaces.ToString;

public class Stepifiers {


    public static Stepifier<Character, String> charsFromString() {
        return charsFromString(str -> str);
    }

    public static <T> Stepifier<Character, T> charsFromString(ToString<T> converter) {
        return obj -> converter.convert(obj).chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public static Stepifier<Character, String> sortedCharsInString() {
        return str -> str.chars().mapToObj(c -> (char) c).sorted().toArray(Character[]::new);
    }
}
