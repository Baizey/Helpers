package lsm.datastructures.trie;

import lsm.helpers.interfaces.ToString;

public class TrieFactory {

    public static <T> Trie<Character, T> string(ToString<T> converter) {
        return new Trie<>(Stepifiers.charsFromString(converter));
    }

    public static Trie<Character, String> string() {
        return new Trie<>(Stepifiers.charsFromString());
    }

    public static Trie<Character, String> sortedString() {
        return new Trie<>(Stepifiers.sortedCharsInString());
    }

    public static <T> Trie<T, T[]> generic() {
        return new Trie<>(thing -> thing);
    }

}
