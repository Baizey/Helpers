package lsm.algorithms.Astar;

import lsm.helpers.Note;

import java.util.ArrayList;
import java.util.HashMap;

public class AstarTest {
    private static int[] t;
    private static String name1, name2;
    public static void main(String... args){
        HashMap<String, Cell> n = new HashMap<>();
        for( String a : input ){
            t = split(a);
            name1 = t[0] + "_" + t[1];
            name2 = t[2] + "_" + t[3];
            n.computeIfAbsent(name1, (String k) -> Astar.node(name1, t[0], t[1]));
            n.computeIfAbsent(name2, (String k) -> Astar.node(name2, t[2], t[3]));
            Astar.edge(n.get(name1), n.get(name2), 1, a.split(" ")[2]);
        }
        Cell start = n.get("0_0"), goal;
        ArrayList<String> path;
        for (int i = 1; i < 10; i++) {
            goal = n.get(i + "_" + i);
            Astar.solve(start, goal);
            path = Astar.routeFormalPath();
            Note.writenl("\nRoute from 0_0 to " + i + "_" + i);
            Note.writenl("Cost: " + Astar.routeCost());
            for(int j = 1; j < path.size(); j += 2)
                Note.writenl(path.get(j));
        }
    }

    private static int[] split(String in){
        String[] a = in.split(" ");
        return new int[]{Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[3]),Integer.parseInt(a[4])};
    }




    private static String[] input = ("0 0 street_0 1 0\n" +
            "1 0 street_0 0 0\n" +
            "1 0 street_0 2 0\n" +
            "2 0 street_0 1 0\n" +
            "2 0 street_0 3 0\n" +
            "3 0 street_0 2 0\n" +
            "3 0 street_0 4 0\n" +
            "4 0 street_0 3 0\n" +
            "4 0 street_0 5 0\n" +
            "5 0 street_0 4 0\n" +
            "5 0 street_0 6 0\n" +
            "6 0 street_0 5 0\n" +
            "6 0 street_0 7 0\n" +
            "7 0 street_0 6 0\n" +
            "7 0 street_0 8 0\n" +
            "8 0 street_0 7 0\n" +
            "8 0 street_0 9 0\n" +
            "9 0 street_0 8 0\n" +
            "0 0 avenue_0 0 1\n" +
            "0 1 avenue_0 0 0\n" +
            "0 1 avenue_0 0 2\n" +
            "0 2 avenue_0 0 1\n" +
            "0 2 avenue_0 0 3\n" +
            "0 3 avenue_0 0 2\n" +
            "0 3 avenue_0 0 4\n" +
            "0 4 avenue_0 0 3\n" +
            "0 4 avenue_0 0 5\n" +
            "0 5 avenue_0 0 4\n" +
            "0 5 avenue_0 0 6\n" +
            "0 6 avenue_0 0 5\n" +
            "0 6 avenue_0 0 7\n" +
            "0 7 avenue_0 0 6\n" +
            "0 7 avenue_0 0 8\n" +
            "0 8 avenue_0 0 7\n" +
            "0 8 avenue_0 0 9\n" +
            "0 9 avenue_0 0 8\n" +
            "0 1 street_1 1 1\n" +
            "1 1 street_1 0 1\n" +
            "1 0 avenue_1 1 1\n" +
            "1 1 avenue_1 1 0\n" +
            "0 2 street_2 1 2\n" +
            "1 2 street_2 0 2\n" +
            "1 1 avenue_1 1 2\n" +
            "1 2 avenue_1 1 1\n" +
            "0 3 street_3 1 3\n" +
            "1 3 street_3 0 3\n" +
            "1 2 avenue_1 1 3\n" +
            "1 3 avenue_1 1 2\n" +
            "0 4 street_4 1 4\n" +
            "1 4 street_4 0 4\n" +
            "1 3 avenue_1 1 4\n" +
            "1 4 avenue_1 1 3\n" +
            "0 5 street_5 1 5\n" +
            "1 5 street_5 0 5\n" +
            "1 4 avenue_1 1 5\n" +
            "1 5 avenue_1 1 4\n" +
            "0 6 street_6 1 6\n" +
            "1 6 street_6 0 6\n" +
            "1 5 avenue_1 1 6\n" +
            "1 6 avenue_1 1 5\n" +
            "0 7 street_7 1 7\n" +
            "1 7 street_7 0 7\n" +
            "1 6 avenue_1 1 7\n" +
            "1 7 avenue_1 1 6\n" +
            "0 8 street_8 1 8\n" +
            "1 8 street_8 0 8\n" +
            "1 7 avenue_1 1 8\n" +
            "1 8 avenue_1 1 7\n" +
            "0 9 street_9 1 9\n" +
            "1 9 street_9 0 9\n" +
            "1 8 avenue_1 1 9\n" +
            "1 9 avenue_1 1 8\n" +
            "1 1 street_1 2 1\n" +
            "2 1 street_1 1 1\n" +
            "2 0 avenue_2 2 1\n" +
            "2 1 avenue_2 2 0\n" +
            "1 2 street_2 2 2\n" +
            "2 2 street_2 1 2\n" +
            "2 1 avenue_2 2 2\n" +
            "2 2 avenue_2 2 1\n" +
            "1 3 street_3 2 3\n" +
            "2 3 street_3 1 3\n" +
            "2 2 avenue_2 2 3\n" +
            "2 3 avenue_2 2 2\n" +
            "1 4 street_4 2 4\n" +
            "2 4 street_4 1 4\n" +
            "2 3 avenue_2 2 4\n" +
            "2 4 avenue_2 2 3\n" +
            "1 5 street_5 2 5\n" +
            "2 5 street_5 1 5\n" +
            "2 4 avenue_2 2 5\n" +
            "2 5 avenue_2 2 4\n" +
            "1 6 street_6 2 6\n" +
            "2 6 street_6 1 6\n" +
            "2 5 avenue_2 2 6\n" +
            "2 6 avenue_2 2 5\n" +
            "1 7 street_7 2 7\n" +
            "2 7 street_7 1 7\n" +
            "2 6 avenue_2 2 7\n" +
            "2 7 avenue_2 2 6\n" +
            "1 8 street_8 2 8\n" +
            "2 8 street_8 1 8\n" +
            "2 7 avenue_2 2 8\n" +
            "2 8 avenue_2 2 7\n" +
            "1 9 street_9 2 9\n" +
            "2 9 street_9 1 9\n" +
            "2 8 avenue_2 2 9\n" +
            "2 9 avenue_2 2 8\n" +
            "2 1 street_1 3 1\n" +
            "3 1 street_1 2 1\n" +
            "3 0 avenue_3 3 1\n" +
            "3 1 avenue_3 3 0\n" +
            "2 2 street_2 3 2\n" +
            "3 2 street_2 2 2\n" +
            "3 1 avenue_3 3 2\n" +
            "3 2 avenue_3 3 1\n" +
            "2 3 street_3 3 3\n" +
            "3 3 street_3 2 3\n" +
            "3 2 avenue_3 3 3\n" +
            "3 3 avenue_3 3 2\n" +
            "2 4 street_4 3 4\n" +
            "3 4 street_4 2 4\n" +
            "3 3 avenue_3 3 4\n" +
            "3 4 avenue_3 3 3\n" +
            "2 5 street_5 3 5\n" +
            "3 5 street_5 2 5\n" +
            "3 4 avenue_3 3 5\n" +
            "3 5 avenue_3 3 4\n" +
            "2 6 street_6 3 6\n" +
            "3 6 street_6 2 6\n" +
            "3 5 avenue_3 3 6\n" +
            "3 6 avenue_3 3 5\n" +
            "2 7 street_7 3 7\n" +
            "3 7 street_7 2 7\n" +
            "3 6 avenue_3 3 7\n" +
            "3 7 avenue_3 3 6\n" +
            "2 8 street_8 3 8\n" +
            "3 8 street_8 2 8\n" +
            "3 7 avenue_3 3 8\n" +
            "3 8 avenue_3 3 7\n" +
            "2 9 street_9 3 9\n" +
            "3 9 street_9 2 9\n" +
            "3 8 avenue_3 3 9\n" +
            "3 9 avenue_3 3 8\n" +
            "3 1 street_1 4 1\n" +
            "4 1 street_1 3 1\n" +
            "4 0 avenue_4 4 1\n" +
            "4 1 avenue_4 4 0\n" +
            "3 2 street_2 4 2\n" +
            "4 2 street_2 3 2\n" +
            "4 1 avenue_4 4 2\n" +
            "4 2 avenue_4 4 1\n" +
            "3 3 street_3 4 3\n" +
            "4 3 street_3 3 3\n" +
            "4 2 avenue_4 4 3\n" +
            "4 3 avenue_4 4 2\n" +
            "3 4 street_4 4 4\n" +
            "4 4 street_4 3 4\n" +
            "4 3 avenue_4 4 4\n" +
            "4 4 avenue_4 4 3\n" +
            "3 5 street_5 4 5\n" +
            "4 5 street_5 3 5\n" +
            "4 4 avenue_4 4 5\n" +
            "4 5 avenue_4 4 4\n" +
            "3 6 street_6 4 6\n" +
            "4 6 street_6 3 6\n" +
            "4 5 avenue_4 4 6\n" +
            "4 6 avenue_4 4 5\n" +
            "3 7 street_7 4 7\n" +
            "4 7 street_7 3 7\n" +
            "4 6 avenue_4 4 7\n" +
            "4 7 avenue_4 4 6\n" +
            "3 8 street_8 4 8\n" +
            "4 8 street_8 3 8\n" +
            "4 7 avenue_4 4 8\n" +
            "4 8 avenue_4 4 7\n" +
            "3 9 street_9 4 9\n" +
            "4 9 street_9 3 9\n" +
            "4 8 avenue_4 4 9\n" +
            "4 9 avenue_4 4 8\n" +
            "4 1 street_1 5 1\n" +
            "5 1 street_1 4 1\n" +
            "5 0 avenue_5 5 1\n" +
            "5 1 avenue_5 5 0\n" +
            "4 2 street_2 5 2\n" +
            "5 2 street_2 4 2\n" +
            "5 1 avenue_5 5 2\n" +
            "5 2 avenue_5 5 1\n" +
            "4 3 street_3 5 3\n" +
            "5 3 street_3 4 3\n" +
            "5 2 avenue_5 5 3\n" +
            "5 3 avenue_5 5 2\n" +
            "4 4 street_4 5 4\n" +
            "5 4 street_4 4 4\n" +
            "5 3 avenue_5 5 4\n" +
            "5 4 avenue_5 5 3\n" +
            "4 5 street_5 5 5\n" +
            "5 5 street_5 4 5\n" +
            "5 4 avenue_5 5 5\n" +
            "5 5 avenue_5 5 4\n" +
            "4 6 street_6 5 6\n" +
            "5 6 street_6 4 6\n" +
            "5 5 avenue_5 5 6\n" +
            "5 6 avenue_5 5 5\n" +
            "4 7 street_7 5 7\n" +
            "5 7 street_7 4 7\n" +
            "5 6 avenue_5 5 7\n" +
            "5 7 avenue_5 5 6\n" +
            "4 8 street_8 5 8\n" +
            "5 8 street_8 4 8\n" +
            "5 7 avenue_5 5 8\n" +
            "5 8 avenue_5 5 7\n" +
            "4 9 street_9 5 9\n" +
            "5 9 street_9 4 9\n" +
            "5 8 avenue_5 5 9\n" +
            "5 9 avenue_5 5 8\n" +
            "5 1 street_1 6 1\n" +
            "6 1 street_1 5 1\n" +
            "6 0 avenue_6 6 1\n" +
            "6 1 avenue_6 6 0\n" +
            "5 2 street_2 6 2\n" +
            "6 2 street_2 5 2\n" +
            "6 1 avenue_6 6 2\n" +
            "6 2 avenue_6 6 1\n" +
            "5 3 street_3 6 3\n" +
            "6 3 street_3 5 3\n" +
            "6 2 avenue_6 6 3\n" +
            "6 3 avenue_6 6 2\n" +
            "5 4 street_4 6 4\n" +
            "6 4 street_4 5 4\n" +
            "6 3 avenue_6 6 4\n" +
            "6 4 avenue_6 6 3\n" +
            "5 5 street_5 6 5\n" +
            "6 5 street_5 5 5\n" +
            "6 4 avenue_6 6 5\n" +
            "6 5 avenue_6 6 4\n" +
            "5 6 street_6 6 6\n" +
            "6 6 street_6 5 6\n" +
            "6 5 avenue_6 6 6\n" +
            "6 6 avenue_6 6 5\n" +
            "5 7 street_7 6 7\n" +
            "6 7 street_7 5 7\n" +
            "6 6 avenue_6 6 7\n" +
            "6 7 avenue_6 6 6\n" +
            "5 8 street_8 6 8\n" +
            "6 8 street_8 5 8\n" +
            "6 7 avenue_6 6 8\n" +
            "6 8 avenue_6 6 7\n" +
            "5 9 street_9 6 9\n" +
            "6 9 street_9 5 9\n" +
            "6 8 avenue_6 6 9\n" +
            "6 9 avenue_6 6 8\n" +
            "6 1 street_1 7 1\n" +
            "7 1 street_1 6 1\n" +
            "7 0 avenue_7 7 1\n" +
            "7 1 avenue_7 7 0\n" +
            "6 2 street_2 7 2\n" +
            "7 2 street_2 6 2\n" +
            "7 1 avenue_7 7 2\n" +
            "7 2 avenue_7 7 1\n" +
            "6 3 street_3 7 3\n" +
            "7 3 street_3 6 3\n" +
            "7 2 avenue_7 7 3\n" +
            "7 3 avenue_7 7 2\n" +
            "6 4 street_4 7 4\n" +
            "7 4 street_4 6 4\n" +
            "7 3 avenue_7 7 4\n" +
            "7 4 avenue_7 7 3\n" +
            "6 5 street_5 7 5\n" +
            "7 5 street_5 6 5\n" +
            "7 4 avenue_7 7 5\n" +
            "7 5 avenue_7 7 4\n" +
            "6 6 street_6 7 6\n" +
            "7 6 street_6 6 6\n" +
            "7 5 avenue_7 7 6\n" +
            "7 6 avenue_7 7 5\n" +
            "6 7 street_7 7 7\n" +
            "7 7 street_7 6 7\n" +
            "7 6 avenue_7 7 7\n" +
            "7 7 avenue_7 7 6\n" +
            "6 8 street_8 7 8\n" +
            "7 8 street_8 6 8\n" +
            "7 7 avenue_7 7 8\n" +
            "7 8 avenue_7 7 7\n" +
            "6 9 street_9 7 9\n" +
            "7 9 street_9 6 9\n" +
            "7 8 avenue_7 7 9\n" +
            "7 9 avenue_7 7 8\n" +
            "7 1 street_1 8 1\n" +
            "8 1 street_1 7 1\n" +
            "8 0 avenue_8 8 1\n" +
            "8 1 avenue_8 8 0\n" +
            "7 2 street_2 8 2\n" +
            "8 2 street_2 7 2\n" +
            "8 1 avenue_8 8 2\n" +
            "8 2 avenue_8 8 1\n" +
            "7 3 street_3 8 3\n" +
            "8 3 street_3 7 3\n" +
            "8 2 avenue_8 8 3\n" +
            "8 3 avenue_8 8 2\n" +
            "7 4 street_4 8 4\n" +
            "8 4 street_4 7 4\n" +
            "8 3 avenue_8 8 4\n" +
            "8 4 avenue_8 8 3\n" +
            "7 5 street_5 8 5\n" +
            "8 5 street_5 7 5\n" +
            "8 4 avenue_8 8 5\n" +
            "8 5 avenue_8 8 4\n" +
            "7 6 street_6 8 6\n" +
            "8 6 street_6 7 6\n" +
            "8 5 avenue_8 8 6\n" +
            "8 6 avenue_8 8 5\n" +
            "7 7 street_7 8 7\n" +
            "8 7 street_7 7 7\n" +
            "8 6 avenue_8 8 7\n" +
            "8 7 avenue_8 8 6\n" +
            "7 8 street_8 8 8\n" +
            "8 8 street_8 7 8\n" +
            "8 7 avenue_8 8 8\n" +
            "8 8 avenue_8 8 7\n" +
            "7 9 street_9 8 9\n" +
            "8 9 street_9 7 9\n" +
            "8 8 avenue_8 8 9\n" +
            "8 9 avenue_8 8 8\n" +
            "8 1 street_1 9 1\n" +
            "9 1 street_1 8 1\n" +
            "9 0 avenue_9 9 1\n" +
            "9 1 avenue_9 9 0\n" +
            "8 2 street_2 9 2\n" +
            "9 2 street_2 8 2\n" +
            "9 1 avenue_9 9 2\n" +
            "9 2 avenue_9 9 1\n" +
            "8 3 street_3 9 3\n" +
            "9 3 street_3 8 3\n" +
            "9 2 avenue_9 9 3\n" +
            "9 3 avenue_9 9 2\n" +
            "8 4 street_4 9 4\n" +
            "9 4 street_4 8 4\n" +
            "9 3 avenue_9 9 4\n" +
            "9 4 avenue_9 9 3\n" +
            "8 5 street_5 9 5\n" +
            "9 5 street_5 8 5\n" +
            "9 4 avenue_9 9 5\n" +
            "9 5 avenue_9 9 4\n" +
            "8 6 street_6 9 6\n" +
            "9 6 street_6 8 6\n" +
            "9 5 avenue_9 9 6\n" +
            "9 6 avenue_9 9 5\n" +
            "8 7 street_7 9 7\n" +
            "9 7 street_7 8 7\n" +
            "9 6 avenue_9 9 7\n" +
            "9 7 avenue_9 9 6\n" +
            "8 8 street_8 9 8\n" +
            "9 8 street_8 8 8\n" +
            "9 7 avenue_9 9 8\n" +
            "9 8 avenue_9 9 7\n" +
            "8 9 street_9 9 9\n" +
            "9 9 street_9 8 9\n" +
            "9 8 avenue_9 9 9\n" +
            "9 9 avenue_9 9 8").split("\n");
}
