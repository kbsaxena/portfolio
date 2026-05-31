package sorting.advanced;

public class InversionsCount {
    static int count;
    public static void main(String[] args) {
        int[] arr = {5,2,8,4,1,6,7,3};
        int c = inversionCount(arr);
        System.out.println("Total inversion count - " + c);
    }

    //Brute Force
    private static int inversionCount1(int[] arr) {
        int count = 0;
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]>arr[j]){
                    count++;
                }
            }
        }

        return count;
    }

    //Using Merge Sort O(nlogn)
    static int inversionCount(int arr[]) {
        count = 0;
        mergeSort(arr);
        return count;
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

    private static void merge(int[] a, int[] b, int[] c) {
        int i=0, j=0, k=0;
        while(i<a.length && j<b.length){
            if(a[i] <= b[j]) c[k++] = a[i++];
            else {
                count += a.length - i;
                c[k++] = b[j++];
            }
        }

        while(i<a.length) c[k++] = a[i++];
        while(j<b.length) c[k++] = b[j++];
    }

}
