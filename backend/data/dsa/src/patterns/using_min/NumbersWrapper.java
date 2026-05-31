package patterns.using_min;

import utility.Input;

public class NumbersWrapper {
    /* n = 4 //4 wrapped by 3 and so on
    1 1 1 1 1 1 1
    1 2 2 2 2 2 1
    1 2 3 3 3 2 1
    1 2 3 4 3 2 1
    1 2 3 3 3 2 1
    1 2 2 2 2 2 1
    1 1 1 1 1 1 1
     */

    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");
        for(int i=1; i<=2*n-1; i++){
            for(int j=1; j<=2*n-1; j++){
                int a = i, b = j;
                if (i>n) a = 2*n - i;
                if (j>n) b = 2*n - j;

                //Using ternary
                //int a = (i>n)? 2*n - i: i, b = (j>n)?2*n - j: j;

                System.out.print((Math.min(a, b)) + " ");
            }
            System.out.println();
        }
    }
}
