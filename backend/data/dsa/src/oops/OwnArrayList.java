package oops;

class MyArrayList{
    private int[] arr;
    private int idx;

    MyArrayList(int capacity){
        arr = new int[capacity];
    }

    void add(int ele){
        if(idx == arr.length) increaseCapacity();
        arr[idx++] = ele;

    }

    void add(int ele, int index){
        if(idx == arr.length) increaseCapacity();
        for(int i=idx;i>index; i--){
            arr[i] = arr[i-1];
        }
        arr[index] = ele;
        idx++;
    }

    void remove(){
        if(idx == 0) {
            System.out.println("Array is Empty!!");
            return;
        }
        idx--;
    }

    void remove(int index){
        if(idx == 0) {
            System.out.println("Array is Empty!!");
            return;
        }
        for(int i = index; i<idx; i++){
            arr[i] = arr[i+1];
        }
        idx--;
    }

    int get(int index){
        if(index<0 || index >= idx){
            System.out.println("Index Out of Bounds!!");
            return -1;
        }
        return arr[index];
    }

    void increaseCapacity() {
        int[] b = new int[arr.length*2];
        /*for(int i=0;i<arr.length;i++){
            b[i] = arr[i];
        }*/
        System.arraycopy(arr, 0, b, 0, arr.length);
        arr = b;
    }

    void display(){
        for(int i=0;i<idx;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    int getCapacity(){
        return arr.length;
    }

    int getSize(){
        return idx;
    }
}



public class OwnArrayList {
    public static void main(String[] args) {
        MyArrayList a = new MyArrayList(3);
        a.remove();
        a.add(10); a.add(20); a.add(30);
        a.display();
        System.out.println("Size - " + a.getSize() + " Capacity - " + a.getCapacity());
        a.add(33);
        a.display();
        System.out.println("Size - " + a.getSize() + " Capacity - " + a.getCapacity());
        a.remove();
        a.display();
        System.out.println(a.get(2));
        a.get(3);
        a.remove(0);
        a.display();
        a.add(55, 0);
        a.display();
        System.out.println("Size - " + a.getSize() + " Capacity - " + a.getCapacity());
    }
}
