package lsm.datastructures.time;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.interfaces.Action;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static lsm.datastructures.time.TimeUnit.SECONDS;

public class Time {
    private static final String defaultName = "Program";
    private static final Map<String, Long> times = new ConcurrentHashMap<>();
    private static TimeUnit using = SECONDS;

    private Time() {
    }

    public static void takeTime(Action action) throws Exception {
        takeTime(defaultName, action);
    }

    public static void takeTime(String name, Action action) throws Exception {
        Time.start(name);
        action.act();
        Time.write(name);
    }

    public static void using(TimeUnit unit) {
        using = unit;
    }

    public static void start() {
        start(defaultName);
    }

    public static void start(String name) {
        times.put(name, (System.nanoTime()));
    }

    public static void write() {
        write(defaultName);
    }

    public static BigDecimal get() {
        return get(defaultName);
    }

    public static BigDecimal get(String name) {
        var end = System.nanoTime();
        var start = times.getOrDefault(name, null);
        if (start != null)
            return asValue(start, end, using);
        return null;
    }

    public static void write(String name) {
        var end = System.nanoTime();
        var start = times.getOrDefault(name, null);
        if (start != null)
            Note.write(name).write(" took ").writenl(asDisplay(start, end, using));
    }

    public static void writeAndReset() {
        writeAndReset(defaultName);
    }

    public static void writeAndReset(String name) {
        write(name);
        start(name);
    }

    private static BigDecimal asValue(long startTime, long endTime, TimeUnit timeUnit) {
        var time = BigDecimal.valueOf(endTime - startTime);
        return time.divide(timeUnit.getDivisor(time), 3, RoundingMode.HALF_UP);
    }

    private static String asDisplay(long startTime, long endTime, TimeUnit timeUnit) {
        var time = BigDecimal.valueOf(endTime - startTime);
        timeUnit = timeUnit.resolve(time);
        time = time.divide(timeUnit.getDivisor(), 3, RoundingMode.HALF_UP);
        return time + " " + timeUnit.getName();
    }
}

