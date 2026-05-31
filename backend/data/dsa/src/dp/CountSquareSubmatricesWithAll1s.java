package dp;

public class CountSquareSubmatricesWithAll1s {
    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int count = 0;

        for(int i=0;i<m;i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if(i == 0 || j == 0) count++;
                    else {
                        int top = matrix[i - 1][j];
                        int left = matrix[i][j - 1];
                        int diagonal_left_top = matrix[i - 1][j - 1];
                        if(top >= 1 && left >= 1 && diagonal_left_top >= 1) {
                            matrix[i][j] += Math.min(top, Math.min(left, diagonal_left_top));
                        }
                        count += matrix[i][j];
                    }
                }
            }
        }
        return count;
    }
}
