package binarytrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSTraversal {
    public static void main(String[] args) {
        Node a = new Node(3);
        Node b = new Node(5);
        Node c = new Node(2);
        Node d = new Node(7);
        Node e = new Node(1);
        Node f = new Node(6);
        a.left = b; a.right = c;
        b.left = d; b.right = e;
        c.right = f;

        bfs(a);
    }

    //Level Order Traversal/BFS
    private static void bfs(Node root){
        Queue<Node> q = new LinkedList<>();
        if(root!=null) q.add(root);
        while(q.size()>0){
            Node front = q.remove();
            System.out.print(front.data + " ");
            if(front.left!=null) q.add(front.left);
            if(front.right!=null)q.add(front.right);
        }
    }
}
