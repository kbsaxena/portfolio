package string;

public class Strings {
    public static void main(String[] args) {
        String s = " A2b{Cc {B2a";
        System.out.println(isPalindrome(s));
        System.out.println(s.substring(2,2));
    }

    private static boolean isPalindrome(String s) {
        int i=0, j=s.length()-1;

        while(i<j){
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            if(ci == ' ') i++;
            else if(cj == ' ') j--;
            else{
                if(ci<=90 && ci>=65) ci = (char) (ci+32);
                if(cj<=90 && cj>=65) cj = (char) (cj+32);
                if(ci != cj) return false;
                i++;
                j--;
            }
        }
        return true;
    }
}
