package binarytrees;

import java.util.ArrayList;

public class RootToLeafPaths {
    public static ArrayList<ArrayList<Integer>> Paths(Node root) {
        // code here
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> arr = new ArrayList<>();
        dfs(root, arr, ans);
        return ans;
    }

    private static void dfs(Node root, ArrayList<Integer> arr, ArrayList<ArrayList<Integer>> ans) {
        if(root == null) return;

        arr.add(root.data);

        //Leaf node
        if(root.left == null && root.right == null){
            ArrayList<Integer> a = new ArrayList<>(arr); //Create Copy
            ans.add(a);
        }
        dfs(root.left, arr, ans);
        dfs(root.right, arr, ans);

        //BackTracking
        arr.remove(arr.size()-1);
    }
}
