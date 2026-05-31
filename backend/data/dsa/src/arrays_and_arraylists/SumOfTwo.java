package arrays_and_arraylists;

import java.util.Arrays;
import java.util.HashSet;

public class SumOfTwo {
    public static void main(String[] args) {
        int[] arr = {0, -1, 2, -3, 1};
        int target = -2;
        System.out.println(twoSum(arr, target));
    }

    static boolean twoSum(int arr[], int target) {
        //Brute Force - TC = O(n^2)
        /*
        int n = arr.length;

        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                if(arr[i] + arr[j] == target) return true;
            }
        }
        return false;
        */

        // Sorting - TC = O(nlogn)
        /*
        Arrays.sort(arr); //O(nlogn)
        int n = arr.length;
        int i = 0, j = n-1;

        while(i<j){    //O(n)
            if(arr[i] + arr[j] < target) i++;
            else if(arr[i] + arr[j] > target) j--;
            else return true;
        }

        return false;
        */

        //Using HashSet - TC = O(n)
        HashSet<Integer> hs = new HashSet<>();
        for(int ele: arr){
            if(hs.contains(target-ele)) return true;
            hs.add(ele);
        }
        return false;
    }
}
