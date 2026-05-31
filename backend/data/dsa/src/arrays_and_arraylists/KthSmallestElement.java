package arrays_and_arraylists;

public class KthSmallestElement {
    public static void main(String[] args) {
        System.out.println(kthSmallest(new int[]{7, 10, 4, 3, 20, 15} , 3));
    }

    //Using Selection Sort Algo
    //TC = O(n*k)
    public static int kthSmallest(int[] arr, int k) {
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

    //Using Bubble Sort Reversed
    public static int kthSmallest1(int[] arr, int k){
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
}
