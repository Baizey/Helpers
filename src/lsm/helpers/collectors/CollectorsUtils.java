package lsm.helpers.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class CollectorsUtils {

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
}
