package binarySearch.binarysearchonanswer;

public class SmallestDivisorGivenAThreshold {
    public int smallestDivisor(int[] arr, int k) {
        int max = arr[0];
        for(int ele: arr){
            max = Math.max(max, ele);
        }
        int lo = 1, hi = max, ans = max;
        while(lo <= hi){ // TC = O(n × log(max))
            int mid = lo + (hi-lo)/2;
            if(canDivide(mid,arr,k)){
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        return ans;
    }

    private boolean canDivide(int divisor, int[] arr, int threshold) {
        int sum = 0;
        for(int ele: arr){
            sum += ele/divisor;
            if(ele%divisor != 0) sum++;
        }
        return (sum <= threshold);
    }
}
