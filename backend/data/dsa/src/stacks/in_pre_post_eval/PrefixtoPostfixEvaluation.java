package stacks.in_pre_post_eval;

import java.util.Stack;

public class PrefixtoPostfixEvaluation {
    static String preToPost(String exp) {
        Stack<String> st = new Stack<>();
        for(int i=exp.length()-1;i>=0;i--){
            char ch = exp.charAt(i);

            if(Character.isLetterOrDigit(ch))
                st.push(String.valueOf(ch));
            else {
                String top = st.pop();
                String bottom = st.pop();
                st.push(top + bottom + ch);
            }
        }
        return st.peek();
    }
}
