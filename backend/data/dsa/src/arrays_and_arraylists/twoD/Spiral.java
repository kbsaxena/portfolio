package arrays_and_arraylists.twoD;

import java.util.ArrayList;

public class Spiral {
    public static void main(String[] args) {

    }

    public ArrayList<Integer> spirallyTraverse(int[][] mat) {
        ArrayList<Integer> res = new ArrayList<>();
        int m = mat.length; //Rows
        int n = mat[0].length; //Columns

        int fr = 0, lr = m-1, fc = 0, lc = n-1;
        while(res.size() < m*n){
            for(int j=fc; j<=lc; j++){
                res.add(mat[fr][j]);
            }
            fr++;
            if(res.size() == m*n) break;
            for(int i=fr; i<=lr; i++){
                res.add(mat[i][lc]);
            }
            lc--;
            if(res.size() == m*n) break;
            for(int j=lc; j>=fc;j--){
                res.add(mat[lr][j]);
            }
            lr--;
            if(res.size() == m*n) break;
            for(int i=lr; i>=fr;i--){
                res.add(mat[i][fc]);
            }
            fc++;

        }

        return res;
    }
}
