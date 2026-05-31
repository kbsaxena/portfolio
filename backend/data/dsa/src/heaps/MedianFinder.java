package heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public void addNum(int ele) {
        if(maxHeap.size()==0 || ele<=maxHeap.peek()) maxHeap.add(ele);
        else minHeap.add(ele);

        if(maxHeap.size()-minHeap.size()>1) minHeap.add(maxHeap.remove());
        if(minHeap.size()-maxHeap.size()>1) maxHeap.add(minHeap.remove());
    }

    public double findMedian() {
        if(maxHeap.size()>minHeap.size()) return maxHeap.peek();
        else if(maxHeap.size()<minHeap.size()) return minHeap.peek();
        else return (maxHeap.peek() + minHeap.peek())/2.0;
    }
}
