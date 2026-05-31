package heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class KClosestElements {
    // Pair class to store element and its distance from x
    class Pair implements Comparable<Pair>{
        int ele;   // actual element
        int diff;  // distance from x

        Pair(int ele, int diff){
            this.ele = ele;
            this.diff = diff;
        }

        @Override
        public int compareTo(Pair p) {
            // sorting by distance (smaller distance = higher priority)
            if (this.diff == p.diff) {
                // Tie case:
                // Prefer larger element → so smaller one should come first
                // (because we are using maxHeap with reverseOrder)
                return Integer.compare(p.ele, this.ele);
            }
            // Sort based on distance
            return Integer.compare(this.diff, p.diff);
        }
    }

    int[] printKClosest(int[] arr, int k, int x) {

        // Max Heap to keep track of K closest elements
        // reverseOrder → largest (farthest) element stays on top
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // Traverse all elements
        for (int ele : arr) {

            // Skip x itself as per problem requirement
            if(ele == x) continue;

            // Calculate absolute difference from x
            int diff = Math.abs(x - ele);

            // Add element with its distance into heap
            maxHeap.add(new Pair(ele, diff));

            // Maintain heap size = K
            // Remove farthest element if size exceeds K
            if (maxHeap.size() > k) maxHeap.remove();
        }

        // Result array
        int[] ans = new int[k];

        // Fill from end because heap gives farthest → closest
        int idx = k - 1;

        while(!maxHeap.isEmpty()){
            Pair top = maxHeap.remove();

            // Place element in reverse order to get closest → farthest
            ans[idx--] = top.ele;
        }

        return ans;
    }
}
