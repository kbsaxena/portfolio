package patterns.nst_nsp;

import utility.Input;

public class Rhombus {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        int nsp = n-1, nst = 4;
        for(int i=1; i<=n; i++){
            for(int j=1; j<=nsp;j++){
                System.out.print("  ");
            }
            for(int j=1; j<=nst;j++){
                System.out.print("* ");
            }

            nsp--;
            System.out.println();
        }
    }
}
