package patterns;

import utility.Input;

public class VerticallyFlippedTriangle {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        /* Way 1
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n;j++){
                System.out.print((i+j)>n?"* ":"  ");
            }
            System.out.println();
        }
        */

        for(int i=1; i<=n; i++){
            for(int j=1; j<=n-i;j++){
                System.out.print("  ");
            }

            for(int j=1; j<=i;j++){
                System.out.print(j + " ");
            }

            System.out.println();
        }



    }
}
