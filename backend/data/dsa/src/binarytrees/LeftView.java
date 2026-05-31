package binarytrees;

import java.util.ArrayList;

public class LeftView {
    public ArrayList<Integer> leftView(Node root) {
        // code here
        int[] arr = new int[levels(root)];
        dfsPost(root, 0 , arr);
        ArrayList<Integer> ans = new ArrayList<>();
        for(int ele: arr) ans.add(ele);
        return ans;
    }
    private static int levels(Node root) {
        //base case
        if(root == null) return 0;
        return 1 + Math.max(levels(root.left), levels(root.right));
    }

    public void dfsPost(Node root, int level, int[] arr){
        if(root == null) return;
        arr[level] = root.data;
        dfsPost(root.right, level+1, arr);
        dfsPost(root.left, level+1, arr);
    }
}
