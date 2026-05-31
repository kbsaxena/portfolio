package heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneWeight {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int stone : stones) {
            pq.add(stone);
        }

        while(pq.size()>1){
            int a = pq.remove(); //largest
            int b = pq.remove(); //second largest
            if(a != b) pq.add(a-b);
        }

        return pq.isEmpty() ? 0 : pq.peek();
    }
}
