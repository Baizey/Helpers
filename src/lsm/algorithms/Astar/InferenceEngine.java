package lsm.algorithms.Astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class InferenceEngine {
    ArrayList<Clause> clauses = new ArrayList<>();
    HashMap<Literal, Boolean> KB = new HashMap<>();
    InferenceEngine(){

    }



    private class Clause{
        HashSet<Literal> positive = new HashSet<>(), negative = new HashSet<>();
        Clause(String in){
            for(String a : in.split(">")){

            }
        }
    }
    private class Literal{
        String str;
        Literal(String str){ this.str = str; }
    }
}
