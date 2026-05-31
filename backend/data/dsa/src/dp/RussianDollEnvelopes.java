package dp;

import java.util.ArrayList;
import java.util.Arrays;

public class RussianDollEnvelopes {
    public int maxEnvelopes(int[][] arr) {
        //Sort on increasing length, if length is same
        //then sort on decreasing width
        Arrays.sort(arr,(a,b) -> (a[0]!=b[0]) ? a[0]-b[0] : b[1]-a[1]);

        //Longest Increasing Subsequence(Binary Search) on width
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(arr[0][1]);
        for(int i=1;i<arr.length;i++){
            if(arr[i][1]> ans.get(ans.size()-1)) ans.add(arr[i][1]);
            else{ //lowerbound is the least element which is >= arr[i]
                int idx = lowerBound(ans, arr[i][1]);
                ans.set(idx,arr[i][1]);
            }
        }
        return ans.size();
    }

    private static int lowerBound(ArrayList<Integer> ans, int element) {
        int lo = 0, hi = ans.size()-1, idx = 0;
        while(lo<=hi){
            int mid = (lo+hi)/2;
            if(ans.get(mid) >= element) { //possible lower bound
                idx = mid;
                hi = mid -1; //go left
            } else lo = mid+1; // go right
        }
        return idx;
    }

}
