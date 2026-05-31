package binarytrees;

import java.util.ArrayList;

public class FlattenBinaryTreeToLL {
    //Method 2 - Using Recursion
    public static void flatten(Node root) {
        // code here
        if(root == null) return;
        Node lst = root.left;
        Node rst = root.right;
        flatten(lst);
        flatten(rst);
        root.right = lst;
        root.left = null;

        Node temp = root;  //lst can be null
        while(temp.right !=null) temp = temp.right;
        temp.right = rst;
    }

    //Method 1
    public static void flatten1(Node root) {
        // code here
        ArrayList<Node> arr = new ArrayList<>();
        dfsPre(root, arr);
        for(int i = 0; i<arr.size()-1;i++){
            arr.get(i).left = null;
            arr.get(i).right = arr.get(i+1);
        }
    }

    public static void dfsPre(Node root, ArrayList<Node> arr){
        if(root == null) return;
        arr.add(root);
        dfsPre(root.left, arr);
        dfsPre(root.right, arr);
    }
}
