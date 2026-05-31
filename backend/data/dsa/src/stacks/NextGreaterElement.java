package stacks;

import java.util.*;
import java.util.stream.Collectors;

public class NextGreaterElement {
    public ArrayList<Integer> nextLargerElement(int[] arr) {
        // code here
        int n = arr.length;
        int[] nge = new int[n];
        nge[n-1] = -1;

        Stack<Integer> st = new Stack<>(); //arr elements ke indices
        st.push(n-1);

        for(int i = n-2; i>=0 ;i--){
            while(st.size()>0 && arr[st.peek()] <= arr[i]) st.pop();
            if(st.size() == 0) nge[i] = -1;
            else nge[i] = arr[st.peek()];
            st.push(i);
        }

        ArrayList<Integer> list = new ArrayList<>();

        for(int ele : nge){
            list.add(ele);
        }

        return list;
    }
}
