package dp;

public class ShortestCommonSuperSequence {
    public static int minSuperSeq(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = 1 + dp[i-1][j-1];
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
            }
        }

        int i = m, j = n;
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

        i = 0; j = 0;
        StringBuilder scs = new StringBuilder();
        for(int k=0;k<lcs.length();k++){
            char ch = lcs.charAt(k);
            //Move i in s1 until we reach the character same as in lcs
            while(s1.charAt(i) != ch){
                scs.append(s1.charAt(i));
                i++;
            }
            i++;
            //Move j in s2 until we reach the character same as in lcs
            while(s2.charAt(j) != ch){
                scs.append(s2.charAt(j));
                j++;
            }
            j++;
            scs.append(ch);
        }
        //Remaining Characters of s1
        while(i<s1.length()){
            scs.append(s1.charAt(i));
            i++;
        }
        //Remaining Characters of s2
        while(j<s2.length()){
            scs.append(s2.charAt(j));
            j++;
        }
        return scs.length();
    }
}
