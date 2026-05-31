package linkedlist.doubly;

public class RemoveDuplicatesFromSortedDLL {
    Node removeDuplicates(Node head) {
        // Code Here.
        // Your code here
        Node i = head, j = head;

        while(j != null){
            if(i.data == j.data) j = j.next;
            else{
                i.next = j;
                j.prev = i; //Just add this
                i = j;
            }
        }
        i.next = j;

        return head;
    }
}
