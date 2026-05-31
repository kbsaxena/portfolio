package string;

public class ReverseEachWord {
    public static void main(String[] args) {
        String s = "Hello World In Java";
        StringBuilder sb = new StringBuilder(s);

        reverseEachWord(sb);

        System.out.println(sb.toString());
    }

    private static void reverseEachWord(StringBuilder sb) {
        int i=0,j=0;
        while(j<sb.length()){
            if(sb.charAt(j) != ' ') j++;
            else {
                reversePart(sb, i, j-1);
                j++;
                i = j;
            }
        }
        reversePart(sb, i, j-1);
    }

    private static void reversePart(StringBuilder sb, int i, int j) {
        while(i<j){
            char ci = sb.charAt(i);
            char cj = sb.charAt(j);

            sb.setCharAt(i, cj);
            sb.setCharAt(j, ci);

            i++;
            j--;
        }
    }

}
