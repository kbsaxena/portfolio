package dp;

import java.util.Arrays;

public class SticklerThief {
    //Method 4 Using Tabulation(Space Optimized)
    public int findMaxSum(int arr[]) {
        int n = arr.length;
        int[] dp = new int[3];
        dp[0] = arr[0];
        if(n>1) dp[1] = Math.max(arr[0], arr[1]);
        if(n <= 2) return dp[n-1];
        for(int i = 2;i<n;i++){
            int pick = arr[i] + dp[0];
            int skip = dp[1];
            dp[2] = Math.max(pick, skip);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }

        return dp[2];
    }

    //Method 3 Using Andhadhoon Tabulation
    public int findMaxSumAT(int arr[]) {
        int n = arr.length;
        int[] dp = new int[n]; //i : 0 to n-1
        for(int i=0;i<=n-1;i++){
            int pick = arr[i] + ((i-2>=0)?dp[i-2]:0);
            int skip = ((i-1>=0)? dp[i-1] :0);
            dp[i] = Math.max(pick, skip);
        }
        return dp[n-1];
    }

    //Method 3 Using Tabulation
    public int findMaxSum3(int arr[]) {
        int n = arr.length;
        int[] dp = new int[n]; //i - 0 to n-1
        dp[0] = arr[0];
        if(n>1) dp[1] = Math.max(arr[0], arr[1]);
        for(int i = 2;i<n;i++){
            int pick = arr[i] + dp[i-2];
            int skip = dp[i-1];
            dp[i] = Math.max(pick, skip);
        }

        return dp[n-1];
    }

    //Method 2 Using DP(Memoization)
    public int findMaxSum2(int arr[]) {
        int[] dp = new int[arr.length]; //i : 0 to n-1
        Arrays.fill(dp, -1);
        return loot(arr, 0, dp);
    }

    private int loot(int[] arr, int i, int[] dp){
        if(i>=arr.length) return 0;
        if(dp[i] != -1) return dp[i];
        int pick = arr[i] + loot(arr,i+2, dp);
        int skip = loot(arr, i+1, dp);
        return dp[i] = Math.max(pick, skip);
    }

    //Method 1 Using Simple recursion TC=O(2^n)
    public int findMaxSum1(int arr[]) {
        return loot(arr, 0);
    }

    private int loot(int[] arr, int i){
        if(i>=arr.length) return 0;
        int pick = arr[i] + loot(arr,i+2);
        int skip = loot(arr, i+1);
        return Math.max(pick, skip);
    }
}
