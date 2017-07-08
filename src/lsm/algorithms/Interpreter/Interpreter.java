package lsm.algorithms.Interpreter;

import lsm.helpers.Note;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Interpreter {
    private final HashMap<String, BigDecimal> variables = new HashMap<>();
    private final HashMap<String, Function> functions = new HashMap<>();

    public static void main (String... args){
        Interpreter interpreter = new Interpreter();
        Note.writenl(interpreter.eval("pi = " + Math.PI));
        Note.writenl(interpreter.eval("avg(a, b) = (a + b) / 2"));
        Note.writenl(interpreter.eval("avg(0, 1)"));
        Note.writenl(interpreter.eval("tripleavg(a, b, c) = avg(avg(1, 2), c)"));
        Note.writenl(interpreter.eval("five = pi * 5"));
        Note.writenl(interpreter.eval("five * avg(10, 20)"));
        Note.writenl(interpreter.eval("tripleavg(1, 2, 3)"));
    }

    public String eval (String in) {
        in = in.replaceAll(" +", "");
        String result;

        if(!in.contains("=")) {
            Note.write(in + " -> ");
            result = new ParserNode(in).eval(variables, functions).toString();
        } else {
            String[] parts = in.split("=");
            Note.write(parts[0] + " := ");
            if (parts[0].contains("(")) {
                String[] left = parts[0].split("\\(");
                addFunction(left[0], parts[1], left[1].split("\\)")[0].split(","));
                result = parts[1];
            } else {
                Note.write(parts[1] + " -> ");
                result = addVariable(parts[0], parts[1]).toString();
            }
        }
        return result;
    }

    private BigDecimal addVariable (String name, String value) {
        variables.put(name, new ParserNode(value).eval(variables, functions));
        return variables.get(name);
    }

    private void addFunction(String name, String body, String... args) {
        functions.put(name, new Function(name, body, args));
    }
}

class Function {
    private final String name;
    private final String[] argNames;
    private final ParserNode body;

    Function (String name, String body, String... arguments){
        this.name = name;
        this.body = new ParserNode(body);
        this.argNames = arguments;
    }

    public String get(int i) {
        return argNames[i];
    }

    public BigDecimal eval(HashMap<String, BigDecimal> variables, HashMap<String, Function> functions, HashMap<String, BigDecimal> oldArgs, String rawArgs) {
        String[] stringArgs = new String[argNames.length];
        rawArgs = rawArgs.substring(0, rawArgs.length() - 1);
        int[] depth = ParserNode.findParenthesis(rawArgs)[1];
        int last = 0, at = 0;
        for (int i = 1; i < rawArgs.length(); i++) {
            if(depth[i] > 0) continue;
            if(rawArgs.charAt(i) == ',') {
                stringArgs[at++] = new ParserNode(rawArgs.substring(last, i)).eval(variables, functions).toString();
                last = i + 1;
            }
        }
        stringArgs[at] = new ParserNode(rawArgs.substring(last)).eval(variables, functions, oldArgs).toString();

        HashMap<String, BigDecimal> args = new HashMap<>(oldArgs);
        for(int i = 0; i < stringArgs.length; i++)
            args.put(argNames[i], new BigDecimal(stringArgs[i]));
        return body.eval(variables, functions, args);
    }
}

class ParserNode {

    // Either value is initiated or operator and left/right is initiated
    private char operator;
    private ParserNode left, right;
    private String value;

    ParserNode(String input) {
        parse(input);
    }

    private static boolean isOperator (char c) {
        switch (c) {
            case '-': case '+': case '/': case '*':
                return true;
            default: return false;
        }
    }

    static int[][] findParenthesis (String input) {
        int[] jump = new int[input.length()];
        int[] depth = new int[jump.length];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0 ; i < jump.length; i++) {
            if (input.charAt(i) == '('){
                queue.addLast(i);
                depth[i] = (i > 0 ? depth[i - 1] : 0) + 1;
                int j = i;
                while(--j >= 0 && !isOperator(input.charAt(j))){
                    depth[j] = depth[i];
                }
            } else if (input.charAt(i) == ')') {
                jump[i] = queue.pollLast();
                jump[jump[i]] = i;
                depth[i] = depth[i - 1] - 1;
            } else {
                depth[i] = (i > 0 ? depth[i - 1] : 0);
            }
        }
        return new int[][]{jump, depth};
    }

    private boolean parse (String input) {
        // Handles extra +/-
        if(input.charAt(0) == '-' || input.charAt(0) == '+') input = "0" + input;
        int[][] t = findParenthesis(input);
        int[] jump = t[0], depth = t[1];

        // If parenthesis are around entire equation, trim them off
        if(jump.length > 1 && jump[0] == jump.length - 1)
            return parse(input.substring(1, jump.length - 1));

        // Finds next cutting place
        // Prioritizes + and -
        // Will take first * and / if no + or - was found
        // If none were found, assume numerical value
        int mulDiv = -1;
        for (int i = depth.length - 1; i > 0; i--) {
            if(depth[i] > 0) continue; // dont meddle inside parenthesis
            switch (input.charAt(i)) {
                case '-': case '+':
                    char l = input.charAt(i - 1), r = input.charAt(i + 1);
                    // Checks for cases such as a*-b where * is dominant
                    if (
                        (l == ')' || Character.isAlphabetic(l) || Character.isDigit(l)) &&
                        (r == '(' || Character.isAlphabetic(r) || Character.isDigit(r))
                    ) {
                        left = new ParserNode(input.substring(0, i));
                        right = new ParserNode(input.substring(i + 1, input.length()));
                        operator = input.charAt(i);
                        return true;
                    }
                    break;
                case '*': case '/':
                    if(mulDiv == -1) mulDiv = i;
            }
        }
        if (mulDiv > -1) {
            left = new ParserNode(input.substring(0, mulDiv));
            right = new ParserNode(input.substring(mulDiv + 1, input.length()));
            operator = input.charAt(mulDiv);
        } else {
            value = input;
        }
        return true;
    }

    BigDecimal eval (HashMap<String, BigDecimal> variables, HashMap<String, Function> functions) {
        return eval(variables, functions, new HashMap<>());
    }
    BigDecimal eval (HashMap<String, BigDecimal> variables, HashMap<String, Function> functions, HashMap<String, BigDecimal> arguments) {
        if(value == null) {
            BigDecimal l = left.eval(variables, functions, arguments);
            BigDecimal r = right.eval(variables, functions, arguments);
            switch (operator) {
                case '-': return l.subtract(r);
                case '+': return l.add(r);
                case '*': return l.multiply(r);
                case '/': return l.divide(r, 5, RoundingMode.HALF_UP);
            }
        }
        if(arguments.containsKey(value))
            return arguments.get(value);
        if(variables.containsKey(value))
            return variables.get(value);
        // Note.writenl("value: " + value);
        if (value.contains("(")) {
            String name = value.split("\\(")[0];
            String args = value.substring(name.length() + 1);
            return functions.get(name).eval(variables, functions, arguments, args);
        } else
            return new BigDecimal(value);
    }

    public String toString () {
        if(value == null)
            return left.toString() + operator + right.toString();
        return value;
    }

}