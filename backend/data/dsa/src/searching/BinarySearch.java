package searching;

public class BinarySearch {
    public static void main(String[] args) {
        System.out.println(search(new int[]{-1,0,3,5,9,12},9));
        System.out.println(searchFirstOccurrence(new int[]{-1,0,1, 1, 1, 1, 2},1));
        System.out.println(searchLastOccurrence(new int[]{-1,0,1, 1, 1, 1, 2},1));
    }

    public static int search(int[] arr, int target) {
        int n = arr.length;
        int lo = 0, hi = n-1;

        while(lo <= hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>target) hi = mid-1; //GoLeft
            else if (arr[mid]<target) lo = mid+1; //GoRight
            else return mid;
        }

        return -1;
    }

    //If multiple occurrences are there, please return the smallest index
    public static int searchFirstOccurrence(int[] arr, int target) {
        int n = arr.length;
        int lo = 0, hi = n-1;
        int ans = -1;
        while(lo <= hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>target) hi = mid-1; //GoLeft
            else if (arr[mid]<target) lo = mid+1; //GoRight
            else { // Found, Now search for occurances
                hi = mid-1; //Go Left
                ans = mid;
            };
        }

        return ans;
    }

    public static int searchLastOccurrence(int[] arr, int target) {
        int n = arr.length;
        int lo = 0, hi = n-1;
        int ans = -1;
        while(lo <= hi){
            int mid = (lo+hi)/2;
            if(arr[mid]>target) hi = mid-1; //GoLeft
            else if (arr[mid]<target) lo = mid+1; //GoRight
            else {// Found, Now search for occurrences
                lo = mid+1; //Go Right
                ans = mid;
            };
        }

        return ans;
    }
}
