package lsm.helpers.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Node {
    public final int id;
    public int routeId;
    private final HashMap<Node, Integer> edges = new HashMap<>();
    private final ArrayList<Edge> edgeList = new ArrayList<>();
    private static int noPathCost = Integer.MAX_VALUE;

    public static void setNoPathCost(int cost) {
        noPathCost = cost;
    }

    public Node(int id) {
        this.id = id;
        this.routeId = id;
    }

    public void addEdgeOneway(Node node) {
        addEdgeOneway(node, 1);
    }

    public void addEdgeOneway(Node node, int cost) {
        this.edges.put(node, cost);
        this.edgeList.add(new Edge(this, node, cost));
        this.edgeList.sort(Comparator.comparingInt(edge -> edge.cost));
    }

    public void addEdgeTwoway(Node node) {
        addEdgeTwoway(node, 1);
    }

    public void addEdgeTwoway(Node node, int cost) {
        addEdgeOneway(node, cost);
        node.addEdgeOneway(this, cost);
    }

    public HashMap<Node, Integer> getEdgesLookup() {
        return edges;
    }
    public List<Node> getEdgesByDistance(){
        return edgeList.stream().map(edge -> edge.to).collect(Collectors.toList());
    }

    public boolean hasEdge(Node n) {
        return this.edges.containsKey(n);
    }

    public int getCost(Node n) {
        return this.edges.getOrDefault(n, noPathCost);
    }

    public Node getMinimumCostMove(){
        return edgeList.size() > 0 ? edgeList.get(0).to : null;
    }

    public String toString(){
        return String.valueOf(id);
    }
}
