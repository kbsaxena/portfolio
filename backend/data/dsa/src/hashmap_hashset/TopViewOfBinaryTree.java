package hashmap_hashset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Node {
    int data;
    Node left, right;

    Node(int val) {
        this.data = val;
        this.left = null;
        this.right = null;
    }
}
public class TopViewOfBinaryTree {
    public class Pair{
        int level;
        Node node;
        Pair(int level, Node node){
            this.level = level;
            this.node = node;
        }
    }
    public ArrayList<Integer> topView(Node root) {
        // code here
        HashMap<Integer, Integer> map = new HashMap<>(); //<idx , val>
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, root));

        int left=0, right=0;
        while(q.size()>0){
            Pair front = q.remove();
            int idx = front.level;
            Node node = front.node;
            if(idx<left) left = idx;
            if(idx>right) right = idx;
            if(!map.containsKey(idx)) map.put(idx, node.data);
            if(node.left!=null) q.add(new Pair(idx-1, node.left));
            if(node.right!=null) q.add(new Pair(idx+1, node.right));
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=left; i<=right ;i++){
            ans.add(map.get(i));
        }
        return ans;
    }
}
