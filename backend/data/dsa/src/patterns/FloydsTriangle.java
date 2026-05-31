package patterns;

import utility.Input;

public class FloydsTriangle {
    /* n = 4
    1
    2 3
    4 5 6
    7 8 9 10
    */
    public static void main(String[] args) {
        int n = Input.getInt("Enter number - ");
        int count = 1;

        for(int i=1; i<=n; i++){
            for(int j=1; j<=i;j++){
                System.out.print(count++ + " ");
            }
            System.out.println();
        }
    }
}
