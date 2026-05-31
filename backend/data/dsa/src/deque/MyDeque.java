package deque;

import java.util.*;

class MyDeque {
    Deque<Integer> dq = new LinkedList<>();

    // a) add at index
    public void add(int idx, int val) {
        Deque<Integer> temp = new LinkedList<>();

        // Move first idx elements
        for (int i = 0; i < idx; i++) {
            temp.addLast(dq.removeFirst());
        }

        // Add new value
        temp.addLast(val);

        // Add remaining elements
        while (!dq.isEmpty()) {
            temp.addLast(dq.removeFirst());
        }

        dq = temp;
    }

    // b) get value at index
    public int get(int idx) {
        int val = -1;
        int size = dq.size();

        for (int i = 0; i < size; i++) {
            int curr = dq.removeFirst();
            if (i == idx) val = curr;
            dq.addLast(curr);
        }

        return val;
    }

    // c) remove at index
    public void remove(int idx) {
        int size = dq.size();

        for (int i = 0; i < size; i++) {
            int curr = dq.removeFirst();
            if (i != idx) {
                dq.addLast(curr);
            }
        }
    }

    // d) reverse deque
    public void reverse() {
        Stack<Integer> st = new Stack<>();

        while (!dq.isEmpty()) {
            st.push(dq.removeFirst());
        }

        while (!st.isEmpty()) {
            dq.addLast(st.pop());
        }
    }

    //Without extra space
    public void reverse(Deque<Integer> dq) {
        int size = dq.size();

        for (int i = 0; i < size / 2; i++) {
            int front = dq.removeFirst();
            int back = dq.removeLast();

            dq.addFirst(back);
            dq.addLast(front);
        }
    }

    // Utility to print
    public void print() {
        System.out.println(dq);
    }
}
