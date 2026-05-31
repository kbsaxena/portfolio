package queues;
class Node{
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

class LLQueue {
    private Node head;
    private Node tail;
    private int size;

    void add(int ele){
        Node n = new Node(ele);
        if(size == 0) head = tail = n;
        else { //add at tail
            tail.next = n;
            tail = n;
        }
        size++;
    }

    int peek(){
        if(size == 0) throw new RuntimeException("Queue is Empty");
        return head.data;
    }

    int remove(){
        if(size == 0) throw new RuntimeException("Queue is Empty");
        int peek = head.data;
        head = head.next;
        size--;
        return peek;
    }

    void display(){
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
public class LLImplementationOfQueue {
    public static void main(String[] args) {
        LLQueue q = new LLQueue();
        //System.out.println(q.peek()); //Exception
        q.add(10);q.add(20);q.add(30);
        q.display();
        System.out.println("Removed - " + q.remove());
        q.display();
        System.out.println(q.peek());
    }
}
