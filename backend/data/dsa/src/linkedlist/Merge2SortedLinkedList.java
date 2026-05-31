package linkedlist;

public class Merge2SortedLinkedList {
    Node sortedMerge(Node head1, Node head2) { //TC - O(m+n)
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
