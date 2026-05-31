package recursion;

import java.util.ArrayList;

public class StringSubSetSums {
    public static void main(String[] args) {
        int arr[] = {2, 3};
        ArrayList<Integer> subsets = new ArrayList<>();
        subsets(arr, 0, 0, subsets);
        System.out.println(subsets);
        //Collections.sort(subsets);
        //System.out.println(subsets);
    }

    private static void subsets(int[] arr, int sum, int i, ArrayList<Integer> subsets) {
        if(i==arr.length){
            //System.out.print(ans + " ");
            subsets.add(sum);
            return;
        }

        subsets(arr, sum+arr[i], i+1, subsets); //Pick ch
        subsets(arr, sum, i+1, subsets);        //Skip ch
    }
}
