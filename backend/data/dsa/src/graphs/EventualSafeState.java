package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EventualSafeState {
    public ArrayList<Integer> safeNodes(int n, int[][] edges) {
        // Code here
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        int[] in_degree = new int[n];
        for(int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adjList.get(v).add(u); //reverse the edges

            in_degree[u]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(in_degree[i]==0) q.add(i);
        }
        ArrayList<Integer> ans = new ArrayList<>();
        while(q.size()>0){
            int vertex = q.remove();
            ans.add(vertex);
            for(int ele: adjList.get(vertex)){
                in_degree[ele]--;
                if(in_degree[ele] == 0) q.add(ele);
            }
        }

        return ans;
    }
}
