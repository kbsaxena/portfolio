package greedy;

import java.util.Arrays;

public class NMeetingsInARoom {
    public int maxMeetings(int start[], int end[]) {
        int n = start.length;
        int[][] arr = new int[n][2];
        for(int i=0;i<n;i++){
            arr[i][0] = start[i];
            arr[i][1] = end[i];
        }

        //Sort based on end time
        Arrays.sort(arr, (a,b) -> a[1]-b[1]);

        int count = 1;
        int endTime = arr[0][1];
        for(int i=0;i<n;i++){
            int startTime = arr[i][0];
            if(startTime>endTime){
                count++;
                endTime = arr[i][1];
            }
        }

        return count;
    }
}
