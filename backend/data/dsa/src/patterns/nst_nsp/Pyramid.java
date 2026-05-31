package patterns.nst_nsp;

import utility.Input;

public class Pyramid {
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");

        int nsp = n-1, nst = 1;
        for(int i=1; i<=n; i++){
            for(int j=1; j<=nsp;j++){
                System.out.print("  ");
            }
            for(int j=1; j<=nst;j++){
                System.out.print("* ");
            }

            nsp--;
            nst += 2;
            System.out.println();
        }
    }
}
