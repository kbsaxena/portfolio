package queues;

import java.util.LinkedList;
import java.util.Queue;

public class TraversingAQueue {
    public static void main(String[] args) {
        Queue<Integer> q = new LinkedList<>();
        q.add(10);q.add(5);q.add(100);q.add(30);q.add(1);
        int sum = 0;

        int n = q.size();
        for(int i=1;i<=n;i++){
            int peek = q.peek();
            sum = sum+peek;
            System.out.print(peek+ " ");
            q.add(q.remove());
        }

        System.out.println(sum);
        System.out.println(q);
    }
}
