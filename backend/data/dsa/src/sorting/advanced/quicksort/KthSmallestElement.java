package sorting.advanced.quicksort;

import java.util.Collections;
import java.util.PriorityQueue;

public class KthSmallestElement {
    //Method 5 Using Quick Select (QuickSort)
    //TC = O(n)
    public static int kthSmallest(int[] arr, int k) {
        return quickSort(arr,0, arr.length-1, k);
    }

    public static int quickSort(int[] arr, int lo, int hi, int k) {
        int pivot = partition(arr,lo,hi); //pivot+1 th smallest element
        if(pivot+1 == k) return arr[pivot];
        else if(pivot+1 < k) return quickSort(arr,pivot+1,hi, k); //go right
        else return quickSort(arr,lo,pivot-1,k); //go left
    }

    private static int partition(int[] arr, int lo, int hi) {
        int pivot = arr[lo];
        int i = lo+1, j = hi;
        while(i<=j){
            if(arr[i]<=pivot) i++;
            else if(arr[j]>pivot) j--;
            else{
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        //swap first(lo) with j
        int temp = arr[j];
        arr[j] = arr[lo];
        arr[lo] = temp;
        return j;
    }

    //Method 4 Using Max Heap - This is safest to use
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
