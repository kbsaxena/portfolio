package linkedlist;

public class MergeSortForLL {
    public Node mergeSort(Node head) {
        if(head == null || head.next == null) return head;
        //Break Into 2 list
        Node slow= head, fast = head;
        while(fast.next!= null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        Node head2 = slow.next;
        slow.next = null;

        //magic
        head = mergeSort(head);
        head2 = mergeSort(head2);

        return sortedMerge(head, head2);
    }

    private Node sortedMerge(Node head1, Node head2) { //TC - O(m+n)
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
