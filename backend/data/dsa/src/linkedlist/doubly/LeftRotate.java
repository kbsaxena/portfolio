package linkedlist.doubly;

public class LeftRotate {

    public Node rotateDLL(Node head, int k) {
        if(k == 0 || head == null || head.next == null) return head;

        int n = 0;
        Node temp = head;
        while(temp!= null){
            n++;
            temp = temp.next;
        }

        k = k%n;

        if(k==0) return head;

        //Just add this one line - Rest Same logic as RotateRight
        k = n-k;

        Node fast = head, slow=head;
        for(int i=1; i<=k ; i++){
            fast = fast.next;
        }

        while(fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }

        Node newHead = slow.next;
        newHead.prev = null;
        slow.next = null;
        fast.next = head;

        return newHead;
    }
}
