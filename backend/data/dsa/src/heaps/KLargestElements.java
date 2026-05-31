package heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class KLargestElements {
    public ArrayList<Integer> kLargest(int[] arr, int k) {
        // Your code here
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int ele: arr){
            minHeap.add(ele);
            if(minHeap.size()>k) minHeap.remove();
        }
        ArrayList<Integer> ans = new ArrayList<>();
        while(minHeap.size()>0){
            ans.add(minHeap.remove());
        }
        Collections.reverse(ans);

        return ans;
    }
}
