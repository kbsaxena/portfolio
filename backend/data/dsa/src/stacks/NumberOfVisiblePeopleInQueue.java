package stacks;

import java.util.Stack;

public class NumberOfVisiblePeopleInQueue {
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        ans[n-1] = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(heights[n-1]);
        for(int i=n-2; i>=0; i--){
            int count = 0;
            while(stack.size()>0 && stack.peek()<heights[i]){
                count++;
                stack.pop();
            }

            if(stack.size()>0) count++; //Very important

            ans[i] = count;
            stack.push(heights[i]);
        }

        return ans;
     }
}
