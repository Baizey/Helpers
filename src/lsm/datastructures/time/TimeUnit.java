package lsm.datastructures.time;

import java.math.BigDecimal;

public enum TimeUnit {
    AUTO(null, null),
    MINUTES("minutes", BigDecimal.valueOf(60000000000L)),
    SECONDS("seconds", BigDecimal.valueOf(1000000000L)),
    MILLIS("milliseconds", BigDecimal.valueOf(1000000L)),
    MICRO("microseconds", BigDecimal.valueOf(1000L)),
    NANO("nanoseconds", BigDecimal.ONE);

    private final BigDecimal divisor;
    private String name;

    TimeUnit(String name, BigDecimal unit) {
        this.name = name;
        this.divisor = unit;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getDivisor() {
        return divisor;
    }

    public BigDecimal getDivisor(BigDecimal timeInNano) {
        return resolve(timeInNano).divisor;
    }

    public TimeUnit resolve(BigDecimal timeInNano) {
        if (this != AUTO) return this;
        if (timeInNano.compareTo(MICRO.divisor) < 0)
            return NANO;
        if (timeInNano.compareTo(MILLIS.divisor) < 0)
            return MICRO;
        if (timeInNano.compareTo(SECONDS.divisor) < 0)
            return MILLIS;
        if (timeInNano.compareTo(MINUTES.divisor) < 0)
            return SECONDS;
        return MINUTES;
    }
}
