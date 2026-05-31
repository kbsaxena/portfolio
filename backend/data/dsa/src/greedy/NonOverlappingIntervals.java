package greedy;

import java.util.Arrays;

public class NonOverlappingIntervals {
    public int minRemoval(int intervals[][]) {
        int n = intervals.length;

        //Sort based on end time
        Arrays.sort(intervals, (a, b) -> a[1]-b[1]);

        int count = 1;
        int endTime = intervals[0][1];
        for(int i=0;i<n;i++){
            int startTime = intervals[i][0];
            if(startTime>=endTime){
                count++;
                endTime = intervals[i][1];
            }
        }

        return n-count;
    }
}
