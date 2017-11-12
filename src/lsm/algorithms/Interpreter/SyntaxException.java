package lsm.algorithms.Interpreter;

class SyntaxException extends Exception {
    SyntaxException(String msg) {
        super("Syntax error: " + msg);
    }
}
