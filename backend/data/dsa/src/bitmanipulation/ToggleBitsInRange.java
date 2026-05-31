package bitmanipulation;

public class ToggleBitsInRange {
    static int toggleBits(int n, int l, int r) { // index starts from 1 not 0
        for(int i = l; i <= r; i++){
            n = n ^ (1 << (i - 1));
        }
        return n;
    }
}
