package dp;

import java.util.Arrays;

public class MinimumCostClimbingStairs {
    //Method 4 Using DP(Tabulation Space Optimized)
    static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[3];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for(int i = 2;i<n;i++){
            dp[2] = cost[i] + Math.min(dp[1], dp[0]);
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return Math.min(dp[1], dp[0]);
    }

    //Method 3 Using DP(Tabulation)
    static int minCostClimbingStairs3(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        dp[0] = cost[0];
        if(n>1) dp[1] = cost[1];
        for(int i = 2;i<n;i++){
            dp[i] = cost[i] + Math.min(dp[i-1], dp[i-2]);
        }
        return Math.min(dp[n-2], dp[n-1]);
    }

    //Method 2 Using DP(Memoization)
    static int minCostClimbingStairs2(int[] cost) {
        int[] dp = new int[cost.length];
        Arrays.fill(dp, -1);
        return Math.min(minCost(cost, 0, dp), minCost(cost, 1, dp));
    }

    private static int minCost(int[] cost, int i, int[] dp) {
        if(i>=cost.length) return 0;
        if(dp[i] != -1) return dp[i];
        return dp[i] = cost[i] + Math.min(minCost(cost, i+1, dp), minCost(cost,i+2, dp));
    }

    //Method 1: Using Simple Recursion
    static int minCostClimbingStairs1(int[] cost) {
        return Math.min(minCost(cost, 0), minCost(cost, 1));
    }

    private static int minCost(int[] cost, int i) {
        if(i>=cost.length) return 0;
        return cost[i] + Math.min(minCost(cost, i+1), minCost(cost,i+2));
    }
}
