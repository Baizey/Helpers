package lsm.algorithms.Interpreter;

import lsm.helpers.IO.read.text.TextReader;
import lsm.helpers.Note;
import lsm.helpers.Numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Interpreter {
    // rounding is what's display
    // Internal rounding is to handle things such as (1/3)*3 = 1 rather than = 0.9999999
    private int rounding, internalRounding;
    private static final String
            operators = "+-%/*^",                   // String of all operators in use
            specialChars = operators + ",()=",      // String of all special characters in use
            cleanRightSide = " *([,%()*/+\\-^]) *", // Dont fix "1 2", let that fail on its own
            cleanLeftSide = " +",                   // Just remove all spaces, only care about varnames (no spaces), parenthesis and commas
            variableName = "[a-zA-Z][a-zA-Z0-9]*",  // VARNAME definition
            isVariable = "^[a-zA-Z][a-zA-Z0-9]*$",  // ^VARNAME$ < pattern if varname definition changes
            isFunction = "^([a-zA-Z][a-zA-Z0-9]*)\\((([a-zA-Z][a-zA-Z0-9]*)(,[a-zA-Z][a-zA-Z0-9]*)*)?\\)$"; // ^VARNAME\\((VARNAME(,VARNAME)*)?\\)$ < pattern if varname definition changes

    private final HashMap<String, BigDecimal> variables = new HashMap<>();
    private final HashMap<String, Function> functions = new HashMap<>();


    @SuppressWarnings("InfiniteLoopStatement")
    public static void main (String... args) throws SyntaxException, StackOverflowError, ArithmeticException {
        Interpreter interpreter = new Interpreter();
        String[] tests;

        tests = new String[]{"4+(-2)", "a(b,c,d)=b*c*d", "a()", "a(1)", "a(1,2)", "a(1,2,3)", "a((1+2),2)", "a(1,a(2,2,2), 3)", "(1/3)+(1/3)"};
        /*
        String[] tests = "pow(a,b) = a^b\npow(10,10)\n2^2\n5 + 5\n(2 * 5 + 1) / 10\nx =  1 / 2\ny = x * 2\n(x + 2) * (y * (5 - 100))\nz = 5*-3.14\n2 + (2 ^ (3 * (2 ^ 3 + 1 * -5)))".split("\n");
        */
        for (String test : tests)
            Note.writenl(test + " -> " + interpreter.eval(test));

        Scanner console = TextReader.getConsoleReader();
        while (true) {
            Note.write("Input: ");
            Note.writenl(interpreter.eval(console.nextLine()));
        }
    }
    public Interpreter () { setRounding(20); }
    public Interpreter (int rounding) {
        setRounding(rounding);
    }

    /**
     * Main usage function
     * Handles "all" internal errors pretty-like
     * Takes in stuff like:
     * a + b * c / (2 ^ 2)
     * x = 2 ^ 10
     * avg(a,b) = (a+b)/2
     * @param in mathematical evaulation, with possible variable name to store result in (or function to store evaluation methode in)
     * @return result as string, and if something went wrong what went wrong
     */
    public String eval (String in) {
        try {
            return unsafeEval(in);
        } catch (SyntaxException e) {
            return e.getMessage();
        } catch (ArithmeticException e) {
            return "Arithmetic error: " + e.getMessage();
        } catch (StackOverflowError e) {
            return "Too many operators OR recursive";
        }
    }

    private String unsafeEval (String in) throws SyntaxException, StackOverflowError, ArithmeticException {
        if(in == null || in.length() == 0) throw new SyntaxException("Nothing given");

        Function func = figureInput(in);

        switch (func.getType()) {
            case Function.FUNCTION:
                addFunction(func);
                return "Function added";
            case Function.VARIABLE:
                addVariable(func.getName(), func.eval(this, new String[0]).toString());
                return Numbers.round(variables.get(func.getName()), rounding).stripTrailingZeros().toString();
            default:
                return Numbers.round(func.eval(this, new String[0]), rounding).stripTrailingZeros().toString();
        }
    }

    private void addVariable (String name, String value) throws SyntaxException, StackOverflowError, ArithmeticException {
        variables.put(name, new BigDecimal(value));
    }

    private void addFunction(Function func) throws SyntaxException, StackOverflowError, ArithmeticException {
        String name = func.getName();
        Function old = functions.getOrDefault(name, null);
        functions.put(name, func);
        try {
            String[] tempArgs = new String[func.getArgNames().length];
            Arrays.fill(tempArgs, "1");
            func.eval(this, tempArgs);
            func.simplify(this);
        } catch (SyntaxException e) {
            // If there was an old function, revert to having it
            if (old == null) functions.remove(name); else functions.put(name, old);
            throw new SyntaxException("(Function invalid) " + e.getMessage());
        } catch (StackOverflowError e) {
            // If there was an old function, revert to having it
            if (old == null) functions.remove(name); else functions.put(name, old);
            throw new StackOverflowError("(Function invalid) " + e.getMessage());
        }
        // Blame the test arguments for this error and pray this doesnt trigger before the others
        catch (ArithmeticException ignored) {}
    }

    /*
     * UTILITY FUNCTIONS
     */
    private Function figureInput (String in) throws SyntaxException, StackOverflowError {
        int type = Function.EVALUATION;
        String name = "!testname!", left, right;
        String[] args = new String[0];
        if (hasLeft(in)) {
            String[] temp = in.split("=");
            if (temp.length != 2)
                throw new SyntaxException("Only one equal sign allowed and must have stuff on both sides");
            left = cleanLeftSide(temp[0]);
            right = cleanRightSide(temp[1]);
            if (isFunction(left)) {
                type = Function.FUNCTION;
                name = getFuncName(left);
                args = getFuncArgs(left);
            } else if (isVariable(left)) {
                type = Function.VARIABLE;
                name = left;
            } else
                throw new SyntaxException("Left side is neither variable nor function");
        } else
            right = cleanRightSide(in);
        return new Function(type, name, right, args);
    }
    private static String cleanRightSide (String right) {
        return right.replaceAll(cleanRightSide, "$1").trim();
    }
    private static String cleanLeftSide (String left) {
        return left.replaceAll(cleanLeftSide, "");
    }
    private static String[] getFuncArgs (String in) throws SyntaxException {
        int index = in.indexOf("(");
        String[] arr = in.substring(index + 1, in.length() - 1).split(",");
        if(arr.length == 1 && arr[0].length() == 0)
            return new String[0];
        return arr;
    }
    private static String getFuncName (String in) {
        return in.split("\\(")[0];
    }
    private static boolean hasLeft (String in) { return in.contains("="); }
    private static boolean isFunction (String in) {
        return in != null && in.matches(isFunction);
    }
    private static boolean isVariable (String in) {
        return in != null && in.matches(isVariable);
    }
    private static boolean isCharacter (char c) {
        c = Character.toLowerCase(c);
        return c >= 'a' && c <= 'z';
    }
    static boolean isNumOrChar (char c) {
        return isCharacter(c) || Character.isDigit(c);
    }
    static boolean addSubBefore(char c) {
        return c == ')' || isNumOrChar(c);
    }

    HashMap<String,Function> getFunctions() {
        return functions;
    }
    HashMap<String,BigDecimal> getVariables() {
        return variables;
    }
    public int getRounding () {
        return rounding;
    }
    public int getInternalRounding () {
        return internalRounding;
    }
    public void setRounding(int rounding) {
        if (rounding < 0 || rounding > 100) return;
        this.rounding = rounding;
        this.internalRounding = rounding + 1;
    }
}

