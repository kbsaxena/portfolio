package linkedlist;

public class CriticalMinMaxNoOfNodes {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        //List size is always >= 2
        ListNode p = head;
        ListNode c = head.next;
        ListNode n = head.next.next;

        int[] ans = {-1,-1};
        if(n == null) return ans;
        int idx = 1, first = -1, last = -1, min = Integer.MAX_VALUE;

        while(n != null){
            if((c.val < p.val && c.val < n.val) || (c.val > p.val && c.val > n.val)){
                if(first == -1) first = idx;
                if(last != -1){ //distance between consecutive nodes
                    int distance = idx - last;
                    min = Math.min(min, distance);
                }
                last = idx;
            }
            idx++;
            p = p.next;
            c = c.next;
            n = n.next;
        }

        int max = last-first;
        if(min != Integer.MAX_VALUE){
            ans[0] = min;
            ans[1] = max;
        }

        return ans;
    }

}
