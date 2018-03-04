package lsm.algorithms.Interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;

public class ParserNode {
    // Either value is initiated or operator and left/right is initiated
    private Character operator = null;
    private ParserNode left = null, right = null;
    private String value = null;

    /**
     * Figures out each char's depth in string (parenthesis-wise)
     * closing parenthesis will be 1 depth below their opening partner
     * @param input, string with parenthesis in it
     * @return int[] array of depth for each char
     * @throws SyntaxException, If parenthesis arent partnered up correctly
     */
    static int[] findParenthesis (String input) throws SyntaxException {
        if (input == null || input.length() == 0)
            return new int[0];

        int[] depth = new int[input.length()];
        int currDepth = 0;

        for (int i = 0 ; i < depth.length; i++) {
            switch (input.charAt(i)) {
                case '(':
                    depth[i] = ++currDepth;
                    // Adds function names to depth of parenthesis for function start
                    for (int j = i; j >= 0 && (Interpreter.isNumOrChar(input.charAt(j))); j--)
                        depth[j] = currDepth;
                    break;
                case ')':
                    if (currDepth == 0)
                        throw new SyntaxException("Lacking open parenthesis for closing parenthesis");
                    depth[i] = --currDepth;
                    break;
                default:
                    depth[i] = currDepth;
            }
        }
        if(currDepth > 0)
            throw new SyntaxException("Too many opening parenthesis");
        return depth;
    }

    private boolean surrounded (int[] depth) {
        for (int i = 0; i < depth.length - 1; i++)
            if (depth[i] == 0) return false;
        return depth.length > 1;
    }

    ParserNode(String input) throws SyntaxException, StackOverflowError, ArithmeticException {
        if (input == null || input.length() == 0) throw new SyntaxException("Somethings fucky... I cant quite see what thou");
        // Handles extra +/- in stuff like 5+-2 or -+-+-+5-++--+++-+-+4
        if (input.charAt(0) == '-' || input.charAt(0) == '+') input = "0" + input;
        int[] depth = findParenthesis(input);

        // Trim surrounding parenthesis
        //noinspection SuspiciousMethodCalls
        while (surrounded(depth)) {
            input = input.substring(1, depth.length - 1);
            if (input.charAt(0) == '-' || input.charAt(0) == '+') input = "0" + input;
            depth = findParenthesis(input);
        }

        int addSub = -1, mulDivMod = -1, pow = -1;
        out: for (int i = depth.length - 2; i > 0; i--) {
            if (depth[i] > 0) continue; // dont meddle inside parenthesis
            switch (input.charAt(i)) {
                case '-': case '+':
                    // Checks for cases such as from*-to where * is dominant
                    if (!Interpreter.addSubBefore(input.charAt(i - 1))) break;
                    addSub = i;
                    break out;
                case '*': case '/': case '%': mulDivMod = Math.max(mulDivMod, i); break;
                case '^': pow = Math.max(pow, i); break;
            }
        }
        // Figure out which operator to use
        int usedOperator = addSub > 0 ? addSub
                    : (mulDivMod > 0 ? mulDivMod
                        : (pow > 0 ? pow : -1));

        if (usedOperator > 0) {
            left = new ParserNode(input.substring(0, usedOperator));
            right = new ParserNode(input.substring(usedOperator + 1, input.length()));
            operator = input.charAt(usedOperator);
        } else
            value = input;
    }
    private BigDecimal eval (Interpreter interpreter) throws SyntaxException, ArithmeticException, StackOverflowError {
        return eval(interpreter, new HashMap<>(), new HashSet<>());
    }
    private BigDecimal eval(Interpreter interpreter, HashSet<String> reserved) throws SyntaxException, ArithmeticException, StackOverflowError {
        return eval(interpreter, new HashMap<>(), reserved);
    }
    BigDecimal eval (Interpreter interpreter, HashMap<String, BigDecimal> arguments) throws SyntaxException, ArithmeticException, StackOverflowError {
        return eval(interpreter, arguments, new HashSet<>());
    }
    private BigDecimal eval(Interpreter interpreter, HashMap<String, BigDecimal> arguments, HashSet<String> reserved) throws SyntaxException, ArithmeticException, StackOverflowError {
        if (value == null) {
            BigDecimal l = left.eval(interpreter, arguments);
            BigDecimal r = right.eval(interpreter, arguments);
            switch (operator) {
                case '-': return l.subtract(r);
                case '+': return l.add(r);
                case '*': return l.multiply(r);
                case '%': return l.remainder(r);
                case '/': return l.divide(r, interpreter.getInternalRounding(), RoundingMode.HALF_UP);
                case '^': return l.pow(r.intValue());
            }
        }
        if (reserved.contains(value))
            throw new SyntaxException("Variable '" + value + "' is reserved");
        if(arguments.containsKey(value))
            return arguments.get(value);

        HashMap<String, BigDecimal> variables = interpreter.getVariables();
        if(variables.containsKey(value))
            return variables.get(value);
        if (value.contains("(")) {
            HashMap<String, Function> functions = interpreter.getFunctions();
            String name = value.split("\\(")[0];
            if(!functions.containsKey(name)) throw new SyntaxException("Function '" + name + "' not defined");
            String args = value.substring(name.length() + 1, value.length() - 1);
            return functions.get(name).eval(interpreter, arguments, args);
        }
        if(value.matches("[0-9]+(\\.[0-9]*)?"))
            return new BigDecimal(value);
        throw new SyntaxException("Variable '" + value + "' not defined");
    }

    void simplify (Interpreter interpreter, HashSet<String> reservered) {
        if (value != null) {
            if (reservered.contains(value))
                return;
            HashMap<String, BigDecimal> variables = interpreter.getVariables();
            if(variables.containsKey(value))
                value = variables.get(value).toPlainString();
        } else {
            try {
                value = eval(interpreter, reservered).toPlainString();
                left = null;
                right = null;
            } catch (Exception e) {
                left.simplify(interpreter, reservered);
                right.simplify(interpreter, reservered);
            }
        }
    }

    public String toString () {
        if(value == null)
            return left.toString() + operator + right.toString();
        return value;
    }
}
