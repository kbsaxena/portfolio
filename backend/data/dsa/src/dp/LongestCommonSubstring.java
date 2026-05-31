package dp;

public class LongestCommonSubstring {
    public int longCommSubstr(String s1, String s2) {
        int m = s1.length(), n = s2.length(), maxLength = 0;
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = 0;
                maxLength = Math.max(maxLength, dp[i][j]);
            }
        }

        return maxLength;
    }
}
