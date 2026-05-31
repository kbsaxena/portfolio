package string;

import java.util.ArrayList;

public class AllSubstrings {
    public static void main(String[] args) {
        String s = "abcd";

        for(int i=0; i<s.length();i++){
            for(int j=i; j<s.length(); j++){
                System.out.print(s.substring(i,j+1) + " ");
            }
        }
    }

    public static ArrayList<String> subsetStrings(char arr[]) {
        int n = arr.length;
        ArrayList<String> ans = new ArrayList<>();
        int twoPowerN = (1<<n);
        for(int i=0;i<twoPowerN;i++){
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<n;j++){
                if((i>>j)%2 == 1) sb.append(arr[j]);
            }
            if(sb.length()>0) ans.add(sb.toString());
        }
        return ans;
    }
}
