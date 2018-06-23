package lsm.algorithms.Trie;

public class Itemizers {

    public static Itemizer<Character, String> charsInString() {
        return str -> str.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public static Itemizer<Character, String> sortedCharsInString() {
        return str -> str.chars().mapToObj(c -> (char) c).sorted().toArray(Character[]::new);
    }
}
