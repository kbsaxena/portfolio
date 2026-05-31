package queues;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FirstNegativeInteger {
    static List<Integer> firstNegInt(int arr[], int k) {
        // for n size array how many k sized subarrays will exist?
        // n-(k-1) or n-k+1
        Queue<Integer> q = new LinkedList<>();
        int n = arr.length;
        int[] ans = new int[n-k+1];

        // store indices of negative numbers
        for(int i=0;i<n;i++){
            if(arr[i]<0){
                q.add(i);
            }
        }

        for(int i=0;i<=n-k;i++){
            // remove indices outside window
            while(q.size() != 0 && q.peek()<i){
                q.remove();
            }

            // check if index lies in window
            if(q.size() != 0 && q.peek() <= i+k-1){
                ans[i] = arr[q.peek()];
            } else ans[i] = 0;

        }
        // convert array to list
        List<Integer> result = new ArrayList<>();
        for (int x : ans) {
            result.add(x);
        }

        return result;
    }
}
