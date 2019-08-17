package lsm.datastructures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.*;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public void addAllNodes(Collection<Node> nodes) {
        nodes.forEach(this::addNode);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addAllEdges(Collection<Edge> edges) {
        edges.forEach(this::addEdge);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void createEdges(BiFunction<Node, Node, Double> costCalculator) {
        for (int i = 0; i < nodes.size(); i++) {
            var a = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                var b = nodes.get(j);
                var edge = a.addEdge(b, costCalculator);
                addEdge(edge);
                edge = b.addEdge(a, costCalculator);
                addEdge(edge);
            }
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }
}
