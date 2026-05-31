package queues;

import java.util.LinkedList;
import java.util.Queue;

public class RemoveOddIndexElements {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.add(10);q.add(5);q.add(100);q.add(30);q.add(1);

        removeOddIndex(q);
    }

    private static void removeOddIndex(Queue<Integer> q) {

        int n = q.size();

        for (int i = 1; i <= n; i++) {
            int val = q.remove();

            // keep only even index elements
            if (i % 2 == 0) {
                q.add(val);
            }
        }

        System.out.println("Queue after removing odd index elements: " + q);
    }
}
