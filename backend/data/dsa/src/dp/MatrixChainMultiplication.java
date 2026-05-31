package dp;

public class MatrixChainMultiplication {
    //Method 2 DP Memoization
    static int matrixMultiplication(int arr[]) {
        int n = arr.length;
        int[][] dp = new int[n-1][n-1];
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-1;j++){
                dp[i][j] = -1;
            }
        }
        return calculateMinCost(0, n-2, arr, dp);
    }

    // i -> 0 to n-2   j-> n-2 to 0
    private static int calculateMinCost(int i, int j, int[] arr, int[][] dp) {
        if(i==j) return 0;
        if(dp[i][j] != -1) return dp[i][j];
        int minCost = Integer.MAX_VALUE;
        for(int k=i;k<j;k++){//partition at k
            int cost = arr[i] * arr[k+1] * arr[j+1];
            int multiplicationCost = calculateMinCost(i,k, arr, dp) + calculateMinCost(k+1,j, arr, dp) + cost;
            minCost = Math.min(minCost,multiplicationCost);
        }
        return dp[i][j] = minCost;
    }

    //Method 1 Simple Recursion
    static int matrixMultiplication1(int arr[]) {
        int n = arr.length;
        return calculateMinCost(0, n-2, arr);
    }

    private static int calculateMinCost(int i, int j, int[] arr) {
        if(i==j) return 0;
        int minCost = Integer.MAX_VALUE;
        for(int k=i;k<j;k++){//partition at k
            int cost = arr[i] * arr[k+1] * arr[j+1];
            int multiplicationCost = calculateMinCost(i,k, arr) + calculateMinCost(k+1,j, arr) + cost;
            minCost = Math.min(minCost,multiplicationCost);
        }
        return minCost;
    }
}
