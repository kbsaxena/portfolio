package string;

public class ToggleCase {
    public static void main(String[] args) {
        System.out.println(toggleCase("AoB"));
    }

    public static String toggleCase(String s) {
        StringBuilder sb = new StringBuilder(s);
        for(int i=0; i<sb.length();i++){
            char ch = sb.charAt(i);
            if(ch >=65 && ch<=90){ //Capital hai
                char lower = (char)(ch+32);
                sb.setCharAt(i, lower);
            } else if(ch >=97 && ch<=122){ //Small hai
                char upper = (char)(ch-32);
                sb.setCharAt(i, upper);
            }
        }
        return sb.toString();
    }
}
