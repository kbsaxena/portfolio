package graphs;

import java.util.LinkedList;
import java.util.Queue;

public class RottenOranges {
    public class Triplet{
        int row;
        int col;
        int time;
        Triplet(int row, int col, int time){
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }
    public int orangesRot(int[][] arr) {
        // code here
        int rows = arr.length;
        int cols = arr[0].length;
        Queue<Triplet> q = new LinkedList<>();

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(arr[i][j]==2) //It is rotten at time = 0
                    q.add(new Triplet(i,j,0));
            }
        }

        int maxTime = 0;
        while(q.size()>0){
            Triplet front = q.remove();
            int row = front.row;
            int col = front.col;
            int time = front.time;

            maxTime = Math.max(maxTime, time);

            // rotting Left (row, col-1)
            if(col-1>=0 && arr[row][col-1]==1) {
                arr[row][col-1] = 2;
                q.add(new Triplet(row, col - 1, time + 1));
            }

            // rotting right (row, col+1)
            if(col+1<cols && arr[row][col+1]==1) {
                arr[row][col+1] = 2;
                q.add(new Triplet(row, col + 1, time + 1));
            }

            // rotting up (row-1, col)
            if(row-1>=0 && arr[row-1][col]==1) {
                arr[row-1][col] = 2;
                q.add(new Triplet(row - 1, col, time + 1));
            }

            //rotting down (row+1, col)
            if(row+1<rows && arr[row+1][col]==1) {
                arr[row+1][col] = 2;
                q.add(new Triplet(row + 1, col, time + 1));
            }

        }

        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(arr[i][j]==1) return -1;
            }
        }

        return maxTime;
    }
}
