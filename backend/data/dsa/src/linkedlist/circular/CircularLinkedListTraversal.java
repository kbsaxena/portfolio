package linkedlist.circular;

class Node {
    int data;
    Node next;

    Node(int data){
        this.data = data;
    }
}

public class CircularLinkedListTraversal {
    void printList1(Node head) {
        // Print head first
        System.out.print(head.data + " ");
        Node temp = head.next;
        while(temp != head){
            System.out.print(temp.data + "");
            temp = temp.next;
        }
    }

    void printList2(Node head) {
        // Your code here
        Node temp = head;
        while(temp.next != head){
            System.out.print(temp.data + "");
            temp = temp.next;
        }
        System.out.print(temp.data);
    }
}
