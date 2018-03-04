package lsm.algorithms.Trie;


import lsm.helpers.IO.write.text.console.Note;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie {
    private char[] order;
    private boolean sort = true;
    private Node root = new Node();
    private HashMap<Character, Integer> orderpos = new HashMap<>();

    // Default sorting is off and no order is set
    public Trie(){ this(false); }
    // If bool is given, order is set and sort is left true
    public Trie(boolean sort){
        this(sort, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'});
    }
    // If order is given, sort is left true
    public Trie(char[] order){
        this.order = order;
        for(int i = 0; i < order.length; i++)
            orderpos.put(order[i], i);
    }

    public Trie(boolean sort, char[] order){
        this(order);
        this.sort = sort;
    }

    // Only works properly when sort is false
    // And then it only finds 'letters' + something
    public ArrayList<String> findWordsContaining(String letters){
        ArrayList<String> words = new ArrayList<>();
        Node root = findNode(letters);
        if(root == null) return words;
        ArrayList<Node> queue = new ArrayList<>(); queue.add(root);
        while(queue.size() > 0){
            root = queue.get(0);
            queue.remove(0);
            if(root.hasWords())
                words.addAll(root.getWords());
            for(Object a : root.getChildren().keySet())
                queue.add(root.get((char)a));
        }
        return words;
    }
    public void addWord(String word){
        addWord(word, root);
    }
    public void addWord(String word, Node at){
        String sort = sortWord(word);
        for(char a : sort.toCharArray())
            at = at.add(a);
        at.setWord(word);
    }
    public Node findNode(String word){
        String sort = sortWord(word);
        Node at = root;
        for(char a : sort.toCharArray()){
            at = at.get(a);
            if(at == null) return null;
        }
        return at;
    }

    private String sortWord(String word){
        if(!sort) return word;
        String sorted = "";
        int[] count = new int[order.length];
        for (char a : word.toCharArray())
            if(orderpos.containsKey(a))
                count[orderpos.get(a)]++;
        for(int i = 0; i < order.length; i++)
            for (int j = 0; j < count[i]; j++)
                sorted += order[i];
        return sorted;
    }
    public Node root(){ return root; }

    public void displayTree(){
        recurseDisplay(root, 0);
    }
    private void recurseDisplay(Node n, int depth){
        for(char key : n.getChildren().keySet()){
            Note.setSeperator("").writenl(new char[]{key});
            recurseDisplay(n.get(key), depth + 1);
        }
    }
}

