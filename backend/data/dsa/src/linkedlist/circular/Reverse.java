package linkedlist.circular;

public class Reverse {
    public Node reverseCircular(Node head) {
        //code here
        //Finding tail
        Node tail = head;
        while(tail.next != head){
            tail = tail.next;
        }

        //Converted to singly ll
        tail.next = null;

        // We can write in 2 ways
        // tail = reverse(head)  - this is also valid
        reverse(head);

        head.next = tail;
        return tail;
    }

    Node reverse(Node head) {
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
}
