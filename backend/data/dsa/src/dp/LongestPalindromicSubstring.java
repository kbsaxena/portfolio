package dp;

public class LongestPalindromicSubstring {
    public String getLongestPal(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLength = 0;
        String ans = "";
        for(int k=0;k<n;k++){
            int i=0, j=k;
            while(j<n){
                if(i==j) dp[i][j] = true; //Single characters are palindrome
                else if(j == i+1){ //Comparing 2 length string
                    if(s.charAt(i) == s.charAt(j)) dp[i][j] = true;
                }
                else { //Comparing 3 or more length string
                    if(s.charAt(i) == s.charAt(j) && dp[i+1][j-1] == true) dp[i][j] = true;
                }

                if(dp[i][j] == true){ //Found Palindrome
                    int lengthOfPalindrome = j-i+1;
                    if(lengthOfPalindrome>maxLength){
                        maxLength = lengthOfPalindrome;
                        ans = s.substring(i, j+1);
                    }
                }
                i++;
                j++;
            }
        }
        return ans;
    }
}
