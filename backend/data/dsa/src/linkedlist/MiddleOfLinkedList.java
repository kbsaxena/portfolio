package linkedlist;

public class MiddleOfLinkedList {
    // 2 Pass solution(Traversing 2 times)
    int getMiddle2(Node head) {
        // code here
        int length = 0;
        Node temp = head;
        while(temp != null){
            length++;
            temp = temp.next;
        }

        temp = head;
        for(int i=1; i<=length/2; i++){
            temp = temp.next;
        }

        return temp.data;
    }

    // 2 pointer solution
    int getMiddle(Node head) {
        Node fast = head;
        Node slow = head;
        while(fast != null && fast.next!= null){
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow.data;
    }
}
