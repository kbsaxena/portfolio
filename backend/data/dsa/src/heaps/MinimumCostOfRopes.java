package heaps;

import java.util.PriorityQueue;

public class MinimumCostOfRopes {
    public static int minCost(int[] arr) {
        // code here
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int ele: arr) pq.add(ele);
        int cost = 0;
        while(pq.size()>1){
            int a = pq.remove();
            int b = pq.remove();
            cost += (a+b);
            pq.add(a+b);
        }
        return cost;
    }
}
