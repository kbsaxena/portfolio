package dp;

public class MinimumPathSum {
    //Method 3 Using DP(Tabulation)
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i == 0 && j == 0) dp[i][j] = grid[i][j];
                else if(i == 0) dp[i][j] = grid[i][j] + dp[i][j-1];
                else if(j == 0) dp[i][j] = grid[i][j] + dp[i-1][j];
                else dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[m-1][n-1];
    }

    //Method 2 Using DP(Memoization)
    public int minPathSum2(int[][] grid) {// m: m to 1, n: n to 1
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dp[i][j] = -1;
            }
        }
        return pathSum(m-1,n-1, grid, dp);
    }

    public int pathSum(int m, int n, int[][] grid, int[][] dp){
        if(m == 0 && n == 0) return grid[0][0];
        if(m < 0 || n < 0) return Integer.MAX_VALUE;
        if(dp[m][n] != -1) return dp[m][n];
        return dp[m][n] = grid[m][n] + Math.min(pathSum(m-1,n,grid,dp) , pathSum(m,n-1, grid,dp));
    }

    //Method 1 Simple Recursion
    public int minPathSum1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return pathSum(m-1,n-1, grid);
    }

    public int pathSum(int m, int n, int[][] grid){
        if(m == 0 && n == 0) return grid[0][0];
        if(m < 0 || n < 0) return Integer.MAX_VALUE;
        return grid[m][n] + Math.min(pathSum(m-1,n,grid) , pathSum(m,n-1, grid));
    }
}
