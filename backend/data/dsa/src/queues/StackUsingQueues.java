package queues;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class MyStackAddEfficient {
    Queue<Integer> q = new LinkedList<>();
    public void push(int x) {
        q.add(x);
    }

    public int pop() {
        int n = q.size();
        for(int i=1;i<=n-1;i++){
            q.add(q.remove());
        }
        return q.remove();
    }

    public int top() {
        int n = q.size();
        for(int i=1;i<=n-1;i++){
            q.add(q.remove());
        }
        int peek = q.peek();
        q.add(q.remove());

        return peek;
    }

    public boolean empty() {
        return q.size() == 0;
    }
}

class MyStackPeekPopEfficient {
    Queue<Integer> q = new LinkedList<>();
    public void push(int x) { // Push at front
        q.add(x);
        int n = q.size();
        for(int i=1;i<=n-1;i++) q.add(q.remove());
    }

    public int pop() {
        return q.remove();
    }

    public int top() { //peek
        return q.peek();
    }

    public boolean empty() {
        return q.size() == 0;
    }
}

public class StackUsingQueues {
}
