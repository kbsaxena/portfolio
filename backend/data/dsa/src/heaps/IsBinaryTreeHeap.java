package heaps;

import java.util.LinkedList;
import java.util.Queue;

class Node {
    int data;
    Node left,right;
    Node(int d){
        data=d;
        left=right=null;
    }
}

public class IsBinaryTreeHeap {
    //TC - O(N) - Triple Pass
    public boolean isHeap(Node root) {
        return isMaxHeap(root) && isCBT(root,1,size(root));
    }

    private boolean isMaxHeap(Node root) {
        if(root == null) return true;
        int leftData = (root.left != null)?root.left.data:Integer.MIN_VALUE;
        int rightData = (root.right != null)?root.right.data:Integer.MIN_VALUE;
        if(root.data<= leftData || root.data <= rightData) return false;
        return isMaxHeap(root.left) && isMaxHeap(root.right);
    }

    private boolean isCBT(Node root, int idx, int size) {
        if(root==null) return true;
        if(idx>size) return false;
        return isCBT(root.left,idx*2, size) && isCBT(root.right,idx*2 + 1, size);
    }

    private int size(Node root) {
        if(root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }


    //Method 2 Using Queue TC - O(N) - Single Pass
    public boolean isHeap2(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        boolean nullSeen = false;
        while (!q.isEmpty()) {
            Node curr = q.remove();

            // If null encountered
            if (curr == null) {
                nullSeen = true;
            } else {

                // If we saw null before and now non-null → not CBT
                if (nullSeen) return false;

                // Check max-heap property
                if (curr.left != null && curr.data <= curr.left.data) return false;
                if (curr.right != null && curr.data <= curr.right.data) return false;

                // Add children (even nulls)
                q.add(curr.left);
                q.add(curr.right);
            }
        }
        return true;
    }
}
