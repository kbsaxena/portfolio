package basics;

import java.util.Scanner;

public class Rectangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Length of the Rectangle : ");
        int length = sc.nextInt();
        System.out.print("Enter Breadth of the Rectangle : ");
        int breadth = sc.nextInt();

        int perimeter = 2 * (length+breadth);
        int area = length * breadth;

        if (area > perimeter)
            System.out.println("Area of rectangle is greater than the perimeter");
        else
            System.out.println("Perimeter of rectangle is greater than the Area");
    }
}
