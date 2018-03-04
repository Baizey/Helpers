package lsm.helpers;

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
            SECONDS = 0,
            MILLIS = 1,
            MICRO = 2,
            NANO = 3;
    private static int using = SECONDS;
    private static final String defaultName = "Program";
    private static final HashMap<String, Long> starts = new HashMap<>();

    public static void takeTime(Action action) {
        takeTime(defaultName, action);
    }

    public static void takeTime(String name, Action action) {
        Time.init(name);
        action.function();
        Time.write(name);
    }

    public static void using(int unit) {
        if (Numbers.inRange(unit, 0, 3)) Time.using = unit;
    }

    public static void init() {
        init(defaultName);
    }

    public static void init(String name) {
        starts.put(name, System.nanoTime());
    }

    public static void write() {
        write(defaultName);
    }

    public static void write(String name) {
        long time = getNanos(name);
        if (starts.getOrDefault(name, 0L) == 0L) return;
        System.out.println(name + " took " + asString(time));
    }

    public static double get() {
        return get(defaultName);
    }

    public static double get(String name) {
        BigDecimal time = BigDecimal.valueOf(getNanos(name)), divisor = null;
        switch(using) {
            case SECONDS: divisor = BILLION; break;
            case MILLIS: divisor = MILLION; break;
            case MICRO: divisor = THOUSAND; break;
            case NANO: divisor = BigDecimal.ONE; break;
        }
        return time.divide(divisor, 3, RoundingMode.HALF_UP).doubleValue();
    }

    public static void reset() {
        reset(defaultName);
    }

    public static void reset(String name) {
        write(name);
        init(name);
    }

    public static long getNanos() {
        return getNanos(defaultName);
    }

    public static long getNanos(String name) {
        return System.nanoTime() - starts.get(name);
    }

    private static String asString(long time) {
        switch (using) {
            case SECONDS:
                return BigDecimal.valueOf(time).divide(BILLION, 3, RoundingMode.HALF_UP).toString() + " seconds";
            case MILLIS:
                return BigDecimal.valueOf(time).divide(MILLION, 3, RoundingMode.HALF_UP).toString() + " milliseconds";
            case MICRO:
                return BigDecimal.valueOf(time).divide(THOUSAND, 3, RoundingMode.HALF_UP).toString() + " microseconds";
            case NANO:
                return String.valueOf(time) + " nanoseconds";
            default:
                return "error";
        }
    }
}

