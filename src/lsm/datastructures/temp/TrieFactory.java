package lsm.datastructures.temp;

public class TrieFactory {

    public static Trie<Character, String> string() {
        return new Trie<>(Itemizers.charsInString());
    }

    public static Trie<Character, String> sortedString() {
        return new Trie<>(Itemizers.sortedCharsInString());
    }

    public static <T> Trie<T, T[]> generic() {
        return new Trie<>(thing -> thing);
    }

}
