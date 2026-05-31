package dp;

import java.util.Arrays;

public class PartitionEqualSubsetSum {
    // Method 3 Andhadhoon Dp Tabulation
    static boolean equalPartitionAT(int arr[]) {
        int arraySum = 0;
        for (int ele : arr) arraySum += ele;
        if (arraySum % 2 == 1) return false;
        int n = arr.length;
        int target = arraySum / 2;
        Boolean[][] dp = new Boolean[n][target+1];
        for(int i=0;i<=n-1;i++){
            for(int j=0;j<=target;j++){
                if(j - arr[i] >= 0){
                    //if(i == arr.length) return (target == 0); here j is replacing target
                    boolean pick = ((i-1>=0)?dp[i-1][j-arr[i]]:(j==0));
                    boolean skip = ((i-1>=0)?dp[i-1][j]:(j==0));
                    dp[i][j] = pick || skip;
                } else {
                    boolean skip = ((i-1>=0)?dp[i-1][j]:(j==0));
                    dp[i][j] = skip;
                }
            }
        }
        return dp[n-1][target];
    }

    // Method 3 Dp Tabulation
    static boolean equalPartition(int arr[]) {
        int arraySum = 0;
        for (int ele : arr) arraySum += ele;
        if (arraySum % 2 == 1) return false;
        int n = arr.length;
        int target = arraySum / 2;
        boolean[][] dp = new boolean[n+1][target + 1]; //Use boolean because you don’t need a third state (null) in tabulation.

        //handling base case if(i == arr.length) return (target == 0);
        dp[n][0] = true;

        //Same as recursion: i from 0→n-1 becomes n-1→0
        for (int i = n - 1; i >= 0; i--) {
            for (int t = 0; t <= target; t++) {
                if (t - arr[i] >= 0) {
                    boolean pick = dp[i + 1][t - arr[i]];
                    boolean skip = dp[i + 1][t];
                    dp[i][t] = pick || skip;
                } else {
                    boolean skip = dp[i + 1][t];
                    dp[i][t] = skip;
                }
            }
        }
        return dp[0][target];
    }

    //Method2 Using DP Memoization
    static boolean equalPartition2(int arr[]) {
        int arraySum = 0;
        for(int ele: arr) arraySum += ele;
        if(arraySum % 2 == 1) return false;
        int n = arr.length;
        int target = arraySum/2;
        //cannot use boolean as it stores only true and false, then how to store unvisited or -1 value
        Boolean[][] dp = new Boolean[n][target+1]; //auto initializes whole array with null
        return subset(0, target, arr, dp);
    }

    //i -> 0 to n-1   target-> target to 0
    private static boolean subset(int i, int target, int[] arr, Boolean[][] dp) {
        if(i == arr.length) return (target == 0);
        if(dp[i][target] != null) return dp[i][target];
        if(target - arr[i] >= 0){
            boolean pick = subset(i+1, target-arr[i], arr, dp);
            boolean skip = subset(i+1, target, arr, dp);
            return dp[i][target] = pick || skip;
        } else {
            boolean skip = subset(i+1, target, arr, dp);
            return dp[i][target] = skip;
        }
    }

    //Method1 Using Simple Recursion
    static boolean equalPartition1(int arr[]) {
        int arraySum = 0;
        for(int ele: arr) arraySum += ele;
        if(arraySum % 2 == 1) return false;
        int target = arraySum/2;
        return subset(0, target, arr);
    }

    private static boolean subset(int i, int target, int[] arr) {
        if(i == arr.length) return (target == 0);
        if(target - arr[i] >= 0){
            boolean pick = subset(i+1, target-arr[i], arr);
            boolean skip = subset(i+1, target, arr);
            return pick || skip;
        } else {
            boolean skip = subset(i+1, target, arr);
            return skip;
        }
    }
}
