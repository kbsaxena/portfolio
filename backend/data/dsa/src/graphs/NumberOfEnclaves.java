package graphs;

import java.util.LinkedList;
import java.util.Queue;

public class NumberOfEnclaves {
    public class Pair{
        int row;
        int col;
        Pair(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    int numberOfEnclaves(int[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        Queue<Pair> q = new LinkedList<>();

        //Marking Boundaries
        //Marking First row and last row
        for(int j=0;j<cols;j++){
            if(arr[0][j] == 1){
                arr[0][j] = -1;
                q.add(new Pair(0,j));
            }
            if(arr[rows-1][j] == 1){
                arr[rows-1][j] = -1;
                q.add(new Pair(rows-1,j));
            }

        }
        //Marking first column and last column
        for(int i=0;i<rows;i++){
            if(arr[i][0] == 1){
                arr[i][0] = -1;
                q.add(new Pair(i,0));
            }
            if(arr[i][cols-1] == 1){
                arr[i][cols-1] = -1;
                q.add(new Pair(i,cols-1));
            }

        }

        while(q.size()>0){
            Pair front = q.remove();
            int row = front.row;
            int col = front.col;

            // left (row, col-1)
            if(col-1>=0 && arr[row][col-1]==1) {
                arr[row][col-1] = -1;
                q.add(new Pair(row, col - 1));
            }

            // right (row, col+1)
            if(col+1<cols && arr[row][col+1]==1) {
                arr[row][col+1] = -1;
                q.add(new Pair(row, col + 1));
            }

            // up (row-1, col)
            if(row-1>=0 && arr[row-1][col]==1) {
                arr[row-1][col] = -1;
                q.add(new Pair(row - 1, col));
            }

            // down (row+1, col)
            if(row+1<rows && arr[row+1][col]==1) {
                arr[row+1][col] = -1;
                q.add(new Pair(row + 1, col));
            }

        }

        int count = 0;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(arr[i][j]==1) count++;
            }
        }

        return count;
    }
}
