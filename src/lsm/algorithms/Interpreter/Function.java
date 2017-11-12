package lsm.algorithms.Interpreter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Function {
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
