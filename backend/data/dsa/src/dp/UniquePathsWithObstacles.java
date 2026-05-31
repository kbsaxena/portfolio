package dp;

public class UniquePathsWithObstacles {
    //Method 4 Tabulation Space optimized
    public int uniquePathsWithObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] dp = new int[n];

        // Start
        dp[0] = (grid[0][0] == 1) ? 0 : 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dp[j] = 0; // obstacle
                } else if (j > 0) {
                    dp[j] = dp[j] + dp[j-1];
                }
            }
        }

        return dp[n-1];
    }
    //Method 3 Using Tabulation
    public int uniquePathsWithObstacles3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // Start cell
        if (grid[0][0] == 1) return 0;
        dp[0][0] = 1;

        // First column
        for (int i = 1; i < m; i++) {
            if (grid[i][0] == 1) dp[i][0] = 0;
            else dp[i][0] = dp[i-1][0];
        }

        // First row
        for (int j = 1; j < n; j++) {
            if (grid[0][j] == 1) dp[0][j] = 0;
            else dp[0][j] = dp[0][j-1];
        }

        // Fill rest
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }

        return dp[m-1][n-1];
    }

    //Method 2 DP Memoization
    public int uniquePathsWithObstacles2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m+1][n+1];
        for(int i=0;i<=m;i++){
            for(int j=0;j<=n;j++){
                dp[i][j] = -1;
            }
        }
        return paths(m-1, n-1, grid, dp);
    }

    public int paths(int i, int j, int[][] grid, int[][] dp){
        if(i < 0 || j < 0) return 0;
        if(grid[i][j] == 1) return 0;       // obstacle
        if(i == 0 && j == 0) return 1;      // base case
        if(dp[i][j] != -1) return dp[i][j];
        return dp[i][j] = paths(i-1, j, grid, dp) + paths(i, j-1, grid, dp);
    }

    //Method 1 - Simple Recursion
    public int uniquePathsWithObstacles1(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        return paths(m-1, n-1, grid);
    }

    public int paths(int i, int j, int[][] grid){
        if(i < 0 || j < 0) return 0;
        if(grid[i][j] == 1) return 0;       // obstacle
        if(i == 0 && j == 0) return 1;      // base case
        return paths(i-1, j, grid) + paths(i, j-1, grid);
    }
}
