package stacks;

import java.util.Stack;

public class BaseBallGame {
    public int calPoints(String[] operations) {
        Stack<Integer> st = new Stack<>();
        for(int i=0; i<operations.length;i++){
            String s = operations[i];
            if(s.equals("C")) st.pop();
            else if(s.equals("D")) st.push(st.peek() * 2);
            else if(s.equals("+")){
                //pop first then push it again
                int top = st.pop();
                int sum = st.peek() + top;
                st.push(top);
                st.push(sum);
            } else st.push(Integer.parseInt(s));
        }
        int score = 0;
        while(st.size()>0){
            score += st.pop();
        }
        return score;
    }
}
