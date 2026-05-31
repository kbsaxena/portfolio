package binarytrees;

import java.util.ArrayList;

public class PathsFromRootWithSpecifiedSum {
    public static ArrayList<ArrayList<Integer>> printPaths(Node root, int sum) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> arr = new ArrayList<>();
        dfs(root, sum, arr, ans);
        return ans;
    }

    private static void dfs(Node root, int target, ArrayList<Integer> arr, ArrayList<ArrayList<Integer>> ans) {
        if(root == null) return;

        arr.add(root.data);
        target -= root.data;

        if(target == 0) {
            ArrayList<Integer> a = new ArrayList<>(arr); //Create Copy
            ans.add(a);
        }

        dfs(root.left, target, arr, ans);
        dfs(root.right, target, arr, ans);

        //BackTracking
        arr.remove(arr.size()-1);
    }
}
