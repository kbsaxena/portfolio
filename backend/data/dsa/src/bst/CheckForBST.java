package bst;

import java.util.ArrayList;

public class CheckForBST {
    //Method 2
    public boolean isBST2(Node root) {
        // code here
        ArrayList<Integer> arr = new ArrayList<>();
        inorder(root, arr);
        for(int i=0;i<arr.size()-1;i++){
            if(arr.get(i) >= arr.get(i+1)) return false;
        }
        return true;
    }

    private void inorder(Node root, ArrayList<Integer> arr) {
        if(root == null) return;
        inorder(root.left, arr);
        arr.add(root.data);
        inorder(root.right, arr);
    }


    //Method 1
    public boolean isBST(Node root) {
        // code here
        if(root == null) return true;
        if(root.data <= max(root.left)) return false;
        if(root.data >= min(root.right)) return false;
        return isBST(root.left) && isBST(root.right);
    }

    private int max(Node root){
        if(root == null) return Integer.MIN_VALUE;
        return Math.max(root.data, Math.max(max(root.left),max(root.right)));
    }

    private int min(Node root){
        if(root == null) return Integer.MAX_VALUE;
        return Math.min(root.data, Math.min(min(root.left),min(root.right)));
    }
}
