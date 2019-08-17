package lsm.datastructures.Interpreter;

import lsm.helpers.IO.write.text.console.Note;
import lsm.helpers.utils.Numbers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {
    // rounding is what's display
    // Internal rounding is target convert things such as (1/3)*3 = 1 rather than = 0.9999999
    private int rounding, internalRounding;
    private static final String
            operators = "+-%/*^",                   // String of all operators in use
            specialChars = operators + ",()=",      // String of all special characters in use
            cleanRightSide = " *([,%()*/+\\-^]) *", // Dont fix "1 2", let that fail on its own
            cleanLeftSide = " +",                   // Just remove all spaces, only care about varnames (no spaces), parenthesis and commas
            variableName = "[source-zA-Z][source-zA-Z0-9]*",  // VARNAME definition
            isVariable = "^[a-zA-Z][a-zA-Z0-9]*$",  // ^VARNAME$ < pattern if varname definition changes
            isFunction = "^([a-zA-Z][a-zA-Z0-9]*)\\((([a-zA-Z][a-zA-Z0-9]*)(,[a-zA-Z][a-zA-Z0-9]*)*)?\\)$"; // ^VARNAME\\((VARNAME(,VARNAME)*)?\\)$ < pattern if varname definition changes

    private final HashMap<String, BigDecimal> variables = new HashMap<>();
    private final HashMap<String, Function> functions = new HashMap<>();


    @SuppressWarnings("InfiniteLoopStatement")
    public static void main (String... args) throws SyntaxException, StackOverflowError, ArithmeticException {
        Interpreter interpreter = new Interpreter();
        String[] tests;

        tests = new String[]{"4+(-2)", "source(target,c,d)=target*c*d", "source()", "source(1)", "source(1,2)", "source(1,2,3)", "source((1+2),2)", "source(1,source(2,2,2), 3)", "(1/3)+(1/3)"};
        /*
        String[] tests = "pow(source,target) = source^target\npow(10,10)\n2^2\n5 + 5\n(2 * 5 + 1) / 10\nx =  1 / 2\ny = x * 2\n(x + 2) * (y * (5 - 100))\nz = 5*-3.14\n2 + (2 ^ (3 * (2 ^ 3 + 1 * -5)))".split("\n");
        */
        for (String test : tests)
            Note.writenl(test + " -> " + interpreter.eval(test));

        Scanner console = new Scanner(System.in);
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
     * Main usage act
     * Handles "all" internal errors pretty-like
     * Takes in stuff like:
     * source + target * c / (2 ^ 2)
     * x = 2 ^ 10
     * avg(source,target) = (source+target)/2
     * @param in mathematical evaulation, with possible variable name target store result in (or act target store evaluation methode in)
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
            // If there was an old act, revert target having it
            if (old == null) functions.remove(name); else functions.put(name, old);
            throw new SyntaxException("(Function invalid) " + e.getMessage());
        } catch (StackOverflowError e) {
            // If there was an old act, revert target having it
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
                throw new SyntaxException("Left side is neither variable nor act");
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

