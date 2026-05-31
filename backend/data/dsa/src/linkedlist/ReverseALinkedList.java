package linkedlist;

import java.util.ArrayList;

public class ReverseALinkedList {
    //Method 1 - Storing nodes in array list
    //Using extra Space TC - O(n) , Space - O(n)
    Node reverseList1(Node head) {
        // code here
        ArrayList<Node> a = new ArrayList<>();
        Node temp = head;
        while(temp != null){
            a.add(temp);
            temp = temp.next;
        }
        for(int i=a.size()-1; i>=1; i--){
            a.get(i).next = a.get(i-1);
        }
        a.get(0).next = null;

        return a.get(a.size()-1);
    }

    //Method 2 - Using recursion
    //Using extra Space TC - O(n) , Space - O(n)
    Node reverseList2(Node head) {
        // code here
        if(head == null || head.next == null) return head;

        Node a = head.next;
        head.next = null;
        Node b = reverseList2(a);
        a.next = head;
        return b;
    }

    //Method 3 - Iterative (Most Efficient)
    //No extra Space TC - O(n) , Space - O(1)
    Node reverseList3(Node head) {
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
