package bst;

import java.util.ArrayList;

public class KthSmallestinBST {
    // M2
    int ans = -1;
    int count = 0;
    public int kthSmallest(Node root, int k) {
        // code here
        inorder2(root, k);
        return ans;
    }

    private void inorder2(Node root, int k) {
        if(root == null) return;

        inorder2(root.left, k);

        count++;
        if(count == k){
            ans = root.data;
            return;
        }

        inorder2(root.right, k);
    }


    // M1
    public int kthSmallest1(Node root, int k) {
        // code here
        ArrayList<Integer> arr = new ArrayList<>();
        inorder(root, arr);
        return arr.size()>=k? arr.get(k - 1): -1;
    }

    private void inorder(Node root, ArrayList<Integer> arr) {
        if(root == null) return;
        inorder(root.left, arr);
        arr.add(root.data);
        inorder(root.right, arr);
    }
}
