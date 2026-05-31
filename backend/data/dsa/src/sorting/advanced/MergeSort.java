package sorting.advanced;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {10,2,33,50,0,3,100,1};
        print(arr);
        mergeSort(arr);
        print(arr);
    }

    private static void mergeSort(int[] arr) {
        int n = arr.length;
        if(n == 1) return;
        int[] a = new int[n/2];
        int[] b = new int[n-n/2];
        for(int i=0;i<a.length;i++) a[i] = arr[i];
        for(int i=0;i<b.length;i++) b[i] = arr[i+n/2];
        mergeSort(a);
        mergeSort(b);
        merge(a,b, arr);
    }

    private static void print(int[] c) {
        for(int ele: c){
            System.out.print(ele + " ");
        }
        System.out.println();
    }

    private static void merge(int[] a, int[] b, int[] c) {
        int i=0, j=0, k=0;
        while(i<a.length && j<b.length){
            if(a[i] <= b[j]) c[k++] = a[i++];
            else c[k++] = b[j++];
        }

        while(i<a.length) c[k++] = a[i++];
        while(j<b.length) c[k++] = b[j++];
    }
}
