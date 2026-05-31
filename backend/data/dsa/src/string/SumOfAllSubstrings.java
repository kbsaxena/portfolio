package string;

public class SumOfAllSubstrings {
    public static void main(String[] args) {
        String s = "12";
        int sum = 0;
        for(int i=0; i<s.length();i++){
            for(int j=i; j<s.length(); j++){
                sum += Integer.parseInt(s.substring(i,j+1));
            }
        }
        System.out.println("Total Sum - " + sum);
    }
}
