package linkedlist;

public class MaxTwinPairSum {
    //Leetcode 2130
    public int pairSum(ListNode head) {
        ListNode slow= head, fast = head;
        while(fast.next!= null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode head2 = slow.next;
        slow.next = null;

        head2 = reverseList(head2);

        ListNode i = head;
        ListNode j = head2;
        int max = 0;

        while(j!=null){
            int sum = i.val+j.val;
            max = Math.max(sum,max);
            i = i.next;
            j = j.next;
        }

        return max;
    }

    ListNode reverseList(ListNode head) {
        // code here
        ListNode prev = null, next = null;
        ListNode current = head;

        while(current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
