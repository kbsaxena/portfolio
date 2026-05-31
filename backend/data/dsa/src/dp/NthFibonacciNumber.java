package dp;

import java.util.Arrays;

public class NthFibonacciNumber {
    //Method 4 - Using Tabulation (Space Optimized)
    public int nthFibonacci4(int n) {
        if(n <= 1) return n;
        int[] dp = new int[3];
        dp[0] = 0;
        dp[1] = 1;
        for(int i=2;i<=n;i++){
            dp[2] = dp[1] + dp[0];
            dp[0] = dp[1];
            dp[1] = dp[2];
        }
        return dp[2];
    }

    //Method 3 - Using Tabulation
    public int nthFibonacci3(int n) {
        int[] dp = new int[n + 1]; //0 to n index
        dp[0] = 0;
        if(n>=1) dp[1] = 1;
        for(int i=2;i<=n;i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    //Method 2 - Using DP
    public int nthFibonacci2(int n) {
        int[] dp = new int[n+1]; //0 to n index
        Arrays.fill(dp, -1); //-1 mean unvisited
        return fibo(n, dp);
    }

    private int fibo(int n, int[] dp){
        if(n==0 || n==1) return n;
        if(dp[n] != -1) return dp[n];
        return dp[n] = fibo(n-1, dp) + fibo(n-2, dp);
    }


    //Method 1 TC = O(2^n)
    public int nthFibonacci1(int n) {
        // code here
        if(n==0 || n==1) return n;
        return nthFibonacci1(n-1) + nthFibonacci1(n-2);
    }
}
