package bst;

public class SumOfNodesInBSTRange {
    public int nodeSum(Node root, int l, int r) {
        if (root == null) return 0;

        // if node is smaller than range
        if (root.data < l) {
            return nodeSum(root.right, l, r);
        }

        // if node is greater than range
        if (root.data > r) {
            return nodeSum(root.left, l, r);
        }

        // node is within range
        return root.data
                + nodeSum(root.left, l, r)
                + nodeSum(root.right, l, r);
    }
}
