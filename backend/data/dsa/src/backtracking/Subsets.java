package backtracking;

import java.util.ArrayList;

public class Subsets {
    //Method 1 Backtracking
    public ArrayList<ArrayList<Integer>> subsets(int arr[]) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        generate(0,arr,new ArrayList<>(), ans);
        return ans;
    }

    private void generate(int i, int[] arr, ArrayList<Integer> a, ArrayList<ArrayList<Integer>> ans) {
        if(i==arr.length){
            ans.add(new ArrayList<>(a)); //Storing copy not actual array as its by reference
            return;
        }
        generate(i+1,arr,a,ans); //skip
        a.add(arr[i]);
        generate(i+1,arr,a,ans); //pick
        a.remove(a.size()-1); //backtracking
    }

    //Method 2 Bit Manipulation
    public ArrayList<ArrayList<Integer>> subsets2(int arr[]) {
        int n = arr.length;
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int twoPowerN = (1<<n);
        for(int i=0;i<twoPowerN;i++){
            ArrayList<Integer> subset = new ArrayList<>();
            for(int j=0;j<n;j++){
                if((i>>j)%2 == 1) subset.add(arr[j]);
            }
            ans.add(subset);
        }
        return ans;
    }
}
