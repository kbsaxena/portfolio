package linkedlist;

class Node {
    int data;
    Node next;
    //Added a random Node for a question used in CloneLLWithNextAndRandom
    Node random;

    Node(int data){
        this.data = data;
    }
}

class LL{
    private Node head;
    private Node tail;
    private int size;

    void insertAtTail(int value){ // O(1) Time
        Node n = new Node(value);
        if(size == 0) head = tail = n;
        else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    void insertAtHead(int value){ // O(1) Time
        Node n = new Node(value);
        if(size == 0) head = tail = n;
        else {
            n.next = head;
            head = n;
        }
        size++;
    }

    void insert(int idx, int value) throws Exception {// O(n) Time
        if(idx < 0 || idx > size) throw new Exception("Index Out of Bound!!");

        if(idx == 0) insertAtHead(value);
        else if(idx == size) insertAtTail(value);
        else {
            Node temp = head;
            //Run loop till the previous index
            for (int i = 1; i <= idx - 1; i++) {
                temp = temp.next;
            }

            Node n = new Node(value);
            n.next = temp.next;
            temp.next = n;

            size++;
        }
    }

    void deleteAtHead() throws Exception{ // O(1) Time
        if(size == 0) throw new Exception("LinkedList Empty!!");
        head = head.next;
        size--;
    }

    void delete(int idx) throws Exception { // O(n) Time
        if(idx < 0 || idx >= size) throw new Exception("Index Out of Bound!!");

        Node temp = head;
        //Reach previous index
        for(int i=1;i<=idx-1;i++){
            temp = temp.next;
        }
        temp.next = temp.next.next;
    }

    int get(int idx) throws Exception { // O(n) Time
        if(idx < 0 || idx >= size) throw new Exception("Index Out of Bound!!");

        Node temp = head;
        for(int i=1;i<=idx;i++){
            temp = temp.next;
        }
        return temp.data;
    }

    void display(){ // O(n) Time
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    void set(int idx, int value) throws Exception { // O(n) Time
        if(idx < 0 || idx >= size) throw new Exception("Index Out of Bound!!");

        Node temp = head;
        for(int i=1;i<=idx;i++){
            temp = temp.next;
        }
        temp.data = value;
    }

    int size(){ // O(1) Time
        return size;
    }
}
public class OwnLinkedList {
    public static void main(String[] args) throws Exception {
        LL list = new LL();
        //list.deleteAtHead(); will give exception
        list.insertAtTail(10);
        list.insertAtTail(1);
        list.display();
        System.out.println("Size of LinkedList is - " + list.size());
        list.insertAtHead(90);
        list.display();
        System.out.println("Size of LinkedList is - " + list.size());
        list.deleteAtHead();
        list.display();
        System.out.println("Size of LinkedList is - " + list.size());
        list.insert(0, 100);
        list.insert(3, 200);
        list.insert(2, -1);
        list.display();
        System.out.println(list.get(3));
        //System.out.println(list.get(5)); Exception
        list.delete(2);
        list.display();
        list.set(2, 1000);
        list.display();
    }
}
