package queues;

import java.util.Queue;
import java.util.Stack;

public class ReArrangeQueue {
    //Method 1
    public static void rearrangeM1(Queue<Integer> q) {
        Stack<Integer> st = new Stack<>();
        int n = q.size();
        int half = n / 2;

        // Step 1: first half queue -> stack
        for(int i=0;i<half;i++){
            st.push(q.remove());
        }

        // Step 2: stack -> queue
        while(!st.isEmpty()){
            q.add(st.pop());
        }

        // Step 3: move first half again to stack
        for(int i=0;i<half;i++){
            st.push(q.remove());
        }

        // Step 4: interleave
        while(!st.isEmpty()){
            q.add(st.pop());
            q.add(q.remove());
        }

        // Step 5: reverse whole queue using stack
        while(!q.isEmpty()){
            st.push(q.remove());
        }

        while(!st.isEmpty()){
            q.add(st.pop());
        }
    }

    //Method 2
    public static void rearrangeM2(Queue<Integer> q) {
        Stack<Integer> st = new Stack<>();
        int n = q.size();
        int half = n / 2;

        // Step 1: queue -> stack
        while(!q.isEmpty()){
            st.push(q.remove());
        }

        // Step 2: stack -> queue (half elements)
        for(int i=0;i<half;i++){
            q.add(st.pop());
        }

        // Step 3: interleave
        while(!st.isEmpty()){
            q.add(q.remove());
            q.add(st.pop());
        }

        // Step 4: reverse using stack
        while(!q.isEmpty()){
            st.push(q.remove());
        }

        while(!st.isEmpty()){
            q.add(st.pop());
        }
    }

}
