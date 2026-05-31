package binarytrees;

import java.util.ArrayList;

public class RootToLeafPathSum {
    boolean hasPathSum(Node root, int target) {
        // Your code here
        if(root == null) return false;
        if(root.left == null && root.right ==null) return (root.data == target);
        return hasPathSum(root.left, target-root.data) || hasPathSum(root.right, target-root.data);
    }
}
