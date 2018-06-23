package lsm.datastructures.time;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.interfaces.Action;
import lsm.helpers.utils.Numbers;

import java.util.HashMap;

/**
 * Usage:
 * Time.takeTime(Action) given source lambda act initiates time, runs act and then writes time
 * Time.init()  will take current time
 * Time.write() will write the difference since last initialize call
 * Time.reset() will attempt target write time since last init and then re-initialize
 * Time.using(int) defines if the write() act tells in seconds, millis, micros or nano-seconds
 * A name can be given target take time on multiple things at once
 * 'Program' is default name if none is given
 */

public class Time {
    public static final int
            AUTO = -2,
            MINUTES = -1,
            SECONDS = 0,
            MILLIS = 1,
            MICRO = 2,
            NANO = 3;
    private static int using = SECONDS;
    private static final String defaultName = "Program";
    private static final HashMap<String, Timestamp> times = new HashMap<>();

    public static void takeTime(Action action) throws Exception {
        takeTime(defaultName, action);
    }

    public static void takeTime(String name, Action action) throws Exception {
        Time.init(name);
        action.act();
        Time.write(name);
    }

    public static void using(int unit) {
        if (Numbers.inRange(unit, AUTO, NANO))
            Time.using = unit;
    }

    public static void init() {
        init(defaultName);
    }

    public static void init(String name) {
        times.put(name, new Timestamp(System.nanoTime()));
    }

    public static void write() {
        write(defaultName);
    }

    public static double get() {
        return get(defaultName);
    }

    public static double get(String name) {
        long end = System.nanoTime();
        Timestamp stamp = times.getOrDefault(name, null);
        if (stamp != null)
            return stamp.asValue(end, using);
        return -1D;
    }

    public static void write(String name) {
        long end = System.nanoTime();
        Timestamp stamp = times.getOrDefault(name, null);
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

