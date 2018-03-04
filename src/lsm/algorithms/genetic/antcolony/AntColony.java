package lsm.algorithms.genetic.antcolony;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.Time;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;

public class AntColony {
    public static void main(String... args) {
        for (int i = 7; i <= 9; i++) {
            Time.init("Dimension: " + i);
            Colony colony = new Colony(i);
            colony.work(10000, 400);
            Time.write("Dimension: " + i);
            Note.writenl("Dimension: " + i + " -> " + (colony.best.size()));
        }
    }
}

class Colony {
    private int dimension;
    private Node start;
    IntList best;

    Colony(int dimension) {
        this.dimension = dimension;
        start = new Node(dimension, new BitSet(dimension));
        HashMap<Integer, Node> nodes = new HashMap<>();
        nodes.put(start.id, start);
        int max = (int) Math.pow(2, dimension);
        // Create nodes in graph
        for (int i = 1; i < max; i++) {
            BitSet id = new BitSet(dimension);
            String bitString = Long.toBinaryString(i);
            if (bitString.length() < dimension) {
                char[] fill = new char[dimension - bitString.length()];
                Arrays.fill(fill, '0');
                bitString = new String(fill) + bitString;
            } else bitString = bitString.substring(bitString.length() - dimension);
            bitString = new StringBuilder(bitString).reverse().toString();
            for (int j = 0; j < dimension; j++)
                id.set(j, bitString.charAt(j) == '1');
            nodes.put(i, new Node(dimension, id));
        }
        // Add edges in graph
        for (Node n : nodes.values()) {
            BitSet id = n.bits;
            for (int i = 0; i < dimension; i++) {
                boolean b = id.get(i);
                id.set(i, !b);
                nodes.get(n.createId()).addEdge(n, i);
                id.set(i, b);
            }
        }
    }

    void work(int iterations, int attempts){
        IntList bestAnt = new IntList(200),
                bestInGen = new IntList(200),
                currAnt = new IntList(200);
        for(int ignore = 0; ignore < iterations; ignore++) {
            bestInGen.clear();
            for(int ignored = 0; ignored < attempts; ignored++) {
                currAnt.clear();
                findPath(currAnt);
                if(bestInGen.size() < currAnt.size())
                    bestInGen.copy(currAnt);
            }
            applyPath(bestInGen);
            if(bestAnt.size() < bestInGen.size()) {
                bestAnt.copy(bestInGen);
                Note.writenl("Generation " + ignore + ": " + bestAnt.size());
            }
        }

        this.best = bestAnt;
    }

    private void applyPath(IntList path){
        int length = path.size();
        Node at = start;
        for (int i = 0; i < length; i++) {
            int e = path.get(i);
            at.alter(e);
            at = at.edges[e];
            at.alter(e);
        }
    }

    private void findPath(IntList curr) {
        HashSet<Integer> blocked = new HashSet<>();
        Node at = start, next = null;
        blocked.add(at.id);

        do {
            double odds = 1.0;
            for (int i = 0; i < dimension; i++)
                if (blocked.contains(at.edges[i].id)) odds -= at.odds[i];
            odds *= Math.random();

            boolean looking = true;

            for (int i = 0; i < dimension; i++) {
                if (blocked.contains(at.edges[i].id)) continue;
                else blocked.add(at.edges[i].id);
                if (!looking) continue;
                odds -= at.odds[i];
                if (odds <= 0) {
                    curr.add(i);
                    next = at.edges[i];
                    looking = false;
                }
            }

            if(!looking) at = next;

        } while (Arrays.stream(at.edges)
                .filter(node -> !blocked.contains(node.id))
                .map(res -> true).findFirst().orElse(false));
    }
}

class Node {
    final BitSet bits;
    final int id;
    final Node[] edges;
    final double[] odds;

    Node(int dimesions, BitSet id) {
        this.bits = id;
        this.id = createId();
        edges = new Node[dimesions];
        odds = new double[dimesions];
        double odd = 1.0 / dimesions;
        Arrays.fill(odds, odd);
    }

    void addEdge(Node node, int i) {
        if (edges[i] != null) return;
        edges[i] = node;
        node.edges[i] = this;
    }

    void alter(int edge) {
        double unassigned = 1;
        for (int i = 0; i < odds.length; i++) {
            if (i != edge) {
                double taking = odds[i] * 0.01;
                odds[edge] += taking;
                odds[i] -= taking;
            }
            unassigned -= odds[i];
        }
        odds[edge] += unassigned;
    }

    int createId() {
        return bits.length() > 0 ? (int) bits.toLongArray()[0] : 0;
    }

    public String toString() {
        String res = String.valueOf(Integer.toBinaryString(id));
        char[] arr = new char[edges.length - res.length()];
        Arrays.fill(arr, '0');
        return new String(arr) + res;
    }
}

class IntList {
    private int size;
    private int[] list;

    IntList(int length){
        list = new int[length];
    }

    void add(int e) {
        list[size++] = e;
    }

    void copy(IntList other) {
        this.size = other.size;
        System.arraycopy(other.list, 0, list, 0, size);
    }

    void clear() {
        size = 0;
    }

    int size(){ return size; }

    int get(int i) {
        return list[i];
    }
}