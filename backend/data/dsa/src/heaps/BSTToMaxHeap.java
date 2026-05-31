package heaps;

import java.util.ArrayList;

public class BSTToMaxHeap {
    static int idx;
    public static void convertToMaxHeapUtil(Node root) {
        // code here
        ArrayList<Integer> arr = new ArrayList<>();
        inOrder(root, arr);
        idx = 0;
        postOrder(root,arr);
    }

    private static void inOrder(Node root, ArrayList<Integer> arr) {
        if(root == null) return;
        inOrder(root.left, arr);
        arr.add(root.data);
        inOrder(root.right, arr);
    }

    private static void postOrder(Node root, ArrayList<Integer> arr) {
        if(root == null) return;
        postOrder(root.left, arr);
        postOrder(root.right, arr);
        root.data = arr.get(idx++);
    }

}
