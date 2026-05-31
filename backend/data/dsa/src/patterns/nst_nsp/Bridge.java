package patterns.nst_nsp;

import utility.Input;

public class Bridge {
    /*
     -------------
     * * * * * * *
     * * *   * * *
     * *       * *
     *           *
    --------------
    */

    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");
        //Upper Bridge
        for(int i=1; i<=(2*n)+1;i++) System.out.print("* ");
        System.out.println();

        //Lower Bridge
        int nsp = 1, nst = n;
        for(int i=1; i<=n; i++){
            for(int j=1; j<=nst;j++){
                System.out.print("* ");
            }
            for(int j=1; j<=nsp;j++){
                System.out.print("  ");
            }
            for(int j=1; j<=nst;j++){
                System.out.print("* ");
            }

            nsp += 2;
            nst -= 1;
            System.out.println();
        }
    }
}
