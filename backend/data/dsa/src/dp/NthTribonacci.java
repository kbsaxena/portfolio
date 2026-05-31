package dp;

import java.util.Arrays;

public class NthTribonacci {
    //Method 2 - Using DP
    public int tribonacci(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        return trib(n, dp);
    }

    public int trib(int n, int[] dp) {
        if(n == 0 || n == 1) return n;
        if(n == 2) return 1;
        if(dp[n] != -1) return dp[n];
        return dp[n] = trib(n-1, dp) + trib(n-2, dp) + trib(n-3, dp);
    }

    //Method 1 TC = O(2^n)
    public int tribonacci1(int n) {
        if(n == 0 || n == 1) return n;
        if(n == 2) return 1;
        return tribonacci1(n-1) + tribonacci1(n-2) + tribonacci1(n-3);
    }
}
