package stacks;

import java.util.Stack;

public class ParenthesesChecker {
    public boolean isBalanced(String s) {
        // code here
        Stack<Character> st = new Stack<>();
        for(int i = 0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c == '(' || c == '{' || c == '['){
                st.push(c);
            } else {
                //Checking size because if closing brackets comes first then stack cannot be empty
                if(st.size()>0 && isSameType(c, st.peek())) st.pop();
                else return false;
            }
        }

        return (st.size() == 0);
    }

    private boolean isSameType(char closing, Character opening) {
        if(opening == '(' && closing == ')') return true;
        if(opening == '{' && closing == '}') return true;
        if(opening == '[' && closing == ']') return true;
        return false;
    }
}
