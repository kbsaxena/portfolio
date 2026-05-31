package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DistanceFromNearest1 {
    public class Pair{
        int row;
        int col;
        Pair(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    public ArrayList<ArrayList<Integer>> nearest(int[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        int[][] ans = new int[rows][cols];
        Queue<Pair> q = new LinkedList<>();

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(arr[i][j]==1) q.add(new Pair(i,j));
            }
        }

        while(q.size()>0){
            Pair front = q.remove();
            int row = front.row;
            int col = front.col;

            // left (row, col-1)
            if(col-1>=0 && arr[row][col-1]==0 && ans[row][col-1]==0) {
                ans[row][col-1] = ans[row][col] + 1;
                q.add(new Pair(row, col - 1));
            }

            // right (row, col+1)
            if(col+1<cols && arr[row][col+1]==0 && ans[row][col+1]==0) {
                ans[row][col+1] = ans[row][col]+1;
                q.add(new Pair(row, col + 1));
            }

            // up (row-1, col)
            if(row-1>=0 && arr[row-1][col]==0 && ans[row-1][col]==0) {
                ans[row-1][col] = ans[row][col]+1;
                q.add(new Pair(row - 1, col));
            }

            // down (row+1, col)
            if(row+1<rows && arr[row+1][col]==0 && ans[row+1][col]==0) {
                ans[row+1][col] = ans[row][col]+1;
                q.add(new Pair(row + 1, col));
            }
        }

        ArrayList<ArrayList<Integer>> finalAns = new ArrayList<>();
        for(int i=0;i<rows;i++){
            ArrayList<Integer> inner = new ArrayList<>();
            for(int j=0;j<cols;j++){
                inner.add(ans[i][j]);
            }
            finalAns.add(inner);
        }

        return finalAns;
    }
}
