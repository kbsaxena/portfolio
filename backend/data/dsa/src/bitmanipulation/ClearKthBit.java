package bitmanipulation;

public class ClearKthBit {
    static int setKthBit(int n, int k) {
        int mask = ~(1<<k); //1's Compliment of mask
        return n & mask ;
    }
}
