package graphs.dijkstra;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PathWithMinimumEffort {
    public class Triplet implements Comparable<Triplet>{
        int row;
        int col;
        int effort;

        public Triplet(int row, int col, int effort) {
            this.row = row;
            this.col = col;
            this.effort = effort;
        }
        public int compareTo(Triplet t){
            return this.effort - t.effort; // min heap
            //return Integer.compare(this.effort, t.effort); //this can also be used
        }
    }
    public int minCostPath(int[][] mat) {
        int noOfRows = mat.length;
        int noOfColumns = mat[0].length;

        int[][] dist = new int[noOfRows][noOfColumns];
        for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[0][0] = 0;

        PriorityQueue<Triplet> minHeap = new PriorityQueue<>();
        minHeap.add(new Triplet(0,0,0));

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}}; //left, right, up, down

        while(minHeap.size()>0){
            Triplet front = minHeap.remove();

            int row = front.row;
            int col = front.col;
            int effort = front.effort;

            if(effort > dist[row][col]) continue;

            for(int[] d: directions){
                int nRow = row + d[0];
                int nCol = col + d[1];

                if(nRow >= 0 && nCol >=0 && nRow<noOfRows && nCol<noOfColumns){
                    int diff = Math.abs(mat[row][col] - mat[nRow][nCol]);

                    int newEffort = Math.max(effort, diff);

                    if(dist[nRow][nCol] > newEffort){
                        dist[nRow][nCol] = newEffort;
                        minHeap.add(new Triplet(nRow, nCol, newEffort));
                    }
                }
            }
        }
        return dist[noOfRows-1][noOfColumns-1];
    }
}
