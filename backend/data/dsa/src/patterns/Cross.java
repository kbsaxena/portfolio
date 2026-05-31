package patterns;

import utility.Input;

public class Cross {
    /* n = 5
     *       *
       *   *
         *
       *   *
     *       *
     */
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        for(int i=1; i<=n; i++){
            for(int j=1; j<=n;j++){
                if(i == j || i+j == n+1) System.out.print("* ");
                else System.out.print("  ");
            }
            System.out.println();
        }
    }
}
