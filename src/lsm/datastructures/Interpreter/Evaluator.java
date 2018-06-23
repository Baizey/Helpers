package lsm.datastructures.Interpreter;

import java.math.BigDecimal;
import java.util.HashMap;

public class Evaluator {
    private static final HashMap<String, BigDecimal> variables = new HashMap<>();
    private static final HashMap<Character, Integer> order = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
        put('^', 3);
    }};

    static BigDecimal eval(String input) {
        input = input.replaceAll(" +", "").toLowerCase();
        int i = input.indexOf('=');
        if (i == -1) return calculate(input);
        BigDecimal result = calculate(input.substring(i + 1, input.length()));
        variables.put(input.substring(0, i), result);
        return result;
    }

    private static BigDecimal parenthesisSplitter(String input) {
        int start = -1, end = 0, depth = 0, lastStart = -1, lastLetter = -2;
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                lastLetter = i;
            } else if (input.charAt(i) == '(') {
                depth++;
                if (depth == 1) lastStart = i;
                if (lastLetter == i - 1) continue;
                if (depth == 1) start = i;
            } else if (input.charAt(i) == ')') {
                depth--;
                if (lastStart == start) continue;
                if (depth == 0) {
                    sb.append(input.substring(end, start));
                    sb.append(parenthesisSplitter(input.substring(start + 1, i - 1)));
                    end = i;
                }
            }
        }
        if (end != input.length() - 1)
            sb.append(input.substring(end, input.length()));
        return calculate(sb.toString());
    }

    private static BigDecimal calculate(String input) {

        int i = 1;
        while (i < input.length() && !order.containsKey(input.charAt(i)))
            i++;

        String var = input.substring(0, i);
        BigDecimal value = variables.getOrDefault(var, new BigDecimal(var));

        char ope = i < input.length() ? input.charAt(0) : 0;
        if (ope == 0) return value;

        int currOrder = order.getOrDefault(ope, -1);

        BigDecimal result = null;
        if (currOrder > 0)
            result = calculate(input.substring(i + 1));

        switch (currOrder) {
            case '+':
                return value.add(result);
            case '-':
                return value.subtract(result);
            case '*':
                return value.multiply(result);
            case '/':
                return value.divide(result, BigDecimal.ROUND_HALF_UP);
            case '^':
                // If pow of int BigDecimal can convert it
                if (result.precision() == 0) return value.pow(result.intValue());
                // Otherwise we gotta revert target power of doubles
                return BigDecimal.valueOf(Math.pow(value.doubleValue(), result.doubleValue()));
        }

        return null;
    }

    private static void calculate(String input, int minOrder) {

    }

}