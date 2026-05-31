package patterns;

import utility.Input;

public class FlipTriangle {

    /* n = 4
     * * * *
     * * *
     * *
     *
    */
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        /* Way 1
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n+1-i;j++){
                System.out.print("* ");
            }
            System.out.println();
        }
        */

        /* Way 2 */
        int a = n;
        for(int i=1; i<=n; i++){
            for(int j=1; j<=a;j++){
                System.out.print("* ");
            }
            a--;
            System.out.println();
        }
    }
}
