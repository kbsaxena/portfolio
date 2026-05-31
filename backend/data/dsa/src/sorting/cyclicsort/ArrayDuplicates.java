package sorting.cyclicsort;

import java.util.ArrayList;

public class ArrayDuplicates {
    //Method 2 Cyclic Sort
    public ArrayList<Integer> findDuplicates(int[] arr) {
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
            if(arr[i] != i+1) ans.add(arr[i]);
        }
        return ans;
    }

    //Method 1 Negative Marking
    public ArrayList<Integer> findDuplicates1(int[] arr) {
        ArrayList<Integer> ans = new ArrayList<>();

        for(int i = 0; i < arr.length; i++) {
            int idx = Math.abs(arr[i]) - 1;

            if(arr[idx] < 0) {
                ans.add(Math.abs(arr[i]));
            } else {
                arr[idx] = -arr[idx];
            }
        }

        return ans;
    }
}
