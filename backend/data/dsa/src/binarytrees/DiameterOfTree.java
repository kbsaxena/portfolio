package binarytrees;

public class DiameterOfTree {
    static int maxDia;
    //Method 2
    public int diameter(Node root) {
        // code here
        maxDia = 0;
        levels(root);
        return maxDia;
    }

    private static int levels(Node root) {
        //base case
        if(root == null) return 0;
        int leftLevels = levels(root.left);
        int rightLevels = levels(root.right);

        maxDia = Math.max(maxDia, leftLevels + rightLevels);

        return 1 + Math.max(leftLevels, rightLevels);
    }

    //Method 1
    public int diameter1(Node root) {
        // code here
        maxDia = 0;
        dfs(root);
        return maxDia;
    }

    private void dfs(Node root) {
        if(root == null) return;
        int dia = levels(root.left) + levels(root.right);
        maxDia = Math.max(maxDia, dia);
        dfs(root.left);
        dfs(root.right);
    }

    private static int levels1(Node root) {
        //base case
        if(root == null) return 0;
        int leftLevels = levels1(root.left);
        int rightLevels = levels1(root.right);

        return 1 + Math.max(leftLevels, rightLevels);
    }
}
