package lsm.helpers;

import lsm.helpers.utils.Numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Usage:
 * Time.init()  will take current time
 * Time.write() will write the difference since last initialize call
 * Time.reset() will attempt to write time since last init and then re-initialize
 * Time.using(int) defines if the write() function tells in seconds, millis, micros or nano-seconds
 * A name can be given to take time on multiple things at once
 * 'Program' is default name if none is given
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Time {
    public static final int SECONDS = 0, MILLIS = 1, MICRO = 2, NANO = 3;
    private static int using = SECONDS;

    public static void using(int unit) {
        if (Numbers.inRange(unit, 0, 3)) Time.using = unit;
    }

    private static final String defaultIndex = "Program";
    private static final HashMap<String, Long> starts = new HashMap<>();

    public static void init() {
        init(defaultIndex);
    }

    public static void init(String name) {
        starts.put(name, System.nanoTime());
    }

    public static void write() {
        write(defaultIndex);
    }

    public static void write(String name) {
        long time = getNanos(name);
        if (starts.getOrDefault(name, 0L) == 0L) return;
        System.out.println(name + " took " + asString(time));
    }

    public static void reset() {
        reset(defaultIndex);
    }

    public static void reset(String name) {
        write(name);
        init(name);
    }

    public static long getNanos() {
        return getNanos(defaultIndex);
    }

    public static long getNanos(String name) {
        return System.nanoTime() - starts.getOrDefault(name, 0L);
    }

    private static final BigDecimal thousand = BigDecimal.valueOf(1000L), million = BigDecimal.valueOf(1000000L), billion = BigDecimal.valueOf(1000000000L);

    private static String asString(long time) {
        switch (using) {
            case SECONDS:
                return BigDecimal.valueOf(time).divide(billion, 3, RoundingMode.HALF_UP).toString() + " seconds";
            case MILLIS:
                return BigDecimal.valueOf(time).divide(million, 3, RoundingMode.HALF_UP).toString() + " milliseconds";
            case MICRO:
                return BigDecimal.valueOf(time).divide(thousand, 3, RoundingMode.HALF_UP).toString() + " microseconds";
            case NANO:
                return String.valueOf(time) + " nanoseconds";
            default:
                return "error";
        }
    }
}