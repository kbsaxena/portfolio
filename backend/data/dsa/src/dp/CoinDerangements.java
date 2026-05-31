package dp;

import java.util.Arrays;

public class CoinDerangements {
    // M3 DP Tabulation
    public int derangeCount(int n) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 0;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = (i - 1) * dp[i - 1] + (n-1) * dp[i - 2];
        }
        return dp[n];
    }

    //M2 DP Memo
    public int derangeCount2(int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);
        return derange(n, dp);
    }

    private int derange(int n, int[] dp) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        if (n == 2) return 1;
        if(dp[n] != -1) return dp[n];
        return dp[n] = (n-1) * derange(n-2, dp) + (n-1) * derange(n-1, dp);
    }

    //M1 Simple Recursion
    public int derangeCount1(int n) {
        return derange(n);
    }

    private int derange(int n) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        if (n == 2) return 1;
        return (n-1) * derange(n-2) + (n-1) * derange(n-1);
    }

}
