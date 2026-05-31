package graphs.dsu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class KruskalAlgorithm {
    public int spanningTree(int n, int[][] edges) {
        //Sort edges wrt edge weight
        Arrays.sort(edges, (a,b) -> a[2]-b[2]);
        DSU dsu = new DSU(n);
        int mstSum = 0;
        for(int[] edge: edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            if (dsu.findGroupLeader(u) != dsu.findGroupLeader(v)){
                dsu.union(u, v);
                mstSum += weight;
            }
        }
        return mstSum;
    }

    //Using Prim Algorithm
    public int spanningTree1(int n, int[][] edges) {
        ArrayList<ArrayList<int[]>> adjList = new ArrayList<>();
        for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
        for(int[] edge : edges){
            int startNode = edge[0];
            int endNode = edge[1];
            int weight = edge[2];

            adjList.get(startNode).add(new int[]{endNode, weight});
            adjList.get(endNode).add(new int[]{startNode, weight});
        }

        boolean visited[] = new boolean[n];

        //PQ<Node, Parent, Weight>
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a,b) -> a[2] - b[2]);
        minHeap.add(new int[]{0,-1,0});

        int sum=0;
        while(minHeap.size()>0){
            int[] front = minHeap.remove();
            int vertex = front[0];
            int parent = front[1];
            int weight = front[2];
            if(visited[vertex]) continue;
            sum = sum + weight;
            visited[vertex] = true;
            for(int[] pair: adjList.get(vertex)){
                int newVertex = pair[0], newWeight = pair[1];
                if(!visited[newVertex]) minHeap.add(new int[]{newVertex, vertex, newWeight});
            }
        }

        return sum;
    }
}
