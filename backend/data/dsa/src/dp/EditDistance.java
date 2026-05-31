package dp;

public class EditDistance {
    //M3 Using DP Tabulation
    public int editDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m][n];
        for(int i=0;i<=m-1;i++){
            for(int j=0;j<=n-1;j++){
                //this comes from andhadhoon tabulation (i-1>=0 && j-1>=0)
                //but base condition after : in ternary operation should be handled
                //if(i<0) return j+1; and if(j<0) return i+1;
                //this (i<0) -> j+1 becomes i-1<0 -> j (subtract -1 from both sides)
                if(s1.charAt(i) == s2.charAt(j)) dp[i][j] = (i-1>=0 && j-1>=0) ? dp[i-1][j-1] : ((i-1<0) ? j : i);
                else{
                    int replace = (i-1>=0 && j-1>=0) ? dp[i-1][j-1] : ((i-1<0) ? j : i);
                    int insert = (j-1>=0) ? dp[i][j-1] : i+1;
                    int remove = (i-1>=0) ? dp[i-1][j] : j+1;
                    dp[i][j] = 1 + Math.min(replace, Math.min(insert, remove)); //Each operation counts as 1
                }
            }
        }
        return dp[m-1][n-1];
    }

    //M2 Using DP Memoization
    public int editDistance2(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m][n];
        for(int i=0;i<=m-1;i++){
            for(int j=0;j<=n-1;j++){
                dp[i][j] = -1;
            }
        }
        return operations(m-1, n-1, new StringBuilder(s1), new StringBuilder(s2), dp);
    }
    //i -> m-1 to 0 , j -> n-1 to 0
    private int operations(int i, int j, StringBuilder s1, StringBuilder s2, int[][] dp) {
        if(i<0) return j+1;
        if(j<0) return i+1;
        if(dp[i][j] != -1) return dp[i][j];
        if(s1.charAt(i) == s2.charAt(j)) return dp[i][j] = operations(i-1, j-1, s1, s2, dp);
        else{
            int replace = operations(i-1, j-1, s1, s2, dp);
            int insert = operations(i, j-1, s1, s2, dp);
            int remove = operations(i-1, j, s1, s2, dp);
            return dp[i][j] = 1 + Math.min(replace, Math.min(insert, remove)); //Each operation counts as 1
        }
    }

    //M1 Using Recursion
    public int editDistance1(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        return operations(m-1, n-1, new StringBuilder(s1), new StringBuilder(s2));
    }

    private int operations(int i, int j, StringBuilder s1, StringBuilder s2) {
        if(i<0) return j+1;
        if(j<0) return i+1;
        if(s1.charAt(i) == s2.charAt(j)) return operations(i-1, j-1, s1, s2);
        else{
            int replace = operations(i-1, j-1, s1, s2);
            int insert = operations(i, j-1, s1, s2);
            int remove = operations(i-1, j, s1, s2);
            return 1 + Math.min(replace, Math.min(insert, remove)); //Each operation counts as 1
        }
    }
}
