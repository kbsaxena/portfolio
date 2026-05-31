package linkedlist;

public class ReverseSublist {
    public static Node reverseBetween(int l, int r, Node head) {
        // code here
        Node dummy = new Node(-1);
        dummy.next = head;

        Node a=null, b=null, c=null, d=null;
        Node temp = dummy;

        for(int i=0;i<=r+1; i++){
            if(i==l-1) a = temp;
            if(i==l) b = temp;
            if(i==r) c = temp;
            if(i==r+1) d = temp;

            if(temp == null) break;
            temp = temp.next;
        }

        a.next = null;
        c.next = null;

        //Now 3 ll dummy to a, b to c, d to null

        c = reverseList(b);

        a.next = c;
        b.next = d;

        return dummy.next;
    }

    static Node reverseList(Node head) {
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
