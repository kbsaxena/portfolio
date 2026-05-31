package dp;

public class MinimumDeletionToMakeStringPalindrome {
    public int minDeletions(String s) {
        return s.length() - longestPalinSubseq(s);
    }

    public int longestPalinSubseq(String s) {
        String s2 = new StringBuilder(s).reverse().toString();
        return lcs(s, s2);
    }

    //M3 Andhadhoon Dp tabulation
    static int lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m][n];
        for(int i=0;i<=m-1;i++){
            for(int j=0;j<=n-1;j++){
                if(s1.charAt(i) == s2.charAt(j)) dp[i][j] = 1 + ((i-1>=0 && j-1>=0)?dp[i-1][j-1]:0);
                else dp[i][j] = Math.max((i-1>=0)?dp[i-1][j]:0,(j-1>=0)?dp[i][j-1]:0);
            }
        }

        return dp[m-1][n-1];
    }
}
