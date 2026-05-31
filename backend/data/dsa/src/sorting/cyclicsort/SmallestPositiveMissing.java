package sorting.cyclicsort;

import java.util.ArrayList;

public class SmallestPositiveMissing {
    public int missingNumber(int[] arr) {
        //Cyclic Sort (1-n) -> ignoring zeros too
        int i=0;
        while(i<arr.length){
            //All ignore scenarios
            if(arr[i] == i+1 || //its at its correct position
                    arr[i] <= 0 ||  //number is negative
                    arr[i] > arr.length || //number is greater than array length
                    arr[i] == arr[arr[i]-1]) //Found duplicate
                i++;
            else {
                int idx = arr[i] - 1;
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(i=0;i<arr.length;i++){
            if(arr[i] != i+1) return i+1;
        }
        return i+1; //equal to array length (last element)
    }
}
