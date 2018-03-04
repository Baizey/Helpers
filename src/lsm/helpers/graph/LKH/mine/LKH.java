package lsm.helpers.graph.LKH.mine;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.graph.Node;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class LKH {

    public static void main(String... args) {


        Node[] graph = new Node[5];
        for(int i = 0; i < graph.length; i++)
            graph[i] = new Node(i);
        for(int i = 1; i < graph.length; i++)
            graph[i].addEdgeTwoway(graph[i - 1]);
        LKH lkh = new LKH(graph);
        Note.writenl(lkh.route);
    }

    private final int size, lastIndex;
    private Node[] route;
    private final Node[] graph;

    public LKH(Node[] graph) { this(graph, graph.length); }
    public LKH(Node[] graph, int noPathCost) {
        this.size = graph.length;
        this.lastIndex = size - 1;
        this.graph = graph;
        this.route = Arrays.copyOf(graph, size);
        Node.setNoPathCost(noPathCost);
    }

}
