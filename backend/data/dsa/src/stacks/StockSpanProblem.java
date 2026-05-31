package stacks;

import java.util.ArrayList;
import java.util.Stack;

public class StockSpanProblem {
    public ArrayList<Integer> calculateSpan(int[] arr) {
        // code here
        int n = arr.length;
        int[] pge = new int[n];

        pge[0] = -1;
        Stack<Integer> st = new Stack<>(); //arr elements ke indices
        st.push(0);

        for(int i = 1; i<n; i++){
            while(st.size()>0 && arr[st.peek()] <= arr[i]) st.pop();
            if(st.size() == 0) pge[i] = -1;
            else pge[i] = st.peek();
            st.push(i);
        }

        ArrayList<Integer> list = new ArrayList<>();

        for(int i=0;i<n;i++){
            list.add(i-pge[i]); //Span
        }

        return list;
    }
}
