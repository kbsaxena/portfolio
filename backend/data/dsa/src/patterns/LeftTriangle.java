package patterns;

import utility.Input;

public class LeftTriangle {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            for(int j=1; j<=i;j++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
