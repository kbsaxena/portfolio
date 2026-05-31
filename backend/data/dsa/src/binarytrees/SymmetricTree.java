package binarytrees;

public class SymmetricTree {
    //Method 1
    public boolean isSymmetric1(Node root) {
        // code here
        mirror(root.left);
        return isIdentical(root.left, root.right);
    }

    //M2
    public boolean isSymmetric(Node root) {
        // code here
        return helper(root.left, root.right);
    }

    private boolean helper(Node p, Node q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.data != q.data) return false;
        return helper(p.left, q.right) && helper(p.right, q.left);
    }

    //Method 1 - Preserving the tree structure
    public boolean isSymmetricPreservingTree(Node root) {
        // code here
        mirror(root.left); //This modified the left tree
        boolean flag =  isIdentical(root.left, root.right);
        mirror(root.left); //Preserved back
        return flag;
    }

    public boolean isIdentical(Node p, Node q) {
        // code here
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.data != q.data) return false;
        return isIdentical(p.left, q.left) && isIdentical(p.right, q.right);
    }

    void mirror(Node root) {
        // code here
        if(root == null) return;
        Node temp = root.left;
        root.left = root.right;
        root.right = temp;
        mirror(root.left);
        mirror(root.right);
    }
}
