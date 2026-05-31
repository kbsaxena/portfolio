package linkedlist;

public class PartitionListWithEqualValueNodesInBetween {
    //Gfg Practice
    public static Node partition(Node node, int x) {
        Node d1 = new Node(-1);
        Node i = d1;

        Node d2 = new Node(-1);
        Node m = d2;

        Node d3 = new Node(-1);
        Node j = d3;

        Node k = node;

        while(k != null){
            if(k.data < x){
                i.next = k;
                i = i.next;
            } else if(k.data > x) {
                j.next = k;
                j = j.next;
            } else {
                m.next = k;
                m = m.next;
            }
            k = k.next;
        }

        i.next = d2.next;
        m.next = d3.next;
        j.next = null;

        return d1.next;
    }
}
