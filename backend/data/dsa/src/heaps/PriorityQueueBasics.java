package heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class PriorityQueueBasics {
    public static void main(String[] args) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.add(3);minHeap.add(7);minHeap.add(4);
        System.out.println(minHeap);
        minHeap.add(2);minHeap.add(6);minHeap.add(1);
        System.out.println(minHeap);
        minHeap.add(8);minHeap.add(5);minHeap.add(0);
        System.out.println(minHeap);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(3);maxHeap.add(7);maxHeap.add(4);
        System.out.println(maxHeap);
        maxHeap.add(2);maxHeap.add(6);maxHeap.add(1);
        System.out.println(maxHeap);
        maxHeap.add(8);maxHeap.add(5);maxHeap.add(0);
        System.out.println(maxHeap);
    }
}
