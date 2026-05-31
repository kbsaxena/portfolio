package patterns.using_min;

import utility.Input;

public class Numbers {
    /* n = 5
    1 1 1 1 1
    1 2 2 2 2
    1 2 3 3 3
    1 2 3 4 4
    1 2 3 4 5
     */

    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                // Way 1
                //System.out.print(((j>i)?i:j) + " ");

                // Way 2
                System.out.print((Math.min(j, i)) + " ");
            }
            System.out.println();
        }
    }
}
