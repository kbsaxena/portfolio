package graphs.prim;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MinimumCostToConnectAllHouses {
    public int minCost(int[][] houses) {
        int n = houses.length;
        boolean[] visited = new boolean[n];

        //PQ<pt, distance>
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        minHeap.add(new int[]{0,0});

        int sum=0;
        while(minHeap.size()>0){
            int[] front = minHeap.remove();
            int vertex = front[0];
            int distance = front[1];
            if(visited[vertex]) continue;
            sum = sum + distance;
            visited[vertex] = true;
            for(int i=0;i<n;i++){
                if(i==vertex) continue;
                int newDistance = Math.abs(houses[vertex][0]-houses[i][0]) + Math.abs(houses[vertex][1]-houses[i][1]);
                if(!visited[i]) minHeap.add(new int[]{i, newDistance});
            }
        }

        return sum;
    }
}
