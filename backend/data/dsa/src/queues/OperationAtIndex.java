package queues;

import java.util.LinkedList;
import java.util.Queue;

public class OperationAtIndex {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.add(10);q.add(5);q.add(100);q.add(30);q.add(1);

        System.out.println(q);
        peekAtIndex(2, q);
        removeAtIndex(1, q);
        addAtIndex(1, 90, q);
    }

    private static void peekAtIndex(int idx, Queue<Integer> q) {
        int n = q.size();
        // Move idx elements to bring target to front
        for(int i=1;i<=idx;i++){
            q.add(q.remove());
        }
        System.out.println("Peek at " + idx + " is - " + q.peek());

        // Restore queue order
        for(int i=1;i<=n-idx;i++){
            q.add(q.remove());
        }
    }

    private static void removeAtIndex(int idx, Queue<Integer> q) {
        int n = q.size();
        // Move idx elements so target comes to front
        for(int i=1;i<=idx;i++){
            q.add(q.remove());
        }
        // Remove the element
        q.remove();

        // Restore queue order
        for(int i=1;i<=n-idx-1;i++){
            q.add(q.remove());
        }
        System.out.println("After remove at " + idx + " the queue is " + q);
    }

    private static void addAtIndex(int idx, int value, Queue<Integer> q) {
        int n = q.size();
        // Move idx elements so insertion position comes to rear
        for(int i=1;i<=idx;i++){
            q.add(q.remove());
        }

        // Insert new element
        q.add(value);

        // Restore queue order
        for(int i=1;i<=n-idx;i++){
            q.add(q.remove());
        }
        System.out.println("After add at " + idx + " the queue is " + q);
    }

}
