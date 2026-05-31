package queues;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ReverseAQueue {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.add(10);q.add(5);q.add(100);q.add(30);q.add(1);

        reverseQueue(q);

        System.out.println(q);
    }

    private static void reverseQueue(Queue<Integer> q) {
        Stack<Integer> st = new Stack<>();

        // Move all elements from queue to stack
        while (!q.isEmpty()) {
            st.push(q.remove());
        }

        // Move elements back to queue
        while (!st.isEmpty()) {
            q.add(st.pop());
        }
    }
}
