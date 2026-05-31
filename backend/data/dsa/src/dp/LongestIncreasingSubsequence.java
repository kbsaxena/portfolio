package dp;

import java.util.ArrayList;
import java.util.Arrays;

public class LongestIncreasingSubsequence {
    //M2 Using Binary Search
    static int lis(int arr[]) {
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(arr[0]);
        for(int i=1;i<arr.length;i++){
            if(arr[i]> ans.get(ans.size()-1)) ans.add(arr[i]);
            else{ //lowerbound is the least element which is >= arr[i]
                int idx = lowerBound(ans, arr[i]);
                ans.set(idx,arr[i]);
            }
        }
        return ans.size();
    }

    private static int lowerBound(ArrayList<Integer> ans, int element) {
        int lo = 0, hi = ans.size()-1, idx = 0;
        while(lo<=hi){
            int mid = (lo+hi)/2;
            if(ans.get(mid) >= element) { //possible lower bound
                idx = mid;
                hi = mid -1; //go left
            } else lo = mid+1; // go right
        }
        return idx;
    }

    //M1 Using Dp Tabulation
    static int lis1(int arr[]) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        for(int i=1;i<n;i++){
            for(int j=i-1;j>=0;j--){ //Travels i-1 to 0 for every i
                if(arr[i] > arr[j]){
                    dp[i] = Math.max(dp[i], 1+dp[j]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}
