package stacks.in_pre_post_eval;

import java.util.Stack;

public class PostfixEvaluation {
    public int evaluatePostfix(String[] arr) {
        Stack<Integer> st = new Stack<>();
        for(String s : arr){
            if(isOperator(s)){
                int top = st.pop();
                int bottom = st.pop();
                int val = evaluate(bottom, s, top);
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
