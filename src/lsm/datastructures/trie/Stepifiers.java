package lsm.datastructures.trie;

import java.util.function.Function;

public class Stepifiers {


    public static Stepifier<Character, String> charsFromString() {
        return charsFromString(str -> str);
    }

    public static <T> Stepifier<Character, T> charsFromString(Function<T, String> converter) {
        return obj -> converter.apply(obj).chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public static Stepifier<Character, String> sortedCharsInString() {
        return str -> str.chars().mapToObj(c -> (char) c).sorted().toArray(Character[]::new);
    }
}
