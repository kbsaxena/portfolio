package binarytrees;

import java.util.ArrayList;

public class DFSTraversal {
    public ArrayList<Integer> dfsTraversal(Node root) {
        //  code here
        ArrayList<Integer> arr = new ArrayList<>();
        dfsPre(root, arr);
        //dfsIn(root, arr);
        //dfsPost(root, arr);
        return arr;
    }

    public void dfsPre(Node root, ArrayList<Integer> arr){
        if(root == null) return;
        arr.add(root.data);
        dfsPre(root.left, arr);
        dfsPre(root.right, arr);
    }

    public void dfsIn(Node root, ArrayList<Integer> arr){
        if(root == null) return;
        dfsIn(root.left, arr);
        arr.add(root.data);
        dfsIn(root.right, arr);
    }

    public void dfsPost(Node root, ArrayList<Integer> arr){
        if(root == null) return;
        dfsPost(root.left, arr);
        dfsPost(root.right, arr);
        arr.add(root.data);
    }
}
