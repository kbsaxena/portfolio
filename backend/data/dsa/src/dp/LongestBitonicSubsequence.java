package dp;

import java.util.Arrays;

public class LongestBitonicSubsequence {
    public static int longestBitonicSequence(int n, int[] arr) {
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, 1);
        Arrays.fill(dp2, 1);
        for(int i=1;i<n;i++){
            for(int j=i-1;j>=0;j--){ //Travels i-1 to 0 for every i
                if(arr[i] > arr[j]){
                    dp1[i] = Math.max(dp1[i], 1 + dp1[j]);
                }
            }
        }
        for(int i=n-2;i>=0;i--){
            for(int j=i+1;j<n;j++){ //Travels i+1 to n-1 for every i
                if(arr[i] > arr[j]){
                    dp2[i] = Math.max(dp2[i], 1 + dp2[j]);
                }
            }
        }

        int len = 0;
        for(int i=0;i<n;i++){
            if(dp1[i] != 1 && dp2[i] != 1)
                len = Math.max(len, dp1[i] + dp2[i] -1);
        }
        return len;
    }
}
