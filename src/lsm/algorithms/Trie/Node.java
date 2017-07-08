package lsm.algorithms.Trie;

import java.util.ArrayList;
import java.util.HashMap;

public class Node{
    private ArrayList<String> words;
    private boolean hasWords = false;
    private HashMap<Character, Node> children = new HashMap<>();
    Node add(char a){
        if(!children.containsKey(a))
            children.put(a, new Node());
        return children.get(a);
    }
    void setWord(String word){
        if(words == null) {
            words = new ArrayList<>();
            this.hasWords = true;
        }
        words.add(word);
    }
    public Node get(char a){
        return (children.getOrDefault(a, null));
    }
    public boolean hasWords(){ return hasWords; }
    public ArrayList<String> getWords(){ return words; }
    public HashMap<Character, Node> getChildren(){ return children; }

    public int childCount() {
        return children.size();
    }
}
