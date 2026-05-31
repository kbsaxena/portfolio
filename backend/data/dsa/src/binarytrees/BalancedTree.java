package binarytrees;

public class BalancedTree {
    //Method 2 TC-O(n)
    static boolean flag;
    public boolean isBalanced(Node root) {
        flag = true;
        levels(root);
        return flag;
    }

    private static int levels(Node root) {
        //base case
        if(root == null) return 0;
        int leftLevels = levels(root.left);
        int rightLevels = levels(root.right);

        if(Math.abs(leftLevels-rightLevels) > 1) flag = false; //extra

        return 1 + Math.max(leftLevels, rightLevels);
    }

    //Method 1 - TC O(n square)
    public boolean isBalanced1(Node root) {
        // code here
        if(root == null) return true;
        int leftLevels = levels(root.left);
        int rightLevels = levels(root.right);
        if(Math.abs(leftLevels-rightLevels) > 1) return false;
        return isBalanced1(root.left) && isBalanced1(root.right);
    }

    private static int levels1(Node root) {
        //base case
        if(root == null) return 0;
        return 1 + Math.max(levels1(root.left), levels1(root.right));
    }
}
