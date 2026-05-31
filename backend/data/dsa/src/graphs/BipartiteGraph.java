package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BipartiteGraph {
    public boolean isBipartite(int n, int[][] edges) {
        // Create Adjacency List
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for(int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }

        int[] visited = new int[n]; //1 is green, 2 is red, 0 is uncolored

        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                if (!bfs(i, visited, adjList)) return false;
            }
        }

        return true;
    }

    private boolean bfs(int src, int[] visited, ArrayList<ArrayList<Integer>> adjList) {
        visited[src] = 1;

        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        while(q.size()>0){
            int vertex = q.remove();
            int color = visited[vertex];

            for(int ele: adjList.get(vertex)){
                if(visited[ele]==0){
                    q.add(ele);
                    visited[ele] = 3-color;
                } else if(visited[ele] == color) return false;
            }
        }
        return true;
    }
}
