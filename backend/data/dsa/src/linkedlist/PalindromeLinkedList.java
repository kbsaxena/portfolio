package linkedlist;

import java.util.ArrayList;

public class PalindromeLinkedList {
    //Method 1 - Storing values in array list and
    // comparing from both end using i and j pointers
    //Using extra Space TC - O(n) , Space - O(n)
    public boolean isPalindrome1(Node head) {
        // code here
        ArrayList<Integer> a = new ArrayList<>();
        Node temp = head;
        while(temp != null){
            a.add(temp.data);
            temp = temp.next;
        }
        int i=0, j= a.size()-1;
        while(i<j){
            if(a.get(i) != a.get(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    //Method 2 - Using Deep Copy of LL
    //Using extra Space TC - O(n) , Space - O(n)
    public boolean isPalindrome2(Node head) {
        // code here
        //Deep Copy Code
        Node duplicate = new Node(-1);
        Node i = duplicate;
        Node j = head;

        while(j!= null){
            Node newNode = new Node(j.data);
            i.next = newNode;
            j = j.next;
            i = i.next;
        }
        duplicate = duplicate.next; //Deep Copy LL

        i = reverseList(duplicate);
        j = head;

        while(j!=null){
            if(i.data != j.data) return false;
            i = i.next;
            j = j.next;
        }
        return true;
    }

    //Method 3 - Break in 2 list and then reverse 2nd and compare both
    //No extra Space TC - O(n) , Space - O(1)
    public boolean isPalindrome3(Node head) {
        //Break Into 2 list
        Node slow= head, fast = head;
        while(fast.next!= null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        Node head2 = slow.next;
        slow.next = null;

        head2 = reverseList(head2);

        Node i = head;
        Node j = head2;

        while(j!=null){
            if(i.data != j.data) return false;
            i = i.next;
            j = j.next;
        }

        return true;
    }

    Node reverseList(Node head) {
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
