package linkedlist.doubly;

public class Reverse {
    //TC - O(n), Space O(n)
    public Node reverseRec(Node head) {
        // code here
        if(head == null || head.next == null) return head;

        Node a = head.next;
        head.next = null;
        a.prev = null;
        Node b = reverseRec(a);
        a.next = head;
        head.prev = a;
        return b;
    }


    //TC - O(n), Space O(1)
    public Node reverse(Node head) {
        // code here
        Node prev = null, next = null;
        Node current = head;

        while(current != null){
            next = current.next;
            current.next = prev;
            //One extra line to handle previous
            //connection as well
            current.prev = next;
            prev = current;
            current = next;
        }
        return prev;
    }
}
