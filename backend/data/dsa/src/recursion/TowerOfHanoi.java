package recursion;

public class TowerOfHanoi {
    public static void main(String[] args) {
        hanoi(3, 'A', 'C', 'B');
    }

    private static void hanoi(int n, char a, char c, char b) {
        if(n==0) return;
        hanoi(n-1, a, b, c);
        System.out.println(a + " -> " + c);
        hanoi(n-1, b, c, a);
    }
}
