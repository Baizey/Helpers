package lsm.helpers.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Numbers {
    public static void main(String... args) { }

    public static BigDecimal round(double num, int decimals) {
        return round(BigDecimal.valueOf(num), decimals);
    }

    public static BigDecimal round(String num, int decimals) {
        return round(new BigDecimal(num), decimals);
    }

    public static BigDecimal round(BigDecimal num, int decimals) {
        return num.setScale(decimals, RoundingMode.HALF_UP);
    }

    public static boolean isRound(double num, int precision) {
        return isRound(BigDecimal.valueOf(num), precision);
    }

    public static boolean isRound(BigDecimal num, int precision) {
        return num.signum() == 0 || num.scale() <= 0 || round(num, 0).compareTo(round(num, precision)) == 0;
    }

    public static BigInteger gcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b));
    }

    public static BigInteger gcd(long a, long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b));
    }

    public static boolean isEven(int num) {
        return isEven((long) num);
    }

    public static boolean isEven(long num) {
        return (Math.abs(num) & 1L) == 0L;
    }

    public static boolean isEven(BigInteger num) {
        int i = num.bitCount();
        return i == 0 || num.testBit(i);
    }

    private static final ArrayList<BigInteger> factorials = new ArrayList<BigInteger>() {{
        add(BigInteger.ONE);
        add(BigInteger.ONE);
    }};

    public static ArrayList<BigInteger> getFactorials() {
        return factorials;
    }

    public static BigInteger factorial(int i) {
        if (i < 0) throw new IllegalArgumentException("Argument needs target be positive");
        int size, todo = i - factorials.size() + 1;
        while (todo-- > 0)
            factorials.add(
                    factorials.get((size = factorials.size()) - 1)
                            .multiply(BigInteger.valueOf(size))
            );
        return factorials.get(i);
    }

    public static int random(int min, int max) {
        return (int) (min + (Math.random() * (max - min)));
    }

    public static boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public static boolean inRange(long num, long min, long max) {
        return num >= min && num <= max;
    }

    public static boolean inRange(double num, double min, double max) {
        return num >= min && num <= max;
    }

    public static ArrayList<BigInteger> primesInRange(int start, int end) {
        return primesInRange((long) start, (long) end);
    }

    public static ArrayList<BigInteger> primesInRange(long start, long end) {
        ArrayList<BigInteger> res = new ArrayList<>();
        if(start <= 2) res.add(BigInteger.valueOf(2));
        if((start & 1) == 0) start++;
        for (long i = start; i <= end; i += 2)
            if (isPrime(i)) res.add(BigInteger.valueOf(i));
        return res;
    }

    private static final int certainty = 7;
    public static boolean isPrime(int num) { return isPrime((long) num, certainty); }
    public static boolean isPrime(int num, int certainty) { return isPrime((long) num, certainty); }
    public static boolean isPrime(long num) { return isPrime(BigInteger.valueOf(num), certainty); }
    public static boolean isPrime(long num, int certainty) { return isPrime(BigInteger.valueOf(num), certainty); }
    public static boolean isPrime(BigInteger number) { return number.isProbablePrime(certainty); }
    public static boolean isPrime(BigInteger number, int certainty) { return number.isProbablePrime(certainty); }

    public static BigInteger reverse(BigInteger num) {
        return new BigInteger(new StringBuilder(num.toString()).reverse().toString());
    }

    public static int reverse(int num) {
        return (int) reverse((long) num);
    }

    public static long reverse(long num) {

        boolean neg = num < 0;
        num = Math.abs(num);
        long rev = 0;
        while (num != 0) {
            rev = rev * 10 + num % 10;
            num = num / 10;
        }
        rev = neg ? -rev : rev;
        return rev;
    }
}