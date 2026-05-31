package recursion;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = IntStream.of(19, 2, 39, 4, 5, 56, 100, 8, 20, 27)
                .sorted()
                .toArray();
        int target = 56;
        System.out.println(Arrays.toString(arr));
        System.out.println(search(arr, target));
        System.out.println(binarysearch(arr, target));
    }

    private static int search(int[] arr, int target) {
        return binary(0, arr.length-1, arr, target);
    }

    //Recursive BS (Logn complexity)
    private static int binary(int lo, int hi, int[] arr, int target) {
        if(lo>hi) return -1;
        int mid = (lo+hi)/2;
        if(arr[mid] == target) return mid;
        if(arr[mid]>target) return binary(lo, mid-1, arr, target);
        else return binary(mid+1, hi, arr, target);
    }

    //Normal Way to do BS (Logn complexity)
    public static int binarysearch(int[] arr, int target) {
        int n = arr.length;
        int lo = 0, hi = n-1;
        int ans = -1;
        while(lo <= hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>target) hi = mid-1; //GoLeft
            else if (arr[mid]<target) lo = mid+1; //GoRight
            else {
                hi = mid-1; //Go Left
                ans = mid;
            }
        }

        return ans;
    }
}
