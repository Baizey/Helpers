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
 * 'Program' is default name if none is given
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class Time {
    private static final String defaultIndex = "Program";
    private static final HashMap<String, Long> starts = new HashMap<String, Long>() {{
        put(defaultIndex, System.currentTimeMillis());
    }};

    public static void init() {
        init(defaultIndex);
    }

    public static void init(String name) {
        starts.put(name, System.currentTimeMillis());
    }

    public static void write() {
        write(defaultIndex);
    }

    public static void write(String name) {
        long start = starts.getOrDefault(name, 0L);

        // Should probably throw an error message?
        if (start == 0L)
            return;

        long diff = System.currentTimeMillis() - start;
        Note.writenl(name + " took " + asSeconds(diff) + " seconds");
    }

    public static void reset() {
        reset(defaultIndex);
    }

    public static void reset(String name) {
        write(name);
        init(name);
    }

    /**
     * @param time, time in milliseconds
     * @returns String representing time in seconds
     */
    private static String asSeconds(long time) {
        return BigDecimal.valueOf(time).divide(BigDecimal.valueOf(1000L), 3, RoundingMode.HALF_UP).toString();
    }
}