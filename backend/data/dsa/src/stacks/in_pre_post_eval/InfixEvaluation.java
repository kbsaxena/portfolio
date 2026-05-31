package stacks.in_pre_post_eval;

import java.util.Stack;

public class InfixEvaluation {
    public int evaluateInfix(String[] arr) {
        Stack<Integer> values = new Stack<>();
        Stack<String> ops = new Stack<>();
        for (String s : arr) {
            if (isNumber(s)) values.push(Integer.parseInt(s));// 1. Number → push to values
            else if (s.equals("(")) ops.push(s); // 2. Opening bracket
            else if (s.equals(")")) { // 3. Closing bracket
                while (!ops.peek().equals("(")) solve(values, ops);
                ops.pop(); // remove '('
            } else if (isOperator(s)) { // 4. Operator
                while (!ops.isEmpty() &&
                        isOperator(ops.peek()) &&
                        ((!s.equals("^") && priority(s) <= priority(ops.peek()) ) ||
                                (s.equals("^") && priority(s) <  priority(ops.peek()) )))
                    solve(values, ops);

                ops.push(s);
            }
        }

        while (!ops.isEmpty()) {
            solve(values, ops);
        }

        return values.peek();
    }

    private void solve(Stack<Integer> values, Stack<String> ops) {
        int top = values.pop();
        int bottom = values.pop();
        String op = ops.pop();

        int res = evaluate(bottom, op, top);
        values.push(res);
    }

    private int evaluate(int a, String op, int b) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> Math.floorDiv(a, b);
            default -> (int) Math.pow(a, b);
        };
    }

    private int priority(String s) {
        return switch (s) {
            case "^" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    private boolean isOperator(String s) {
        return s.equals("+") ||
                s.equals("-") ||
                s.equals("*") ||
                s.equals("/") ||
                s.equals("^");
    }

    private boolean isNumber(String s) {
        return Character.isDigit(s.charAt(0));
    }
}
