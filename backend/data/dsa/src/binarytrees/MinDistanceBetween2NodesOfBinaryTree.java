package binarytrees;

import java.util.ArrayList;

public class MinDistanceBetween2NodesOfBinaryTree {
    int findDist(Node root, int n1, int n2) {
        // Your code here
        ArrayList<Node> arr = new ArrayList<>();
        ArrayList<Node> a1 = new ArrayList<>();
        ArrayList<Node> a2 = new ArrayList<>();
        dfsPre(root,n1,n2,arr,a1,a2);
        int i=0;
        while(i<a1.size() && i<a2.size() && a1.get(i).data == a2.get(i).data) i++;
        return a1.size()-i + a2.size()-i;
    }

    private void dfsPre(Node root, int n1, int n2, ArrayList<Node> arr, ArrayList<Node> a1, ArrayList<Node> a2) {
        if(root == null) return;
        arr.add(root);
        if(root.data == n1) a1.addAll(arr);
        if(root.data == n2) a2.addAll(arr);
        dfsPre(root.left,n1,n2,arr,a1,a2);
        dfsPre(root.right,n1,n2,arr,a1,a2);
        arr.remove(arr.size()-1); //Backtracking
    }
}
