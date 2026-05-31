package graphs.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {
    public class Pair implements Comparable<Pair>{
        int node;
        int distance;

        public Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair p) {
            if(this.distance == p.distance) return this.node - p.node;
            return this.distance - p.distance;
        }
    }
    public int[] dijkstra(int n, int[][] edges, int src) {
        // code here
        ArrayList<ArrayList<Pair>> adjList = new ArrayList<>();
        for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
        for(int[] edge : edges){
            int startNode = edge[0];
            int endNode = edge[1];
            int distance = edge[2];

            adjList.get(startNode).add(new Pair(endNode, distance));
            adjList.get(endNode).add(new Pair(startNode, distance));
        }

        int[] ans = new int[n];
        Arrays.fill(ans, Integer.MAX_VALUE);
        ans[src] = 0;

        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        minHeap.add(new Pair(src,0));

        while(minHeap.size()>0){
            Pair front = minHeap.remove();
            int node = front.node;
            int distanceFromSource = front.distance;
            if(distanceFromSource > ans[node]) continue;
            for(Pair p:adjList.get(node)){
                int endNode = p.node;
                int distance = p.distance;
                int totalDistance = distanceFromSource + distance; //VV IMP
                if(ans[endNode] > totalDistance) {
                    minHeap.add(new Pair(endNode,totalDistance));
                    ans[endNode] = totalDistance;
                }
            }
        }
        return ans;
    }
}
