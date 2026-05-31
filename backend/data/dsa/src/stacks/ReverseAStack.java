package stacks;

import java.util.Stack;

public class ReverseAStack {
    public static void main(String[] args) {
        Stack<Integer> main = new Stack<>();
        main.push(1);main.push(2);main.push(3);
        System.out.println(main);

        Stack<Integer> st1 = new Stack<>();
        while(main.size()>0){
            st1.push(main.pop());
        }

        Stack<Integer> st2 = new Stack<>();
        while(st1.size()>0){
            st2.push(st1.pop());
        }

        while(st2.size()>0){
            main.push(st2.pop());
        }

        System.out.println(main);
    }
}
