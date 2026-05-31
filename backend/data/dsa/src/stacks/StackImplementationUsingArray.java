package stacks;

class StackArray {
    int[] arr;
    int t;        // top pointer
    int capacity;

    StackArray(int size) {
        capacity = size;
        arr = new int[capacity];
        t = 0;
    }

    // push
    void push(int ele) throws Exception {
        if (t == capacity) {
            throw new Exception("Stack Overflow");
        }
        arr[t++] = ele;
    }

    // pop
    int pop() throws Exception {
        if (t == 0) {
            throw new Exception("Stack Underflow");
        }
        return arr[--t];
    }

    // peek
    int peek() throws Exception {
        if (t == 0) {
            throw new Exception("Stack Empty");
        }
        return arr[t - 1];
    }

    // size
    int size() {
        return t;
    }

    // isEmpty
    boolean isEmpty() {
        return t == 0;
    }
}

public class StackImplementationUsingArray {
    public static void main(String[] args) throws Exception {

        StackArray st = new StackArray(10);

        st.push(10);
        st.push(20);
        st.push(30);

        System.out.println(st.peek()); // 30
        System.out.println(st.pop());  // 30
        System.out.println(st.size()); // 2
    }
}
