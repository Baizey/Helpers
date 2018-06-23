package lsm.datastructures.Astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Astar {
    private static ArrayList<Cell> cells = new ArrayList<>();
    public static final Heuristic
        MANHATTEN = new Heuristic(){ double calc(Cell at, Cell goal){  return Math.abs(at.x() - goal.x()) + Math.abs(at.y() - goal.y()); } },
        DISTANCE = new Heuristic(){ double calc(Cell at, Cell goal){   return Math.sqrt(Math.pow(at.x() - goal.x(), 2) + Math.pow(at.y() - goal.y(), 2)); } },
        QUADTILE = new Heuristic(){ double calc(Cell at, Cell goal){   return Math.max(Math.abs(at.x() - goal.x()), Math.abs(at.y() - goal.y())); } },
        DIJKSTRA = new Heuristic() { double calc(Cell at, Cell goal) { return 0.0; } }; // Who needs heuristic anyway, right?
    public static final CellComparator
        MOVEDHEURESTIC = new CellComparator() { public int compare(Cell x, Cell y) {
            return ( (temp = x.estimatedCost() - y.estimatedCost()) > 0.0 ? 1 : (temp < 0.0 ? -1 : 0));
        } },
        HEURISTIC = new CellComparator() {      public int compare(Cell x, Cell y) {
            return ( (temp = x.heuristicCost() - y.heuristicCost()) > 0.0 ? 1 : (temp < 0.0 ? -1 : 0));
        } },
        MOVEDCOST = new CellComparator() {      public int compare(Cell x, Cell y) {
            return ( (temp = x.currentCost() - y.currentCost()) > 0.0 ? 1 : (temp < 0.0 ? -1 : 0));
        } };

    public static void edge (Cell from, Cell to, int moveAcrossCost) {
        Cell edge = new Edge(from, to, moveAcrossCost);
        from.addEdge(edge);
        cells.add(edge);
    }
    public static void edge (Cell from, Cell to, int moveAcrossCost, String name) {
        Cell edge = new Edge(from, to, moveAcrossCost, name);
        from.addEdge(edge);
        cells.add(edge);
    }


    public static void edge2Way (Cell a, Cell b, int moveAcrossCost) {
        edge(a, b, moveAcrossCost);
        edge(b, a, moveAcrossCost);
    }
    public static Cell node (String name, int x, int y) { cells.add(new Node(name, 0, x, y)); return cells.get(cells.size() - 1); }
    public static Cell node (String name, int moveToCost, int x, int y) { cells.add(new Node(name, moveToCost, x, y)); return cells.get(cells.size() - 1); }
    public static void clearCells(){
        for(Cell a : cells)
            a.clearEdges();
        cells = new ArrayList<>();
    }

    public static boolean solve( Cell start, Cell goal){
        return solve(start, goal, Astar.DISTANCE);
    }
    public static boolean solve(Cell start, Cell goal, Heuristic heuristic){
        return solve(start, goal, heuristic, MOVEDHEURESTIC);
    }
    private static Cell goal = null;
    public static boolean solve(Cell start, Cell goal, Heuristic heuristic, CellComparator comp ){
        if (heuristic == null || comp == null | goal == null || start == null) return false;
        Astar.goal = goal;
        HashSet<Integer> visited = new HashSet<>();
        PriorityQueue<Cell> frontier = new PriorityQueue<>(comp);
        visited.add(start.id());
        frontier.add(start);
        start.setAsStartNode();
        Cell curr;
        while( (curr = frontier.poll()) != null ){
            // Handles edges
            if( curr.isEdge() ){
                if(visited.contains(curr.firstedge().id()))
                    continue;
                curr.firstedge().movedFrom(curr, curr.heuristicCost());
                curr = curr.firstedge();
                visited.add(curr.id());
                if( curr.id() == goal.id() )
                    return true;
            }
            // Adds new states
            for( Cell next : curr.edges() )
                if( !visited.contains(next.id()) ){
                    next.movedFrom(curr, heuristic.calc(next, goal));
                    visited.add(next.id());
                    frontier.add(next);
                }
        }
        return false;
    }
    public static double routeCost(){ return routeCost(goal); }
    public static double routeCost(Cell n) {
        if(n == null) return 0;
        return n.currentCost();
    }
    public static ArrayList<Cell> routePath(){ return routePath(goal); }
    public static ArrayList<Cell> routePath(Cell goal) {
        if(goal == null) return null;
        ArrayList<Cell> res = new ArrayList<>();
        res.add(goal);
        while( (goal = goal.from()) != null ) {
            res.add(0, goal); // Inserting in 'reverse' order since we go through the nodes in reverse order
        }
        return res;
    }
    public static ArrayList<String> routeFormalPath(){ return routeFormalPath(goal); }
    public static ArrayList<String> routeFormalPath(Cell goal) {
        if(goal == null) return null;
        ArrayList<Cell> path = routePath();
        ArrayList<String> res = new ArrayList<>();
        for(Cell a : path)
            res.add(a.name());
        return res;
    }

    ArrayList<Cell> getCells(){
        return new ArrayList<Cell>(cells);
    }


    private abstract static class CellComparator implements Comparator<Cell> {
        double temp;
        public abstract int compare( Cell x, Cell y );
    }

}