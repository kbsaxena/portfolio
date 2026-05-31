package stacks;

import java.util.Stack;

public class StackTraversal {
    public static void main(String[] args) {
        Stack<Integer> st = new Stack<>();
        st.push(20);st.push(10);st.push(30);

        Stack<Integer> st1 = new Stack<>();
        while(st.size()>0){
            st1.push(st.pop());
        }

        while(st1.size()>0){
            System.out.println(st1.peek());
            st.push(st1.pop());
        }
    }
}
