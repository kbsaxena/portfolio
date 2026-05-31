package stacks;

import java.util.Stack;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) { this.val = val; }
}

public class RemoveNodesFromLL {
    public ListNode removeNodes(ListNode head) {
        Stack<ListNode> st = new Stack<>();
        ListNode temp = head;
        while(temp != null){
            while(st.size()>0 && st.peek().val < temp.val) st.pop();
            st.push(temp);
            temp = temp.next;
        }

        ListNode dummy = new ListNode(-1);
        temp = dummy;
        while(st.size()>0){
            ListNode top = st.pop();
            temp.next = top;
            temp = temp.next;
        }

        temp.next = null;

        temp = dummy.next;
        return reverse(temp);
    }

    private ListNode reverse(ListNode head){
        ListNode c = head, p = null, n = null;
        while(c != null){
            n = c.next;
            c.next = p;
            p = c;
            c = n;
        }

        return p;
    }
}
