package lsm.datastructures.natural.antcolony.graph;

public class Edge extends lsm.datastructures.graph.Edge {

    double chance = 0D;

    public Edge(Node source, Node target, double cost) {
        super(source, target, cost);
    }

    public void setChance(double chance){
        this.chance = Math.min(1D, Math.max(0D, chance));
    }

}
