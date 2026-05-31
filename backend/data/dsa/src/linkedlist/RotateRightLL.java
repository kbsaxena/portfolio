package linkedlist;

public class RotateRightLL {
    Node rotateRight(Node head, int k){
        if(k == 0 || head == null || head.next == null) return head;

        int n = 0;
        Node temp = head;
        while(temp!= null){
            n++;
            temp = temp.next;
        }

        k = k%n;

        if(k==0) return head;

        Node fast = head, slow=head;
        for(int i=1; i<=k ; i++){
            fast = fast.next;
        }

        while(fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }

        Node newHead = slow.next;
        slow.next = null;
        fast.next = head;

        return newHead;
    }
}
