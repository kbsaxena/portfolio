package dp;

import java.util.Arrays;

public class KnapsackWithDuplicateItems {
    //Method 3 Using Andhadhoon Dp Tabulation
    public int knapSackAT(int val[], int wt[], int C) {
        int n = val.length;
        int[][] dp = new int[n][C+1];
        for(int i=0;i<=n-1;i++){
            for(int j=0;j<=C;j++){
                if(j - wt[i] >= 0){
                    int pick = val[i] + dp[i][j-wt[i]];
                    int skip = ((i-1>=0)?dp[i-1][j]:0);
                    dp[i][j] = Math.max(pick,skip);
                } else {
                    int skip = ((i-1>=0)?dp[i-1][j]:0);
                    dp[i][j] = skip;
                }
            }
        }
        return dp[n-1][C];
    }

    //Method 3 Using Dp Tabulation
    public int knapSack(int val[], int wt[], int C) {
        int n = val.length;
        int[][] dp = new int[n + 1][C + 1];
        //Base case is dp[n][cap] = 0, which is already satisfied by default initialization of int array.
        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 0; cap <= C; cap++) {
                if (cap - wt[i] >= 0) {
                    int pick = val[i] + dp[i][cap - wt[i]];
                    int skip = dp[i + 1][cap];
                    dp[i][cap] = Math.max(pick, skip);
                } else {
                    int skip = dp[i + 1][cap];
                    dp[i][cap] = skip;
                }

            }
        }
        return dp[0][C];
    }

    //Method2 Using DP Memoization
    public int knapSack2(int val[], int wt[], int C) {
        int n = val.length;
        int[][] dp = new int[n][C+1];
        for(int[] a: dp){
            Arrays.fill(a, -1);
        }
        return maxLoot(0, C, val, wt, dp);
    }
    // i -> 0 to n-1   C -> C to 0
    private int maxLoot(int i, int C, int[] val, int[] wt, int[][] dp) {
        if(i == val.length) return 0;
        if(dp[i][C] != -1) return dp[i][C];
        if(C - wt[i] >= 0){
            int pick = val[i] + maxLoot(i, C-wt[i], val, wt, dp);
            int skip = maxLoot(i+1, C, val, wt, dp);
            return dp[i][C] = Math.max(pick,skip);
        } else {
            int skip = maxLoot(i + 1, C, val, wt, dp);
            return dp[i][C] = skip;
        }
    }

    //Method1 Using Simple Recursion
    public int knapSack1(int val[], int wt[], int C) {
        return maxLoot(0, C, val, wt);
    }

    private int maxLoot(int i, int C, int[] val, int[] wt) {
        if(i == val.length) return 0;
        if(C - wt[i] >= 0){
            int pick = val[i] + maxLoot(i, C-wt[i], val, wt);
            int skip = maxLoot(i+1, C, val, wt);
            return Math.max(pick,skip);
        } else {
            int skip = maxLoot(i + 1, C, val, wt);
            return skip;
        }
    }

}
