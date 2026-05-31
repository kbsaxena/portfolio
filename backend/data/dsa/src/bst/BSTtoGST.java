package bst;

import java.util.ArrayList;

public class BSTtoGST {
    public static void transformTree(Node root) {
        // code here
        ArrayList<Node> arr = new ArrayList<>();
        inorder(root, arr);
        int temp=0, sum=0;
        for(int i=arr.size()-1;i>=0;i--){
            temp = temp + arr.get(i).data;
            arr.get(i).data = sum;
            sum = temp;
        }
    }

    private static void inorder(Node root, ArrayList<Node> arr) {
        if(root == null) return;
        inorder(root.left, arr);
        arr.add(root);
        inorder(root.right, arr);
    }
}
