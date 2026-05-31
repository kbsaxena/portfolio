package sorting.basic;

public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        selectionSort(arr);
        print(arr);

        int[] arr1 = {5,4,3,2,1};
        selectionSortLargestFirst(arr1);
        print(arr1);
    }

    private static void selectionSort(int[] arr) {
        //Find Smallest element in each pass and replace with leftmost element
        int n = arr.length;
        for(int i=0; i<n-1; i++){
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
    }

    private static void selectionSortLargestFirst(int[] arr) {
        int n = arr.length;
        for(int i=0; i<n-1; i++){
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;
            for(int j=0; j<n-i; j++){
                if(arr[j]>max){
                    max = arr[j];
                    maxIndex = j;
                }
            }

            //Swap arr[i] with arr[minIndex]
            int temp = arr[n-1-i];
            arr[n-1-i] = arr[maxIndex];
            arr[maxIndex] = temp;
        }
    }

    private static void print(int[] arr) {
        for(int a: arr) System.out.print(a + " ");
        System.out.println();
    }

}
