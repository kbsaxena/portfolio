package bitmanipulation;

public class ClearRightMostSetBit {
    static int clearRightMostSetBit(int n) {
        return (n & (n-1));
    }
}
