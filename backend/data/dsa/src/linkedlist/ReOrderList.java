package linkedlist;

public class ReOrderList {
    //i/p = 1 2 3 4 5 6  o/p= 1 6 2 5 3 4
    void reorderlist(Node head) {
        Node slow= head, fast = head;
        while(fast.next!= null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        Node head2 = slow.next;
        slow.next = null;

        head2 = reverseList(head2);

        head = merge(head, head2);
    }

    Node reverseList(Node head) {
        // code here
        Node prev = null, next = null;
        Node current = head;

        while(current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    Node merge(Node head1, Node head2) { //TC - O(m+n)
        // code here
        Node dummy = new Node(-1);

        Node i = head1, j = head2, k = dummy;
        while(i!=null && j!=null){
            if(i.data<j.data){
                k.next = i;
                i = i.next;
            } else {
                k.next = j;
                j = j.next;
            }
            k = k.next;
        }

        if(i == null) k.next = j;
        else k.next = i;

        return dummy.next;
    }
}
