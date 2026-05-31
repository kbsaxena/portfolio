package linkedlist;

public class KthNodeFromLast {
    // Function to find the data of kth node from
    // the end of a linked list.
    //Method1
    int getKthFromLast1(Node head, int k) {
        int len = 0;
        Node temp = head;
        while(temp != null){
            temp = temp.next;
            len++;
        }
        if(k>len) return -1;
        temp = head;
        for(int i=1;i<=len-k;i++){
            temp = temp.next;
        }

        return temp.data;
    }

    //Method 2
    int getKthFromLast(Node head, int k) {
        Node fast = head, slow = head;
        for(int i=1; i<=k; i++){
            if(fast == null) return -1;
            fast = fast.next;
        }

        while(fast != null){
            slow= slow.next;
            fast = fast.next;
        }

        return slow.data;
    }
}
