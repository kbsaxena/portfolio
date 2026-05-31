package heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class KClosestPointsToOrigin {
    public class Triplet implements Comparable<Triplet>{
        int dist;
        int x;
        int y;
        Triplet(int dist, int x, int y){
            this.dist = dist;
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Triplet t) {
            //return this.dist-t.dist;
            return Integer.compare(this.dist, t.dist);
        }
    }

    public ArrayList<ArrayList<Integer>> kClosest(int[][] points, int k) {
        // code here
        PriorityQueue<Triplet> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int[] point : points) {
            int x = point[0];
            int y = point[1];
            int dist = x * x + y * y;
            maxHeap.add(new Triplet(dist, x, y));

            if (maxHeap.size() > k) maxHeap.remove();
        }

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        while(maxHeap.size()>0){
            Triplet top = maxHeap.remove();
            ArrayList<Integer> a = new ArrayList<>();
            a.add(top.x);
            a.add(top.y);
            ans.add(a);
        }
        return ans;
    }
}
