package stacks.in_pre_post_eval;

import java.util.Stack;

public class PostfixtoPrefixEvaluation {
    static String postToPre(String exp) {
        Stack<String> st = new Stack<>();
        for(int i=0;i<exp.length();i++){
            char ch = exp.charAt(i);

            if(Character.isLetterOrDigit(ch))
                st.push(String.valueOf(ch));
            else {
                String top = st.pop();
                String bottom = st.pop();
                st.push(ch + bottom + top);
            }
        }
        return st.peek();
    }
}
