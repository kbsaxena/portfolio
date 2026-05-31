package stacks.in_pre_post_eval;

import java.util.Stack;

public class PrefixEvaluation {
    public int evaluatePrefix(String[] arr) {
        Stack<Integer> st = new Stack<>();
        for(int i=arr.length-1;i>=0;i--){
            String s = arr[i];
            if (isOperator(s)) {
                int top = st.pop();
                int bottom = st.pop();
                int val = evaluate(top, s, bottom);
                st.push(val);
            } else st.push(Integer.parseInt(s));
        }

        return st.peek();
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

    private boolean isOperator(String s) {
        return s.equals("+") ||
                s.equals("-") ||
                s.equals("*") ||
                s.equals("/") ||
                s.equals("^");
    }
}
