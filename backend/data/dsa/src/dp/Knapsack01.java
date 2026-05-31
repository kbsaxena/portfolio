package dp;

import java.util.Arrays;

public class Knapsack01 {
    //Method 4 Using Andhadhoon Dp Tabulation Space Optimized
    public int knapsackSO(int C, int val[], int wt[]) {
        int n = val.length;
        int[][] dp = new int[2][C + 1];
        for (int i = 0; i <= n-1; i++) {
            for (int j = 0; j <= C; j++) {
                if (j - wt[i] >= 0) {
                    int pick = val[i] + ((i - 1) >= 0 ? dp[0][j - wt[i]] : 0);
                    int skip = ((i - 1) >= 0 ? dp[0][j] : 0);
                    dp[1][j] = Math.max(pick, skip);
                } else {
                    int skip = ((i - 1) >= 0 ? dp[0][j] : 0);
                    dp[1][j] = skip;
                }
            }
            //Copy 1st row to 0th row
            for(int j=0;j<= C ;j++){
                dp[0][j] = dp[1][j];
            }

        }
        return dp[1][C];
    }

    //Method 3 Using Andhadhoon Dp Tabulation
    public int knapsackAT(int C, int val[], int wt[]) {
        int n = val.length;
        int[][] dp = new int[n][C + 1];
        for (int i = 0; i <= n-1; i++) {
            for (int j = 0; j <= C; j++) {
                if (j - wt[i] >= 0) {
                    int pick = val[i] + ((i-1)>=0?dp[i - 1][j - wt[i]]:0);
                    int skip = ((i-1)>=0 ? dp[i - 1][j]:0);
                    dp[i][j] = Math.max(pick, skip);
                } else {
                    int skip = ((i-1)>=0 ? dp[i - 1][j]:0);
                    dp[i][j] = skip;
                }

            }
        }
        return dp[n-1][C];
    }

    //Method 3 Using Dp Tabulation
    public int knapsack(int C, int val[], int wt[]) {
        int n = val.length;
        int[][] dp = new int[n + 1][C + 1];
        //Base case is dp[n][cap] = 0, which is already satisfied by default initialization of int array.
        for (int i = n - 1; i >= 0; i--) {
            for (int cap = 0; cap <= C; cap++) {
                if (cap - wt[i] >= 0) {
                    int pick = val[i] + dp[i + 1][cap - wt[i]];
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
    public int knapsack2(int C, int val[], int wt[]) {
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
            int pick = val[i] + maxLoot(i+1, C-wt[i], val, wt, dp);
            int skip = maxLoot(i+1, C, val, wt, dp);
            return dp[i][C] = Math.max(pick,skip);
        } else {
            int skip = maxLoot(i + 1, C, val, wt, dp);
            return dp[i][C] = skip;
        }
    }

    //Method1 Using Simple Recursion
    public int knapsack1(int C, int val[], int wt[]) {
        return maxLoot(0, C, val, wt);
    }

    private int maxLoot(int i, int C, int[] val, int[] wt) {
        if(i == val.length) return 0;
        if(C - wt[i] >= 0){
            int pick = val[i] + maxLoot(i+1, C-wt[i], val, wt);
            int skip = maxLoot(i+1, C, val, wt);
            return Math.max(pick,skip);
        } else {
            int skip = maxLoot(i + 1, C, val, wt);
            return skip;
        }
    }

}
