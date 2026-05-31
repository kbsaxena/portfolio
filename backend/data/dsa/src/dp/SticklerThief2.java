package dp;

import java.util.Arrays;

public class SticklerThief2 {
    //Method 2 Using DP
    public int maxValue(int arr[]) {
        int n = arr.length;
        if(n == 1) return arr[0];

        //Case 1: Exclude Last
        int[] dp1 = new int[n]; //i : 0 to n-1
        Arrays.fill(dp1, -1);
        int case1 = loot(arr, 0, n-2, dp1);

        //Case 2: Exclude First
        int[] dp2 = new int[n]; //i : 0 to n-1
        Arrays.fill(dp2, -1);
        int case2 = loot(arr, 1, n-1, dp2);

        return Math.max(case1, case2);
    }

    private int loot(int[] arr, int i, int end, int[] dp){
        if(i > end) return 0;
        if(dp[i] != -1) return dp[i];
        int pick = arr[i] + loot(arr,i+2, end, dp);
        int skip = loot(arr, i+1, end, dp);
        return dp[i] = Math.max(pick, skip);
    }

    //Method 1 Using Simple recursion TC=O(2^n)
    public int maxValue1(int arr[]) {
        int n = arr.length;
        if(n==1) return arr[0];
        return Math.max(loot(arr, 0, n-2), loot(arr, 1, n-1));
    }

    private int loot(int[] arr, int i, int end){
        if(i > end) return 0;
        int pick = arr[i] + loot(arr,i+2, end);
        int skip = loot(arr, i+1, end);
        return Math.max(pick, skip);
    }
}
