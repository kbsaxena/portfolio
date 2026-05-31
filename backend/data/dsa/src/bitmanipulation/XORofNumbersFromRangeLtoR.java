package bitmanipulation;

public class XORofNumbersFromRangeLtoR {
    //Method 2 Observation TC = O(1)
    public static int findXOR(int l, int r) {
        return xor(l-1) ^ xor(r);
    }

    private static int xor(int n) {
        if(n%4 == 0) return n;
        else if(n%4 == 1) return 1;
        else if(n%4 == 2) return n+1;
        else return 0;
    }

    //Method 1 Looping from l to r TC = O(n)/O(r-l+1)
    public static int findXOR1(int l, int r) {
        int ans = 0;
        for(int i=l;i<=r;i++){
            ans = ans ^ i;
        }
        return ans;
    }
}
