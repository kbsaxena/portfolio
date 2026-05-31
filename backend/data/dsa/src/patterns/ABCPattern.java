package patterns;

import utility.Input;

public class ABCPattern {
    /* If r=4
        a a a a
        B B B B
        c c c c
        D D D D
    */
    public static void main(String[] args) {
        int r = Input.getInt("Enter rows - ");

        for(int i=1; i<=r; i++){
            int diff = (i%2 == 0)?64:96;
            for(int j=1; j<=r;j++){
                System.out.print((char)(i+diff) + " ");
            }
            System.out.println();
        }
    }
}