class Function {
    static final int EVALUATION = 0, VARIABLE = 1, FUNCTION = 2;
    private final int type;
    private final String name;
    private final String[] argNames;
    private final ParserNode parser;

    Function (int type, String name, String parser, String... arguments) throws SyntaxException, StackOverflowError, ArithmeticException {
        this.name = name;
        this.parser = new ParserNode(parser);
        this.argNames = arguments;
        this.type = type;
    }

    BigDecimal eval(Interpreter interpreter, HashMap<String, BigDecimal> oldArgs, String rawArgs) throws SyntaxException, StackOverflowError, ArithmeticException {
        ArrayList<String> stringArgs = new ArrayList<>(argNames.length);
        int[] depth = ParserNode.findParenthesis(rawArgs);
        int last = 0;
        for (int i = 1; i < rawArgs.length(); i++) {
            if(depth[i] > 0) continue;
            if(rawArgs.charAt(i) == ',') {
                stringArgs.add(new ParserNode(rawArgs.substring(last, i)).eval(interpreter, oldArgs).toString());
                last = i + 1;
            }
        }
        if(last > 0 || rawArgs.length() > 0)
            stringArgs.add(new ParserNode(rawArgs.substring(last)).eval(interpreter, oldArgs).toString());

        return eval(interpreter, oldArgs, stringArgs);
    }

    BigDecimal eval(Interpreter interpreter, String[] stringArgs) throws SyntaxException, StackOverflowError, ArithmeticException {
        return eval(interpreter, new HashMap<>(), new ArrayList<>(Arrays.asList(stringArgs)));
    }

    private BigDecimal eval(Interpreter interpreter, HashMap<String, BigDecimal> oldArgs, ArrayList<String> stringArgs) throws SyntaxException, StackOverflowError, ArithmeticException {
        if (stringArgs.size() < argNames.length) {
            StringBuilder sb = new StringBuilder("Function '" + name + "' lacks arguments");
            for (int i = stringArgs.size(); i < argNames.length; i++) {
                if (i > stringArgs.size())
                    sb.append(i == argNames.length - 1 ? " and" : ",");
                sb.append(" '").append(argNames[i]).append("'");
            }
            throw new SyntaxException(sb.toString());
        }
        if (stringArgs.size() > argNames.length)
            throw new SyntaxException("Function '" + name + "' has too many arguments");

        HashMap<String, BigDecimal> args = new HashMap<>(oldArgs);
        for(int i = 0; i < argNames.length; i++)
            args.put(argNames[i], new BigDecimal(stringArgs.get(i)));
        return parser.eval(interpreter, args);
    }

    String getName() {
        return name;
    }
    String[] getArgNames() {
        return argNames;
    }

    int getType() {
        return type;
    }

    void simplify(Interpreter interpreter) {
        parser.simplify(interpreter, new HashSet<>(Arrays.asList(argNames)));
    }
}

class ParserNode {
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
                    // Checks for cases such as a*-b where * is dominant
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

class SyntaxException extends Exception {
    SyntaxException(String msg) {
        super("Syntax error: " + msg);
    }
}