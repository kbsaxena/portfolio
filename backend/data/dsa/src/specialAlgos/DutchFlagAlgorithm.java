package specialAlgos;

public class DutchFlagAlgorithm {
    public void sort012(int[] arr) {
        int lo =0, mid =0, hi = arr.length-1;
        while(mid<=hi){
            if(arr[mid] == 0){
                int temp = arr[lo];
                arr[lo] = arr[mid];
                arr[mid] = temp;
                lo++;
                mid++;
            } else if(arr[mid] == 2){
                int temp = arr[hi];
                arr[hi] = arr[mid];
                arr[mid] = temp;
                hi--;
            } else mid++;
        }
    }
}

