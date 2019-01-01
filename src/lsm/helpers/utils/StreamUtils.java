package lsm.helpers.utils;

import lsm.datastructures.wrap.Wrap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class StreamUtils {

    public static <T> Collector<T, List<List<T>>, List<List<T>>> listsOfSize(int blockSize) {
        return Collector.of(
                ArrayList::new,
                (list, value) -> {
                    var block = (list.isEmpty() ? null : list.get(list.size() - 1));
                    if (block == null || block.size() == blockSize)
                        list.add(block = new ArrayList<>(blockSize));
                    block.add(value);
                },
                (list, value) -> {
                    throw new UnsupportedOperationException("");
                }
        );
    }

    public static <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {
        return distinctBy(keyExtractor, true);
    }

    public static <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor, boolean keepNull) {
        var seen = ConcurrentHashMap.newKeySet();

        if (keepNull) {
            var seenNull = new Wrap<>(false);
            return t -> {
                var k = keyExtractor.apply(t);
                return k != null
                        ? seen.add(k)
                        : (seenNull.get() ? false : seenNull.set(true));
            };
        }

        return t -> {
            var k = keyExtractor.apply(t);
            return k != null && seen.add(k);
        };
    }
}


