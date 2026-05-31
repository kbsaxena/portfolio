package linkedlist;

public class FirstNodeOfLoopInLinkedList {
    public int cycleStart(Node head) {
        Node fast = head;
        Node slow = head;
        while(fast != null && fast.next!= null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) break;
        }

        Node temp = head;
        while(temp != slow) {
            temp = temp.next;
            slow = slow.next;
        }

        return temp.data;
    }
}
