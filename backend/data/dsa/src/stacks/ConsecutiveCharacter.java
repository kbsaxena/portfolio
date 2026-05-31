package stacks;

import java.util.Stack;

public class ConsecutiveCharacter {

    public String removeDuplicates(String s) {
        //aaaabbccaa -> abca
        //Using Stack
        Stack<Character> st = new Stack<>();
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(st.size()==0 || st.peek() != c) st.push(c);
        }

        StringBuilder ans = new StringBuilder();
        while(st.size()>0){
            ans.append(st.pop());
        }

        return ans.reverse().toString();
    }

    public String removeDuplicates1(String s) {
        //aaaabbccaa -> abca
        //Using Sliding Window
        StringBuilder ans = new StringBuilder();
        int i = 0, j = 0;
        while(j<s.length()){
            if(s.charAt(i) == s.charAt(j)) j++;
            else{
                ans.append(s.charAt(i));
                i = j;
            }
        }
        ans.append(s.charAt(i));

        return ans.toString();
    }
}
