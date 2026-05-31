package sorting.advanced.quicksort;

public class QuickSort {
    public void quickSort(int[] arr, int lo, int hi) {
        if(lo>=hi) return;
        int pivot = partition(arr,lo,hi);
        quickSort(arr,lo,pivot-1);
        quickSort(arr,pivot+1,hi);
    }

    //With last element as pivot
    private int partition(int[] arr, int lo, int hi) {
        int pivot = arr[hi];
        int i=lo, j=hi-1;
        while(i<=j){
            if(arr[i]<=pivot) i++;
            else if(arr[j]>pivot) j--;
            else{
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        //swap last(hi) with i
        int temp = arr[i];
        arr[i] = arr[hi];
        arr[hi] = temp;

        return i;
    }

    private int partitionWithFirstElementPivot(int[] arr, int lo, int hi) {
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

    private static int partitionWithMiddleElementPivot(int[] arr, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;

        // Move mid element to start (lo)
        int temp = arr[mid];
        arr[mid] = arr[lo];
        arr[lo] = temp;

        int pivot = arr[lo];
        int i = lo + 1, j = hi;

        while (i <= j) {
            if (arr[i] <= pivot) i++;
            else if (arr[j] > pivot) j--;
            else {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        // Place pivot at correct position
        int t = arr[j];
        arr[j] = arr[lo];
        arr[lo] = t;

        return j;
    }
}
