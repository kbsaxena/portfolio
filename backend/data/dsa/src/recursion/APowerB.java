package recursion;

public class APowerB {
    public static void main(String[] args) {
        System.out.println(power(2,5));
        System.out.println(powerInLogarithmicTC(9,9));
    }

    //Tc O(n)
    private static int power(int a, int b) {
        if(a==1 || a==0) return a;
        if(b==0) return 1;
        return a * power(a, b-1);
    }

    //TC O(logb)
    private static int powerInLogarithmicTC(int a, int b) {
        if(a==1 || a==0) return a;
        if(b==0) return a;
        int pow = power(a, b/2);
        if(b % 2 == 0) return pow * pow;
        else return a * pow * pow;
    }
}
