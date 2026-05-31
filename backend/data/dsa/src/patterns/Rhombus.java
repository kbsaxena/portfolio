package patterns;

import utility.Input;

public class Rhombus {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            //2 Patterns in inner loop (flip + square)
            for(int j=1; j<=n-i;j++){ // Flip
                System.out.print("  ");
            }
            for(int j=1; j<=n;j++){ // Square
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
