package lsm.datastructures.graph;

import java.util.HashMap;
import java.util.function.BiFunction;

public class Node {
    public final HashMap<Node, Edge> edges = new HashMap<>();

    public final String name, data;

    public Node() {
        this(null);
    }

    public Node(String name) {
        this(name, null);
    }

    public Node(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public void addEdge(Node target, double cost) {
        addEdge(new Edge(this, target, cost));
    }

    public void addEdge(Edge edge) {
        edges.put(edge.target, edge);
    }

    public Edge addEdge(Node other, BiFunction<Node, Node, Double> costCalculator) {
        var edge = new Edge(this, other, costCalculator.apply(this, other));
        addEdge(edge);
        return edge;
    }
}
