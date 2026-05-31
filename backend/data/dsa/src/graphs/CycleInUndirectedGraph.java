package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CycleInUndirectedGraph {
    public class Pair{
        int vertex;
        int parent;
        Pair(int vertex, int parent){
            this.vertex = vertex;
            this.parent = parent;
        }
    }
    public boolean isCycle(int n, int[][] edges) {
        // Create Adjacency List
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        for(int i=0;i<n;i++){
            adjList.add(new ArrayList<>());
        }

        for(int i = 0;i<edges.length;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }

        boolean[] visited = new boolean[n];

        for(int i=0;i<n;i++){
            if(!visited[i]){
                if(bfs(i, visited, adjList)) return true;
            }
        }

        return false;

    }

    private boolean bfs(int src, boolean[] visited, ArrayList<ArrayList<Integer>> adjList) {
        visited[src] = true;

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(src, -1));
        while(q.size()>0){
            Pair front = q.remove();
            int vertex = front.vertex;
            int parent = front.parent;

            for(int ele: adjList.get(vertex)){
                if(!visited[ele]){
                    q.add(new Pair(ele, vertex));
                    visited[ele] = true;
                } else if(ele != parent) return true;
            }
        }
        return false;
    }
}
