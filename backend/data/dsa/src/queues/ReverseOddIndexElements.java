package queues;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ReverseOddIndexElements {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.add(10);q.add(20);q.add(30);
        q.add(40);q.add(50);q.add(60);
        System.out.println("i/p -" + q);
        reverseOddIndex(q);
        System.out.println("o/p -" + q);
    }
    private static void reverseOddIndex(Queue<Integer> q) {

        Stack<Integer> st = new Stack<>();
        int n = q.size();

        // Step 1: Push odd index elements into stack
        for (int i = 1; i <= n; i++) {
            int val = q.remove();

            if (i % 2 != 0) {   // odd index
                st.push(val);
            }

            q.add(val); // restore queue
        }

        // Step 2: Replace odd index elements using stack
        for (int i = 1; i <= n; i++) {
            int val = q.remove();

            if (i % 2 != 0) {
                q.add(st.pop());
            } else {
                q.add(val);
            }
        }
    }
}
