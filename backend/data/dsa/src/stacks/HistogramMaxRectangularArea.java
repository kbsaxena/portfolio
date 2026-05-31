package stacks;

import java.util.Stack;

public class HistogramMaxRectangularArea {
    //TC = O(n)
    public static int getMaxArea(int arr[]) {
        // code here
        int n = arr.length;
        //next smaller element
        int[] nse = new int[n];
        nse[n-1] = n; //ye wo -1 hai to the right
        Stack<Integer> st = new Stack<>();
        st.push(n-1);

        for(int i=n-2;i>=0;i--){
            while(st.size()>0 && arr[st.peek()]>=arr[i]) st.pop();
            if(st.size() == 0) nse[i] = n; //ye wo -1 hai to the right
            else nse[i] = st.peek();
            st.push(i);
        }

        //previous smaller element
        int[] pse = new int[n];
        pse[0] = -1; //ye wo -1 hai to the left
        while(st.size()>0) st.pop(); //empty Stack or create new stack
        st.push(0);

        for(int i=1;i<n;i++){
            while(st.size()>0 && arr[st.peek()]>=arr[i]) st.pop();
            if(st.size() == 0) pse[i] = -1; //ye wo -1 hai to the left
            else pse[i] = st.peek();
            st.push(i);
        }
        int maxArea = 0;
        for(int i=0; i<n;i++){
            int area = arr[i] * (nse[i]-pse[i]-1);
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
