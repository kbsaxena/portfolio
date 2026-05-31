package bitmanipulation;

import java.util.ArrayList;

public class FindAllSubsets {
    public static void main(String[] args) {
        //System.out.println(subsets(new int[]{1,2,3}));
        System.out.println(subsetStrings("abc".toCharArray()));
    }

    public static ArrayList<String> subsetStrings(char arr[]) {
        int n = arr.length;
        ArrayList<String> ans = new ArrayList<>();
        int twoPowerN = (1<<n);
        for(int i=0;i<twoPowerN;i++){
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<n;j++){
                if((i>>j)%2 == 1) sb.append(arr[j]);
            }
            if(sb.length()>0) ans.add(sb.toString());
        }
        return ans;
    }

    public static ArrayList<ArrayList<Integer>> subsets(int arr[]) {
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
