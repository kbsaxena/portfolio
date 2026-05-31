package dp;

import java.util.Arrays;

public class FriendsPairingProblem {
    //Method 3 DP Tabulation
    public long countFriendsPairings(int n) {
        if (n <= 2) return n;
        long[] dp = new long[n+1];
        dp[1] = 1;
        dp[2] = 2;
        for(int i=3;i<n;i++){
            dp[i] = dp[i-1] + (i-1) * dp[i-2];
        }
        return dp[n];
    }

    //Method 2 DP Memoization
    public long countFriendsPairings2(int n) {
        long[] dp = new long[n+1];
        Arrays.fill(dp, -1L);
        return countPairings(n, dp);
    }

    private long countPairings(int n, long[] dp) {
        if(n<=2) return n;
        if(dp[n] != -1L) return dp[n];
        return dp[n] = countPairings(n-1, dp) + (n-1) * countPairings(n-2, dp);
    }

    //Method 1 Simple Recursion
    public long countFriendsPairings1(int n) {
        return countPairings(n);
    }

    private long countPairings(int n) {
        if(n<=2) return n;
        return countPairings(n-1) + (n-1) * countPairings(n-2);
    }
}
