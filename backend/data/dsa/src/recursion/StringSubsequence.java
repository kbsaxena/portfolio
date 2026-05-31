package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StringSubsequence {
    public static void main(String[] args) {
        String s = "abc";
        ArrayList<String> subsets = new ArrayList<>();
        subsets(s, "", 0, subsets);
        System.out.println(subsets);
        //Collections.sort(subsets);
        //System.out.println(subsets);
    }

    private static void subsets(String s, String ans, int i, ArrayList<String> subsets) {
        if(i==s.length()){
            //System.out.print(ans + " ");
            subsets.add(ans);
            return;
        }
        char ch = s.charAt(i);
        //subsets(s, ans+ch, i+1); //Pick ch
        //subsets(s, ans, i+1);        //Skip ch

        subsets(s, ans+ch, i+1, subsets); //Pick ch
        subsets(s, ans, i+1, subsets);        //Skip ch
    }
}
