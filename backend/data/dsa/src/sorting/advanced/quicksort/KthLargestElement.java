package sorting.advanced.quicksort;

import java.util.PriorityQueue;

public class KthLargestElement {
    public static int KthLargest(int arr[], int k) {
        int n = arr.length;
        return quickSelect(arr, 0, n - 1, n - k + 1); // convert
    }

    private static int quickSelect(int[] arr, int lo, int hi, int k) {
        if (lo <= hi) {
            int pivotIndex = partition(arr, lo, hi);

            if (pivotIndex + 1 == k) return arr[pivotIndex];
            else if (pivotIndex + 1 < k) return quickSelect(arr, pivotIndex + 1, hi, k);
            else return quickSelect(arr, lo, pivotIndex - 1, k);
        }
        return -1; // edge case
    }

    // 🔹 Partition (middle pivot for better balance)
    private static int partition(int[] arr, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;

        // move mid → lo
        swap(arr, mid, lo);

        int pivot = arr[lo];
        int i = lo + 1, j = hi;
        while (i <= j) {
            if (arr[i] <= pivot) i++;
            else if (arr[j] > pivot) j--;
            else swap(arr, i, j);
        }
        // place pivot correctly
        swap(arr, lo, j);
        return j;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Function to return kth largest element from an array.
    public static int KthLargest1(int arr[], int k) {
        // Your code here
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int ele: arr){
            minHeap.add(ele);
            if(minHeap.size()>k) minHeap.remove();
        }
        return minHeap.peek();
    }
}
