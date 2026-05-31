package stacks;

import java.util.Stack;

public class PushPopPeekAtAnyPosition {
    public static void main(String[] args) {
        Stack<String> st = new Stack<>();
        st.push("A");st.push("B");
        st.push("C");st.push("D");

        print(st);
        System.out.println(peekAtIdx(1, st));
        pushAtIdx(2, "Z", st);
        print(st);
        System.out.println(popAtIdx(2, st));
        print(st);
    }


    private static String peekAtIdx(int idx, Stack<String> st) {
        int k = st.size() - idx - 1;
        Stack<String> st2 = new Stack<>();
        for(int i=1;i<=k;i++){
            st2.push(st.pop());
        }
        String val = st.peek();
        while(st2.size()>0){
            st.push(st2.pop());
        }

        return val;
    }

    private static String popAtIdx(int idx, Stack<String> st) {
        int k = st.size() - idx - 1;
        Stack<String> st2 = new Stack<>();
        for(int i=1;i<=k;i++){
            st2.push(st.pop());
        }
        String val = st.pop();
        while(st2.size()>0){
            st.push(st2.pop());
        }

        return val;
    }

    private static void pushAtIdx(int idx, String value, Stack<String> st) {
        int k = st.size() - idx;
        Stack<String> st2 = new Stack<>();
        for(int i=1;i<=k;i++){
            st2.push(st.pop());
        }
        st.push(value);
        while(st2.size()>0){
            st.push(st2.pop());
        }
    }

    static void print(Stack<String> st){
        Stack<String> st2 = new Stack<>();
        int idx = st.size()-1;
        while(st.size()>0){
            System.out.println(idx-- + " " + st.peek());
            st2.push(st.pop());
        }

        while(st2.size()>0){
            st.push(st2.pop());
        }
    }

}
