package binarytrees;

import java.util.ArrayList;

public class RightView {
    public ArrayList<Integer> rightView(Node root) {
        // code here
        int[] arr = new int[levels(root)];
        dfsPre(root, 0 , arr);
        ArrayList<Integer> ans = new ArrayList<>();
        for(int ele: arr) ans.add(ele);
        return ans;
    }
    private static int levels(Node root) {
        //base case
        if(root == null) return 0;
        return 1 + Math.max(levels(root.left), levels(root.right));
    }

    public void dfsPre(Node root, int level, int[] arr){
        if(root == null) return;
        arr[level] = root.data;
        dfsPre(root.left, level+1, arr);
        dfsPre(root.right, level+1, arr);
    }
}
