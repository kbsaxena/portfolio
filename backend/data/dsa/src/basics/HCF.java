package basics;

public class HCF {
    public static void main(String[] args) {
        System.out.println("Hcf is - " + hcf(8,20));
    }

    public static int hcf(int a, int b){
        for(int i=Math.min(a,b); i>=1; i--){
            if(a%i == 0 && b%i == 0) return i;
        }
        return 1;
    }
}
