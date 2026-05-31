package patterns;

import utility.Input;

public class Pyramid {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            //2 Patterns in inner loop
            for(int j=1; j<=n-i;j++){
                System.out.print("  ");
            }
            for(int j=1; j<=2*i-1;j++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
