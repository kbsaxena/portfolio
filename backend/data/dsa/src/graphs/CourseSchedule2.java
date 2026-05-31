package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CourseSchedule2 {
    public ArrayList<Integer> findOrder(int n, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        int[] in_degree = new int[n];
        for(int i = 0; i < prerequisites.length; i++) {
            int u = prerequisites[i][0]; // course
            int v = prerequisites[i][1]; // prerequisite
            adjList.get(v).add(u); //prerequisite should be completed first

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

        // if cycle detected return empty array
        if(ans.size() != n) return new ArrayList<>();

        return ans;
    }
}
