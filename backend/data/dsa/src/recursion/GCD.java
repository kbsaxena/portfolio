package recursion;

public class GCD {
    public static void main(String[] args) {
        System.out.println(gcd1(20,28));
        System.out.println(gcd2(60,36));
    }

    //TC = O(log(min(a,b)))
    private static int gcd1(int a, int b) {
        if(a==0) return b;
        return gcd1(b%a, a);
    }

    //TC = O(log(min(a,b)))
    private static int gcd2(int a, int b) {
        if(b==0) return a;
        return gcd2(b, a%b);
    }
}
