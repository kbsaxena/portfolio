package recursion;

public class WaysToReachTheNthStair {
    int countWays(int n) {
        return solve(n);
    }

    int solve(int n) {
        if (n == 0 || n == 1) return 1;
        return solve(n - 1) + solve(n - 2);
    }
}
