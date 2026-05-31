package linkedlist;

public class SwappingNodes {
    public Node swapNodes(Node head, int k) {
        Node swap1 = null, swap2 = null;
        Node fast = head, slow = head;

        // move fast k-1 steps ahead
        for(int i=1; i<k; i++){
            fast = fast.next;
        }

        swap1 = fast;
        // move both pointers
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        swap2 = slow;

        // swap
        int temp = swap1.data;
        swap1.data = swap2.data;
        swap2.data = temp;

        return head;
    }
}
