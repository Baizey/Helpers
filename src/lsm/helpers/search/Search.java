package lsm.helpers.search;

import lsm.helpers.interfaces.GetNext;
import lsm.helpers.interfaces.IsElement;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;

public class Search {
}

abstract class Algorithm <T> {
    ArrayDeque<T> queue;
    public abstract T get();
    public abstract void add(T element);

    public T solve(ArrayDeque<T> queue, IsElement<T> isSolution, GetNext<T> getNextStates){
        this.queue = queue;
        HashSet<T> seen = new HashSet<>();
        int size = queue.size();
        T curr;
        for(int i = 0; i < size; i++) {
            curr = queue.pollFirst();
            seen.add(curr);
            queue.addLast(curr);
        }

        while((curr = get()) != null) {
            if(isSolution.isTrue(curr))
                return curr;
            List<T> states = getNextStates.getNext(curr);
            for(T next : states) {
                if(seen.contains(next)) continue;
                seen.add(next);
                add(next);
            }
        }
        return null;
    }
}