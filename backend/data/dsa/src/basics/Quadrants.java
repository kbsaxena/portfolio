package basics;

import java.util.Scanner;

public class Quadrants {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter x value - ");
        int x = sc.nextInt();
        System.out.print("Enter y value - ");
        int y = sc.nextInt();

        if (x == 0 && y == 0) System.out.println("Its at Origin");
        if (x == 0 && y != 0) System.out.println("Its at Y axis");
        if (x != 0 && y == 0) System.out.println("Its at X axis");
        if (x > 0 && y > 0) System.out.println("Its at 1st Quadrant");
        if (x < 0 && y > 0) System.out.println("Its at 2nd Quadrant");
        if (x < 0 && y < 0) System.out.println("Its at 3rd Quadrant");
        if (x > 0 && y < 0) System.out.println("Its at 4th Quadrant");
    }
}
