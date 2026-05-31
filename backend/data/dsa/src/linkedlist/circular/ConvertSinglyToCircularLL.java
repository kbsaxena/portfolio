package linkedlist.circular;


public class ConvertSinglyToCircularLL {
    void convert(Node head){
        Node temp = head;
        while(temp.next!=null){
            temp = temp.next;
        }
        temp.next = head;
    }
}
