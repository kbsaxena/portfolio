package heaps;

import java.util.Collections;
import java.util.PriorityQueue;

public class KthSmallestElement {
    //Method 4 Using Max Heap
    //TC = O(nlogk)
    public static int kthSmallest4(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int ele: arr){
            maxHeap.add(ele);
            if(maxHeap.size()>k) maxHeap.remove();
        }
        return maxHeap.peek();
    }

    //Method 3 Merge Sort
    //TC = O(nlogn)
    public static int kthSmallest3(int[] arr, int k){
        mergeSort(arr);     // sort the array
        return arr[k-1];    // kth smallest element
    }

    private static void mergeSort(int[] arr) {
        int n = arr.length;
        if(n == 1) return;

        int[] a = new int[n/2];
        int[] b = new int[n - n/2];

        for(int i = 0; i < a.length; i++)
            a[i] = arr[i];

        for(int i = 0; i < b.length; i++)
            b[i] = arr[i + n/2];

        mergeSort(a);
        mergeSort(b);

        merge(a, b, arr);
    }

    private static void merge(int[] a, int[] b, int[] c) {
        int i = 0, j = 0, k = 0;

        while(i < a.length && j < b.length){
            if(a[i] <= b[j])
                c[k++] = a[i++];
            else
                c[k++] = b[j++];
        }

        while(i < a.length)
            c[k++] = a[i++];

        while(j < b.length)
            c[k++] = b[j++];
    }

    //Method 2 Using Bubble Sort Reversed
    //TC = O(n*k)
    public static int kthSmallest2(int[] arr, int k){
        //After Each Pass the smallest element is at the start
        int n = arr.length;
        for(int i=0;i<k;i++){
            for(int j=n-1;j>=1+i;j--){
                if(arr[j-1]>arr[j]){//Swaps
                    int temp = arr[j]; arr[j] = arr[j-1]; arr[j-1] = temp;
                }
            }
        }
        return arr[k-1];
    }

    //Method 1 Using Selection Sort
    //TC = O(n*k)
    public static int kthSmallest1(int[] arr, int k) {
        int n = arr.length;
        for(int i=0; i<k; i++){
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for(int j=i; j<n; j++){
                if(arr[j]<min){
                    min = arr[j];
                    minIndex = j;
                }
            }

            //Swap arr[i] with arr[minIndex]
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr[k-1];
    }
}
