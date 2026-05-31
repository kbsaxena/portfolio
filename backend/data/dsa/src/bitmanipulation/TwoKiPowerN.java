package bitmanipulation;

public class TwoKiPowerN {
    public static void main(String[] args) {
        System.out.println(powerOfTwo(4));
    }
    //Using Left Shift O(1)
    public static long powerOfTwo(int n){
        return 1<<n;
    }

    //Using Recursion O(log n)
    public static long powerOfTwo1(int n){
        if(n == 0) return 1;
        long half = powerOfTwo1(n / 2);
        if(n % 2 == 0) return half * half;
        else return 2 * half * half;
    }
}
