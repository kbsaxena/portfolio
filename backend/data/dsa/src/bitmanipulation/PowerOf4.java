package bitmanipulation;

public class PowerOf4 {
    //Method 2
    boolean isPowerOfFour(int n) {
        return isPowerofTwo(n) && (n%3 == 1);
    }

    //Method 1
    boolean isPowerOfFour1(int n) {
        return isPowerofTwo(n) && isPerfectSquare(n);
    }

    private boolean isPerfectSquare(int n) {
        int root = (int)Math.sqrt(n);
        return (root * root == n);
    }

    public static boolean isPowerofTwo(int n) {
        return (n != 0 && (n&(n-1)) == 0);
    }



}
