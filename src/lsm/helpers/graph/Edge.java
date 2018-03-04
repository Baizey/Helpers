package lsm.helpers.graph;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Edge {

    public final Node from, to;
    public final int cost;

    public Edge(Node from, Node to, int cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

}
