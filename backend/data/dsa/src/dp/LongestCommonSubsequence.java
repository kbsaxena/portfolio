package dp;

import java.util.HashSet;

public class LongestCommonSubsequence {
    //Print LCS
    public static void main(String[] args) {
        String s1 = "talent";
        String s2 = "lantern";
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
            }
        }
        //Once dp is created now try printing using dp table
        HashSet<String> set = new HashSet<>();
        printAllLcs(m,n,s1,s2,dp,"", set);
        System.out.println(set);
    }

    //print all lcs recursively
    static void printAllLcs(int i, int j, String s1, String s2, int[][] dp, String lcs, HashSet<String> set){
        if(i==0 || j==0){
            StringBuilder sb = new StringBuilder(lcs);
            sb.reverse();
            set.add(sb.toString());
            return;
        }
        if(s1.charAt(i-1) == s2.charAt(j-1)){
            printAllLcs(i-1,j-1,s1,s2,dp,lcs + s1.charAt(i-1), set);
        } else{
            if(dp[i-1][j]>=dp[i][j-1]) printAllLcs(i-1,j,s1,s2,dp,lcs, set);
            if(dp[i-1][j]<=dp[i][j-1]) printAllLcs(i,j-1,s1,s2,dp,lcs, set);
        }
    }

    //print any one lcs
    static void print(int i, int j, String s1, String s2, int[][] dp){
        StringBuilder lcs = new StringBuilder();
        while(i>0 && j>0){
            if(s1.charAt(i-1) == s2.charAt(j-1)){
                lcs.append(s1.charAt(i-1));
                i--;
                j--;
            } else{
                if(dp[i-1][j]>=dp[i][j-1]) i--;
                else j--;
            }
        }
        lcs.reverse();
        System.out.println(lcs.toString());
    }

    //M3 Dp tabulation (Thinking)
    static int lcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
            }
        }

        return dp[m][n];
    }

    //M3 Andhadhoon Dp tabulation
    static int lcs3(String s1, String s2) {
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

    //M2 Dp Memoization
    static int lcs2(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m][n];
        for(int i=0;i<=m-1;i++){
            for(int j=0;j<=n-1;j++){
                dp[i][j] = -1;
            }
        }
        //we can go from 0 also, but we are going from back as it will be easy for tabulation
        return commonSub(m-1, n-1, new StringBuilder(s1), new StringBuilder(s2), dp);
    }

    private static int commonSub(int i, int j, StringBuilder s1, StringBuilder s2, int[][] dp) {
        if(i<0 || j<0) return 0;
        if(dp[i][j] != -1) return dp[i][j];
        if(s1.charAt(i) == s2.charAt(j)) return dp[i][j] = 1+commonSub(i-1, j-1, s1, s2, dp);
        else return dp[i][j] = Math.max(commonSub(i-1, j, s1, s2, dp), commonSub(i, j-1, s1, s2, dp));
    }

    //M1 Recursion
    static int lcs1(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        //we can go from 0 also, but we are going from back as it will be easy for tabulation
        return commonSub(m-1, n-1, new StringBuilder(s1), new StringBuilder(s2));
    }
    //i -> m-1 to 0, j -> n-1 to 0
    private static int commonSub(int i, int j, StringBuilder s1, StringBuilder s2) {
        if(i<0 || j<0) return 0;
        if(s1.charAt(i) == s2.charAt(j)) return 1+commonSub(i-1, j-1, s1, s2);
        else return Math.max(commonSub(i-1, j, s1, s2), commonSub(i, j-1, s1, s2));
    }
}
