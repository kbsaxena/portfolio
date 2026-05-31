package graphs.floydWarshall;

public class FloydWarshallAlgorithm {
    public void floydWarshall(int[][] dist) {
        //Concept: u-> min distance , u->x->v where x is 0 to n-1
        int n = dist.length;
        for(int k=0;k<n;k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != 100000000 && dist[k][j] != 100000000)
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
