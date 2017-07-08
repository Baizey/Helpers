package lsm.algorithms.Astar;

import java.util.ArrayList;

public abstract class Cell{
    private static int nextID = 0;
    private int id = nextID++, moveToCost, x ,y;
    private double currentCost, estimatedCost, heuristicCost;
    private String name;
    private Cell from;
    private ArrayList<Cell> edges = new ArrayList<>();
    Cell(String name, int moveToCost, int x, int y){ this.name = name; this.moveToCost = moveToCost; this.x = x; this.y = y; }

    abstract boolean isEdge();
    ArrayList<Cell> edges() {
        return edges;
    }
    Cell firstedge(){ return edges.get(0); }
    void addEdge(Cell c){ edges.add(c); }
    void add2WayEdge(Cell c){ addEdge(c); c.addEdge(this); }

    void clearEdges(){ edges = new ArrayList<>(); }
    void movedFrom(Cell from, double heuristicCost){
        this.from = from;
        this.heuristicCost = heuristicCost;
        this.currentCost = from.currentCost() + this.moveToCost;
        this.estimatedCost = this.currentCost + heuristicCost;
    }
    void setAsStartNode(){
        from = null;
        this.currentCost = 0;
        this.heuristicCost = 1;
        this.estimatedCost = 1;
    }

    int x(){ return x; }
    int y(){ return y; }
    Cell from(){ return from; }
    double estimatedCost(){ return estimatedCost; }
    double currentCost() {
        return currentCost;
    }
    double heuristicCost(){ return heuristicCost; }
    String name(){ return name; }
    int id(){ return id; }
    public String toString(){
        return name + " / " + id;
    }
}

class Node extends Cell{
    Node(String name, int moveToCost, int x, int y) {
        super(name, moveToCost, x, y);
    }
    boolean isEdge() {
        return false;
    }
}
class Edge extends Cell{
    Edge(Cell from, Cell to, int moveToCost) {
        super(from.name() + " => " + to.name(), moveToCost, to.x(), to.y());
        addEdge(to);
    }
    Edge(Cell from, Cell to, int moveToCost, String name) {
        super(from.name() + " " + name + " " + to.name(), moveToCost, to.x(), to.y());
        addEdge(to);
    }
    boolean isEdge() {
        return true;
    }
}