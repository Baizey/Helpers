package lsm.datastructures.graph;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Edge {

    public final Node source, target;
    public final double cost;
    public final String name;

    public Edge(Node source, Node target) {
        this(source, target, 1D);
    }

    public Edge(Node source, Node target, String name) {
        this(source, target, 1D, name);
    }

    public Edge(Node source, Node target, double cost) {
        this(source, target, cost, null);
    }

    public Edge(Node source, Node target, double cost, String name) {
        this.source = source;
        this.target = target;
        this.cost = cost;
        this.name = name;
    }

}
