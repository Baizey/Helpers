package lsm.helpers;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.interfaces.Action;
import lsm.helpers.utils.Numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Usage:
 * Time.takeTime(Action) given from lambda function initiates time, runs function and then writes time
 * Time.init()  will take current time
 * Time.write() will write the difference since last initialize call
 * Time.reset() will attempt to write time since last init and then re-initialize
 * Time.using(int) defines if the write() function tells in seconds, millis, micros or nano-seconds
 * A name can be given to take time on multiple things at once
 * 'Program' is default name if none is given
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Time {
    private static final BigDecimal
            THOUSAND = BigDecimal.valueOf(1000L),
            MILLION = BigDecimal.valueOf(1000000L),
            BILLION = BigDecimal.valueOf(1000000000L);
    public static final int
            AUTO = -3,
            HOURS = -2,
            MINUTES = -1,
            SECONDS = 0,
            MILLIS = 1,
            MICRO = 2,
            NANO = 3;
    private static int using = SECONDS;
    private static final String defaultName = "Program";
    private static final HashMap<String, Timestamp> starts = new HashMap<>();

    public static void takeTime(Action action) {
        takeTime(defaultName, action);
    }

    public static void takeTime(String name, Action action) {
        Time.init(name);
        action.function();
        Time.write(name);
    }

    public static void using(int unit) {
        if (Numbers.inRange(unit, -3, 3)) Time.using = unit;
    }

    public static void init() {
        init(defaultName);
    }

    public static void init(String name) {
        starts.put(name, new Timestamp(System.nanoTime()));
    }

    public static void write() {
        write(defaultName);
    }

    public static void write(String name) {
        long end = System.nanoTime();
        Timestamp stamp = starts.getOrDefault(name, null);
        if (stamp != null)
            Note.write(name).write(" took ").writenl(stamp.asDisplay(end, using));
    }

    public static void reset() {
        reset(defaultName);
    }

    public static void reset(String name) {
        write(name);
        init(name);
    }
}

class Timestamp {
    private static final BigDecimal
            ONE = BigDecimal.ONE,
            THOUSAND = BigDecimal.valueOf(1000L),
            MILLION = BigDecimal.valueOf(1000000L),
            BILLION = BigDecimal.valueOf(1000000000L),
            BILLION60 = BigDecimal.valueOf(60000000000L);
    private long start;

    Timestamp(long start) {
        this.start = start;
    }

    public String asDisplay(long end, int displayMode) {
        BigDecimal time = BigDecimal.valueOf(end - start);
        BigDecimal divisor = getDivisor(time, displayMode);
        time = time.divide(divisor, 3, RoundingMode.HALF_UP);
        switch (divisor.toString()) {
            default: return null;
            case "1": return time + " nanoseconds";
            case "1000": return time + " microseconds";
            case "1000000": return time + " milliseconds";
            case "1000000000": return time + " seconds";
            case "60000000000": return time + " minutes";
        }
    }

    private BigDecimal getDivisor(BigDecimal time, int displayMode) {
        switch (displayMode) {
            case Time.AUTO:
                if (time.compareTo(THOUSAND) < 0) return BigDecimal.ONE;
                else if (time.compareTo(MILLION) < 0) return THOUSAND;
                else if (time.compareTo(BILLION) < 0) return MILLION;
                else if (time.compareTo(BILLION60) < 0) return BILLION;
                else return BILLION60;
            case Time.NANO: return ONE;
            case Time.MICRO: return THOUSAND;
            case Time.MILLIS: return MILLION;
            case Time.SECONDS: return BILLION;
            case Time.MINUTES: return BILLION60;
            default: return null;
        }
    }
}