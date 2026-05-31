package queues;

class CircularQueue{
    //queue is from f to r-1
    private int[] arr;
    private int f;
    private int r;
    private int size;

    CircularQueue(int capacity){
        arr = new int[capacity];
    }

    int size(){
        return size;
    }

    void add(int ele) throws Exception {
        if(size == arr.length) throw new Exception("Queue is Full!!!");
        arr[r++] = ele;
        if(r == arr.length) r = 0;
        size++;
    }
    int remove() throws Exception {
        if(size == 0) throw new Exception("Queue is Empty!!!");
        int peek = arr[f];
        f++;
        if(f == arr.length) f = 0;
        size--;
        return peek;
    }
    int peek() throws Exception {
        if(size == 0) throw new Exception("Queue is Empty!!!");
        return arr[f];
    }
    void display(){
        if(r>f){
            for(int i=f;i<=r-1;i++){
                System.out.print(arr[i] + " ");
            }
        } else{
            for(int i=f;i<arr.length;i++){
                System.out.print(arr[i] + " ");
            }
            for(int i=0;i<=r-1;i++){
                System.out.print(arr[i] + " ");
            }
        }
        System.out.println();
    }
}

public class CircularQueueImplementationUsingArray {
    public static void main(String[] args) throws Exception {
        CircularQueue cq = new CircularQueue(5);
        cq.add(10);cq.add(20);cq.add(30);cq.add(40);cq.add(50);
        cq.display();
        //cq.add(10); // Exception
        cq.remove(); cq.remove();
        cq.add(60);cq.add(70);
        cq.display();
    }
}
