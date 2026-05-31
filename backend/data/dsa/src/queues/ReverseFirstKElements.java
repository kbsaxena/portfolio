package queues;

import java.util.Queue;
import java.util.Stack;

public class ReverseFirstKElements {
    static void reverseFirstK(Queue<Integer> q, int k) {

        Stack<Integer> st = new Stack<>();
        int n = q.size();

        // push first k elements (index 1..k)
        for (int i = 1; i <= k; i++) {
            st.push(q.remove());
        }

        // add back reversed
        while (!st.isEmpty()) {
            q.add(st.pop());
        }

        // rotate remaining elements
        for (int i = 1; i <= n - k; i++) {
            q.add(q.remove());
        }

        System.out.println(q);
    }
}
