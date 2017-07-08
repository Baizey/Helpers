package lsm.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * Usage:
 * Time.init()  will take current time
 * Time.write() will write the difference since last initialize call
 * Time.reset() will attempt to write time since last init and then re-initialize
 * A name can be given to take time on multiple things at once
 * Program is default name if none is given
 */

public class Time {
    private static final String defaultIndex = "Program";
    private static final HashMap<String, Long> starts = new HashMap<>();

    public static void init () {
        init(Time.defaultIndex);
    }
    public static void init (String name) {
        Time.starts.put(name, System.currentTimeMillis());
    }

    public static void write () {
        write(Time.defaultIndex);
    }
    public static void write (String name) {
        long start = Time.starts.getOrDefault(name, 0L);
        if (start == 0L) return;
        long diff = System.currentTimeMillis() - start;
        Note.writenl(name + " took " + asSeconds(diff) + " seconds");
    }

    public static void reset () {
        reset(Time.defaultIndex);
    }
    public static void reset (String name) {
        write(name);
        init(name);
    }

    /**
     * @param time, time in milliseconds
     * @returns String representing time in seconds
     */
    private static String asSeconds (long time) {
        return BigDecimal.valueOf(time).divide(Time.thousand, 3, RoundingMode.HALF_UP).toString();
    }
    private static final BigDecimal thousand = BigDecimal.valueOf(1000);
}