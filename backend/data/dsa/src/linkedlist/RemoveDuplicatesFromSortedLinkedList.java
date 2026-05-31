package linkedlist;

public class RemoveDuplicatesFromSortedLinkedList {
    Node removeDuplicates(Node head) {
        // Your code here
        Node i = head, j = head;

        while(j != null){
            if(i.data == j.data) j = j.next;
            else{
                i.next = j;
                i = j;
            }
        }
        i.next = j;

        return head;
    }
}
