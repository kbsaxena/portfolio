package patterns;

import utility.Input;

public class BinaryTriangle {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            for(int j=1; j<=i;j++){
                /* Way 1
                //If i and j both are odd or both are even print 1
                if((i%2 == 0 && j%2 == 0) || (i%2 == 1 && j%2 == 1))
                    System.out.print(1 + " ");
                else System.out.print(0 + " ");
                */

                // Way 2 - Sum of i and j is even print 1
                System.out.print(((i+j) % 2 == 0?1:0) + " ");

            }
            System.out.println();
        }
    }
}
