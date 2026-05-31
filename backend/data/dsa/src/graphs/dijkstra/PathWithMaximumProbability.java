package graphs.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathWithMaximumProbability {
    public class Pair implements Comparable<Pair>{
        int node;
        double probability;

        public Pair(int node, double probability) {
            this.node = node;
            this.probability = probability;
        }

        @Override
        public int compareTo(Pair p) {
            if(this.probability == p.probability) return this.node - p.node;
            return Double.compare(this.probability,p.probability);
        }
    }
    public double maxProbability(int n, int[][] edges, double[] succProb, int src, int dest) {
        ArrayList<ArrayList<Pair>> adjList = new ArrayList<>();
        for(int i=0;i<n;i++) adjList.add(new ArrayList<>());
        for(int i=0;i<edges.length;i++){
            int startNode = edges[i][0];
            int endNode = edges[i][1];
            double prob = succProb[i];

            adjList.get(startNode).add(new Pair(endNode, prob));
            adjList.get(endNode).add(new Pair(startNode, prob));
        }

        double[] ans = new double[n]; //0.0 is the minimum probability
        ans[src] = 1; //Assigning max probability

        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(new Pair(src,1));

        while(maxHeap.size()>0){
            Pair front = maxHeap.remove();
            int node = front.node;
            double probabilityFromSource = front.probability;
            if(probabilityFromSource < ans[node]) continue;
            for(Pair p:adjList.get(node)){
                int endNode = p.node;
                double probability = p.probability;
                double totalProbability = probabilityFromSource * probability; //VV IMP
                if(ans[endNode] < totalProbability) {
                    maxHeap.add(new Pair(endNode, totalProbability));
                    ans[endNode] = totalProbability;
                }
            }
        }
        return ans[dest];
    }
}
