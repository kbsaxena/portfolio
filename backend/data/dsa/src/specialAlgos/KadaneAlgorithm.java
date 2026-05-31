package specialAlgos;

public class KadaneAlgorithm {
    int maxSubarraySum(int[] arr) {
        int currentSum = 0, maxSum = Integer.MIN_VALUE;
        for(int ele: arr){
            currentSum += ele;
            maxSum = Math.max(maxSum, currentSum);
            if(currentSum < 0) currentSum = 0;
        }
        return maxSum;
    }
}
