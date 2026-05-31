package binarytrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversalLineWise {
    public class Pair{
        Node node;
        int level;
        Pair(Node node, int level){
            this.node = node;
            this.level = level;
        }
    }
    public ArrayList<ArrayList<Integer>> levelOrder(Node root) {
        // code here
        Queue<Pair> q = new LinkedList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        int n = levels(root);
        for(int i=0;i<n;i++) ans.add(new ArrayList<>());
        if(root!=null) q.add(new Pair(root, 0));
        while(q.size()>0){
            Pair front = q.remove();
            Node node = front.node;
            int level = front.level;

            ans.get(level).add(node.data);

            if(node.left!=null) q.add(new Pair(node.left, level+1));
            if(node.right!=null)q.add(new Pair(node.right, level+1));
        }
        return ans;
    }

    private static int levels(Node root) {
        //base case
        if(root == null) return 0;
        return 1 + Math.max(levels(root.left), levels(root.right));
    }
}
