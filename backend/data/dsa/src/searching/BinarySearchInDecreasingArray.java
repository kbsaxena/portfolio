package searching;

public class BinarySearchInDecreasingArray {

    public static void main(String[] args) {
        System.out.println(search(new int[]{100,90,83,52,19,2},19));
    }

    public static int search(int[] arr, int target) {
        int n = arr.length;
        int lo = 0, hi = n-1;

        while(lo <= hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>target) lo = mid+1;//GoRight
            else if (arr[mid]<target) hi = mid-1;  //GoLeft
            else return mid;
        }

        return -1;
    }
}
