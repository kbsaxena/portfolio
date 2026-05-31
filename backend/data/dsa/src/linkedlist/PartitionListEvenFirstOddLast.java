package linkedlist;

public class PartitionListEvenFirstOddLast {
    //Gfg - Same as Partition list
    Node divide(Node head) {
        Node d1 = new Node(-1);
        Node i = d1;

        Node d2 = new Node(-1);
        Node j = d2;

        Node k = head;

        while(k != null){
            if(k.data % 2 ==0){
                i.next = k;
                i = i.next;
            } else {
                j.next = k;
                j = j.next;
            }
            k = k.next;
        }

        i.next = d2.next;
        j.next = null;

        return d1.next;
    }
}
