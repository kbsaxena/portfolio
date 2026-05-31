package linkedlist.circular;

/* Given pair Class */
class Pair<T, U> {
    T first;
    U second;

    Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}

public class SplitInto2CircularList {
    public Pair<Node, Node> splitList(Node head) {
        // Code here
        //Finding tail
        Node tail = head;
        while(tail.next != head){
            tail = tail.next;
        }

        //Converted to singly ll
        tail.next = null;

        //Partition
        Node slow = head, fast = head;
        while(fast.next!=null && fast.next.next !=null){
            slow = slow.next;
            fast = fast.next.next;
        }

        Node a = slow.next;

        slow.next = head;
        tail.next = a;

        return new Pair<>(head, a);
    }
}
