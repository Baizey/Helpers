package lsm.datastructures.graph;

import java.util.HashMap;

public class Node {
    public final HashMap<Node, Edge> edges = new HashMap<>();

    public final String name;

    public Node() {
        this(null);
    }

    public Node(String name) {
        this.name = name;
    }

    public void addEdge(Node target, double cost) {
        addEdge(new Edge(this, target, cost));
    }

    public void addEdge(Edge edge) {
        edges.put(edge.target, edge);
    }
}
