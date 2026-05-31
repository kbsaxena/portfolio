package graphs.dsu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NumberOfProvince {
    //Using DSU
    //Overall TC: O(V^2) , Overall SC: O(V) here V=n
    static int numProvinces(ArrayList<ArrayList<Integer>> adj, int n) {
        DSU dsu = new DSU(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(adj.get(i).get(j) == 1){ //i to j edge exist
                    if(dsu.findGroupLeader(i) != dsu.findGroupLeader(j))
                        dsu.union(i,j);
                }
            }
        }
        int count = 0;
        for(int i=0;i<n;i++){
            if(dsu.parent[i] == i) count++;
        }
        return count;
    }

    // USing Queue and Visited array
    // Overall TC: O(V^2) , Overall SC: O(V) here V=n
    static int numProvinces1(ArrayList<ArrayList<Integer>> adj, int n) {
        // 0 to n-1 nodes
        int count = 0;
        boolean[] visited = new boolean[n];
        for(int i=0;i<n;i++){
            if(!visited[i]){
                bfs(i, visited, adj);
                count++;
            }
        }
        return count;
    }

    private static void bfs(int i, boolean[] visited, ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        Queue<Integer> q = new LinkedList<>();
        q.add(i);
        visited[i] = true;

        while(q.size()>0){
            int front = q.remove();
            for(int j=0;j<n;j++){
                if(adj.get(front).get(j) == 1 && !visited[j]){
                    q.add(j);
                    visited[j] = true;
                }
            }
        }

    }
}
