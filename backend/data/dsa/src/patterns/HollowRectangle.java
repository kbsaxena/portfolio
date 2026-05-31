package patterns;

import utility.Input;

public class HollowRectangle {
    /* 5X3
     * * * * *
     *       *
     *       *
     *       *
     * * * * *
    */
    public static void main(String[] args) {
        int r = Input.getInt("Enter rows - ");
        int c = Input.getInt("Enter cols - ");

        for(int i=1; i<=r; i++){
            for(int j=1; j<=c;j++){
                if(i==1 || j==1 || i==r || j ==c) System.out.print("* ");
                else System.out.print("  ");
            }
            System.out.println();
        }
    }
}
