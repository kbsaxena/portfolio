package sorting.cyclicsort;

import java.util.ArrayList;
import java.util.List;

public class FindAllNumbersDisappeared {
    public List<Integer> findDisappearedNumbers(int[] arr) {
        int i=0;
        while(i<arr.length){
            if(arr[i] == i+1 || arr[i] == arr[arr[i]-1]) i++;
            else {
                int idx = arr[i] - 1;
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(i=0;i<arr.length;i++){
            if(arr[i] != i+1) ans.add(i+1);
        }
        return ans;
    }
}
