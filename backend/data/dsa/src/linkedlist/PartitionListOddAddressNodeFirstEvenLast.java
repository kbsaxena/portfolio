package linkedlist;

public class PartitionListOddAddressNodeFirstEvenLast {
    //Leetcode 328
    public ListNode oddEvenList(ListNode head) {
        ListNode d1 = new ListNode(-1);
        ListNode i = d1;

        ListNode d2 = new ListNode(-1);
        ListNode j = d2;

        ListNode k = head;
        //My Approach using Flag
        boolean flag = true;

        //gfg approach
        int idx = 1;

        while(k != null){
            /*if(flag){
                i.next = k;
                i = i.next;
                flag = false;
            } else {
                j.next = k;
                j = j.next;
                flag = true;
            }*/
            if(idx % 2 == 1){
                i.next = k;
                i = i.next;
            } else {
                j.next = k;
                j = j.next;
            }
            k = k.next;
            idx++;
        }

        i.next = d2.next;
        j.next = null;

        return d1.next;
    }
}
