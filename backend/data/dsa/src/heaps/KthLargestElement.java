package heaps;

import java.util.PriorityQueue;

public class KthLargestElement {
    // Function to return kth largest element from an array.
    public static int KthLargest(int arr[], int k) {
        // Your code here
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int ele: arr){
            minHeap.add(ele);
            if(minHeap.size()>k) minHeap.remove();
        }
        return minHeap.peek();
    }
}
