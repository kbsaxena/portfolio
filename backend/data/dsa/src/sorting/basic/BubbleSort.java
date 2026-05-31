package sorting.basic;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        bubbleSort(arr);
        bubbleSortOptimised(arr);
        print(arr);

        int[] arr1 = {5,4,3,2,1};
        bubbleSortReversed(arr1);
        print(arr1);
    }

    private static void print(int[] arr) {
        for(int a: arr) System.out.print(a + " ");
        System.out.println();
    }

    //T.C.(Best, Worst and Average) = O(n^2)
    public static void bubbleSort(int[] arr){
        //After Each Pass the largest element is at the end
        int n = arr.length;
        for(int i=0;i<n-1;i++){ //n-1 passes
            for(int j=0;j<n-1-i;j++){ //used n-1-i instead of n-1 because with each pass the greatest element moves to right
                if(arr[j]>arr[j+1]){ //Swaps
                    int temp = arr[j]; arr[j] = arr[j+1]; arr[j+1] = temp;
                }
            }
        }
        //n-1 + n-2 + n-3 + ... 3 + 2 + 1 = (n-1)*n/2 = No Of Operations
    }

    //T.C.(Worst and Average) = O(n^2)
    //T.C.(Best) = O(n) => If array is already sorted then doing a check
    public static void bubbleSortOptimised(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) { //n-1 passes
            int swaps = 0;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {//Swaps
                    swaps++;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (swaps == 0) break; // That means no swap happened and its already sorted
        }
    }

    public static void bubbleSortReversed(int[] arr){
        //After Each Pass the smallest element is at the start
        int n = arr.length;
        for(int i=0;i<n-1;i++){
            for(int j=n-1;j>=1+i;j--){
                if(arr[j-1]>arr[j]){//Swaps
                    int temp = arr[j]; arr[j] = arr[j-1]; arr[j-1] = temp;
                }
            }
        }
    }
}
