package stacks;

import java.util.Stack;

class SpecialStack {
    Stack<Integer> st = new Stack<>();
    int min = Integer.MAX_VALUE;

    public SpecialStack() {
        // Define Stack
    }

    public void push(int ele) {
        // Add an element to the top of Stack
        if(st.size() == 0){
            st.push(ele);
            min = ele;
        } else if(ele > min) st.push(ele);
        else{ //Push a Fake value
            st.push(2 * ele - min);
            min = ele;
        }
    }

    public void pop() {
        // Remove the top element from the Stack
        if(st.peek() < min) //Something is fishy (min revert karo)
            min = 2 * min - st.peek();
        st.pop();
    }

    public int peek() {
        // Returns top element of the Stack
        if(st.size() == 0) return -1;
        if(st.peek()<=min) return min;
        else return st.peek();
    }

    boolean isEmpty() {
        // Check if the stack is empty
        return (st.size() == 0);
    }

    public int getMin() {
        // Finds minimum element of Stack
        return (st.size() == 0)?-1:min;
    }
}

class SpecialStackWithExtraStack {
    Stack<Integer> st = new Stack<>();
    Stack<Integer> min = new Stack<>();
    public SpecialStackWithExtraStack() {
        // Define Stack
    }

    public void push(int x) {
        // Add an element to the top of Stack
        st.push(x);
        if(min.size() == 0 || min.peek()>x) min.push(x);
        else min.push(min.peek());
    }

    public void pop() {
        // Remove the top element from the Stack
        st.pop();
        min.pop();
    }

    public int peek() {
        // Returns top element of the Stack
        return (st.size() == 0)?-1:st.peek();
    }

    boolean isEmpty() {
        // Check if the stack is empty
        return (st.size() == 0);
    }

    public int getMin() {
        // Finds minimum element of Stack
        return (st.size() == 0)?-1:min.peek();
    }
}
