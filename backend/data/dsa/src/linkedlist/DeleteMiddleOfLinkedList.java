package linkedlist;

public class DeleteMiddleOfLinkedList {
    public Node deleteMiddle(Node head) {
        if (head == null || head.next == null) return null;

        Node fast = head;
        Node slow = head;
        Node prev = null; //Maintain to keep track of previous node to middle

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = prev.next.next;

        return head;
    }
}
