package lsm.datastructures.Astar;

public abstract class Heuristic {
    abstract double calc(Cell at, Cell goal);
}
