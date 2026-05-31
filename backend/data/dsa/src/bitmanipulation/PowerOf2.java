package bitmanipulation;

public class PowerOf2 {
    //Using Bitwise AND
    public static boolean isPowerofTwo(int n) {
        return (n != 0 && (n&(n-1)) == 0);
    }
    //Method 1 Recursion TC = O(logn)
    public static boolean isPowerofTwo1(int n) {
        if(n==1) return true;
        if(n==0 || n%2 == 1) return false;
        return isPowerofTwo1(n/2);
    }
}
