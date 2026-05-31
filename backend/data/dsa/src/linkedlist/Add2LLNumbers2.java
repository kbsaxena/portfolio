package linkedlist;

public class Add2LLNumbers2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode t1 = reverse(l1);
        ListNode t2 = reverse(l2);
        ListNode dummy = new ListNode(-1);
        ListNode t = dummy;
        int carry = 0;

        while(t1 != null || t2 != null){
            int sum = ((t1!=null) ? t1.val : 0) + ((t2!=null) ? t2.val : 0) + carry;

            if(sum>9){
                sum = sum%10;
                carry = 1;
            } else carry = 0;

            ListNode n = new ListNode(sum);
            t.next = n;

            if(t1 != null) t1 = t1.next;
            if(t2 != null) t2 = t2.next;

            t = t.next;
        }

        if(carry == 1){
            ListNode n = new ListNode(carry);
            t.next = n;
        }

        return reverse(dummy.next);

    }

    ListNode reverse(ListNode head) {
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
