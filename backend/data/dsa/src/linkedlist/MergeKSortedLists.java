package linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0) return null;
        ArrayList<ListNode> l1 = new ArrayList<>(Arrays.asList(lists));
        ArrayList<ListNode> l2 = new ArrayList<>();

        while((l1.size()+l2.size())> 1){
            while(l1.size() > 1){
                ListNode a = l1.remove(l1.size()-1);
                ListNode b = l1.remove(l1.size()-1);
                ListNode c = merge(a,b);
                l2.add(c);
            }
            //If single element left
            if(l1.size() == 1) l2.add(l1.remove(0));

            while(l2.size() > 1){
                ListNode a = l2.remove(l2.size()-1);
                ListNode b = l2.remove(l2.size()-1);
                ListNode c = merge(a,b);
                l1.add(c);
            }
            if(l2.size() == 1) l1.add(l2.remove(0));
        }

        return l1.size() == 1?l1.get(0):l2.get(0);
    }

    ListNode merge(ListNode head1, ListNode head2) { //TC - O(m+n)
        // code here
        ListNode dummy = new ListNode(-1);

        ListNode i = head1, j = head2, k = dummy;
        while(i!=null && j!=null){
            if(i.val<j.val){
                k.next = i;
                i = i.next;
            } else {
                k.next = j;
                j = j.next;
            }
            k = k.next;
        }

        if(i == null) k.next = j;
        else k.next = i;

        return dummy.next;
    }
}
