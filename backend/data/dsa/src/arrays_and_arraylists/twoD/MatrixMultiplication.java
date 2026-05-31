package arrays_and_arraylists.twoD;

import java.util.ArrayList;

public class MatrixMultiplication {
    public static void main(String[] args) {

    }

    public ArrayList<ArrayList<Integer>> multiply(int[][] mat1, int[][] mat2) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int n = mat1.length;

        for(int i=0;i<n;i++){
            ans.add(new ArrayList<>());
            for(int j=0;j<n;j++){
                int sum = 0;
                for(int k=0; k<n; k++){
                    sum += mat1[i][k] * mat2[k][j];
                }
                ans.get(i).add(sum);
            }
        }

        return ans;
    }
}
