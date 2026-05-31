package linkedlist.doubly;

class Node {
    int data;
    Node next;
    Node prev;

    Node(int data){
        this.data = data;
    }
}

class DoublyLL{
    Node head;
    Node tail;
    int size;

    void insertAtHead(int val){
        Node n = new Node(val);
        if(size == 0) head = tail = n;
        else {
            n.next = head;
            head.prev = n;
            head = n;
        }
        size++;
    }

    void insertAtTail(int val){
        Node n = new Node(val);
        if(size == 0) head = tail = n;
        else {
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
        size++;
    }

    void display(){
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    void displayReverse(){
        Node temp = tail;
        while(temp != null){
            System.out.print(temp.data + " ");
            temp = temp.prev;
        }
        System.out.println();
    }

    void deleteAtHead() throws Exception {
        if(size == 0) throw new Exception("List is Empty");
        head = head.next;
        if(head!=null) head.prev = null;
        size--;
    }

    void deleteAtTail() throws Exception {
        if(size == 0) throw new Exception("List is Empty");
        tail = tail.prev;
        if(tail!=null) tail.next = null;
        size--;
    }

    void insert(int idx, int val) throws Exception {
        if(idx<0 || idx>size) throw new Exception("Index Out of Bound!!!");
        else if(idx == 0) insertAtTail(val);
        else if(idx == size) insertAtTail(val);
        else{
            Node n = new Node(val);
            Node temp = head;
            for(int i=1; i<=idx-1; i++){
                temp = temp.next;
            }

            //Connect n to right node
            n.next = temp.next;
            temp.next.prev = n;

            //Connect n to left node
            temp.next = n;
            n.prev = temp;

            size++;
        }
    }

    void delete(int idx) throws Exception {
        if(idx<0 || idx>=size) throw new Exception("Index Out of Bound!!!");
        else if(idx == 0) deleteAtHead();
        else if(idx == size-1) deleteAtTail();
        else{
            Node temp = head;
            for(int i=1; i<=idx-1; i++){
                temp = temp.next;
            }

            Node del = temp.next;

            temp.next = del.next;
            del.next.prev = temp;

            size--;
        }
    }

    int get(int idx) throws Exception {
        if(idx<0 || idx>=size) throw new Exception("Index Out of Bound!!!");
        else if(idx == 0) return head.data;
        else if(idx == size-1) return tail.data;
        else{
            Node temp = head;
            for(int i=1; i<=idx; i++){
                temp = temp.next;
            }

            return temp.data;
        }
    }

    //Variation of Get by seeing index is near to head or tail
    int get1(int idx) throws Exception {
        if(idx<0 || idx>=size) throw new Exception("Index Out of Bound!!!");
        else if(idx == 0) return head.data;
        else if(idx == size-1) return tail.data;
        else{
            //Logic
            if(idx <= size/2){
                System.out.println("Called From Head");
                //search from head;
                Node temp = head;
                for(int i=1; i<=idx; i++){
                    temp = temp.next;
                }
                return temp.data;
            } else {
                System.out.println("Called From Tail");
                int newidx = size - idx;
                //search from tail
                Node temp = tail;
                for(int i=1; i<=newidx-1; i++){
                    temp = temp.prev;
                }
                return temp.data;
            }
        }
    }
}
public class DoublyLinkedListImplementation {
    public static void main(String[] args) throws Exception {
        DoublyLL dl = new DoublyLL();
        dl.insertAtHead(10);dl.insertAtHead(100);
        dl.insertAtTail(20);dl.insertAtHead(1);
        dl.display();
        dl.displayReverse();
        dl.deleteAtHead();
        dl.deleteAtTail();
        dl.display();
        dl.insert(1, 2000);
        dl.insert(2, 2);
        dl.insert(2, 24);
        dl.display();
        System.out.println(dl.get(1));
        System.out.println(dl.get1(3));
        dl.delete(2);
        dl.display();
    }
}
