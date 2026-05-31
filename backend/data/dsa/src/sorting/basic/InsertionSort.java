package sorting.basic;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = {5,4,3,2,1};
        insertionSort(arr);
        print(arr);
    }

    private static void insertionSort(int[] arr) {
        //Find Smallest element in each pass and replace with leftmost element
        int n = arr.length;
        for(int i=1; i<n; i++){
            int j = i;
            while(j>0 && arr[j]<arr[j-1]){
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            }

        }
    }

    private static void print(int[] arr) {
        for(int a: arr) System.out.print(a + " ");
        System.out.println();
    }

}
