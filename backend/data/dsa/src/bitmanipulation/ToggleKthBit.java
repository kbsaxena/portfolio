package bitmanipulation;

public class ToggleKthBit {
    static int toggleKthBit(int n, int k) { //k starts from 0
        return (n ^ (1<<k));
    }
}
