package deque;

import java.util.*;

public class DequeExample {

    public static void main(String[] args) {
        Deque<Integer> dq = new LinkedList<>();

        // addFirst()
        dq.addFirst(10);
        dq.addFirst(20);
        System.out.println("After addFirst: " + dq); // [20, 10]

        // addLast()
        dq.addLast(30);
        dq.addLast(40);
        System.out.println("After addLast: " + dq); // [20, 10, 30, 40]

        // removeFirst()
        int removedFirst = dq.removeFirst();
        System.out.println("removeFirst: " + removedFirst); // 20
        System.out.println("After removeFirst: " + dq); // [10, 30, 40]

        // remove()
        int removed = dq.remove(); // same as removeFirst()
        System.out.println("remove(): " + removed); // 10
        System.out.println("After remove(): " + dq); // [30, 40]

        // poll()
        Integer polled = dq.poll(); // removes first element, returns null if empty
        System.out.println("poll(): " + polled); // 30
        System.out.println("After poll(): " + dq); // [40]

        // pollFirst()
        Integer pollFirst = dq.pollFirst();
        System.out.println("pollFirst(): " + pollFirst); // 40
        System.out.println("After pollFirst: " + dq); // []

        // poll on empty deque
        System.out.println("poll on empty: " + dq.poll()); // null
    }
}
