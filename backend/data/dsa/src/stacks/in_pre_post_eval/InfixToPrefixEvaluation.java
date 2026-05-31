package stacks.in_pre_post_eval;

import java.util.Stack;

public class InfixToPrefixEvaluation {
    public String infixToPrefix(String s) {
        s = reverseAndSwapBrackets(s);
        Stack<String> st = new Stack<>();
        String ans = "";
        for(int i=0;i<s.length();i++){
            String str = "" + s.charAt(i);
            if (Character.isLetterOrDigit(s.charAt(i))) ans += str;
            else if(str.equals("(")) st.push(str);
            else if(str.equals(")")){
                while(st.size()>0 && !st.peek().equals("(")) ans += st.pop();
                st.pop(); //removing opening bracket
            } else if(isOperator(str)){
                while(st.size()>0 &&
                        isOperator(st.peek()) &&
                        ((!str.equals("^") && priority(str) <= priority(st.peek())) ||
                                (str.equals("^") && priority(str) <  priority(st.peek()))))
                        ans += st.pop();
                st.push(str);
            }
        }

        while(st.size()>0) ans += st.pop();

        return new StringBuilder(ans).reverse().toString();
    }

    private static int priority(String s) {
        return switch (s) {
            case "^" -> 3;
            case "*", "/" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    private static boolean isOperator(String s) {
        return s.equals("+") ||
                s.equals("-") ||
                s.equals("*") ||
                s.equals("/") ||
                s.equals("^");
    }

    private static String reverseAndSwapBrackets(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);

            if (ch == '(') sb.append(')');
            else if (ch == ')') sb.append('(');
            else sb.append(ch);
        }

        return sb.toString();
    }
}
