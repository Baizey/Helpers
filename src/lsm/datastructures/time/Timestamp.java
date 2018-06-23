package lsm.datastructures.time;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Timestamp {
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

    public double asValue(long end, int displayMode) {
        BigDecimal time = BigDecimal.valueOf(end - start);
        BigDecimal divisor = getDivisor(time, displayMode);
        return time.divide(divisor, 3, RoundingMode.HALF_UP).doubleValue();
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
