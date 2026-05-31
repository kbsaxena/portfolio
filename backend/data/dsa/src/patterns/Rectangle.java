package patterns;

import utility.Input;

public class Rectangle {
    public static void main(String[] args) {
        int r = Input.getInt("Enter rows - ");
        int c = Input.getInt("Enter cols - ");

        for(int i=1; i<=r; i++){
            for(int j=1; j<=c;j++){
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
