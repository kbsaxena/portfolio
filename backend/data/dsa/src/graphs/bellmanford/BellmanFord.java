package graphs.bellmanford;

import java.util.Arrays;

public class BellmanFord {
    public int[] bellmanFord(int n, int[][] edges, int src) {
        int largestValue = 100000000;
        int[] dist = new int[n];
        Arrays.fill(dist, largestValue);
        dist[src] = 0;

        //n-1 times relax
        for(int i =1;i<=n-1;i++){
            for(int[] edge: edges){
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];

                if(dist[u]!=largestValue && dist[u]+weight<dist[v])
                    dist[v] = dist[u]+weight;
            }
        }

        //Running once again to check for -ve cycle
        for(int[] edge: edges){
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            if(dist[u]!=largestValue && dist[u]+weight<dist[v]) {//pakda gaya
                return new int[]{-1};
            }

        }
        return dist;
    }
}
