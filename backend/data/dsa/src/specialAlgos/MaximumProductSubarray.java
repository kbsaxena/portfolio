package specialAlgos;

public class MaximumProductSubarray {
    int maxProduct(int[] arr) {
        int currProduct = 1, maxProduct = Integer.MIN_VALUE;
        for(int i=0;i<arr.length;i++){
            currProduct *= arr[i];
            maxProduct = Math.max(maxProduct, currProduct);
            if(currProduct == 0) currProduct = 1;
        }
        currProduct = 1;
        for(int i=arr.length-1;i>=0;i--){
            currProduct *= arr[i];
            maxProduct = Math.max(maxProduct, currProduct);
            if(currProduct == 0) currProduct = 1;
        }
        return maxProduct;
    }
}
