package basics;

import java.util.Scanner;

public class SidesOfTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter side 1 - ");
        int n1 = sc.nextInt();
        System.out.print("Enter side 2 - ");
        int n2 = sc.nextInt();
        System.out.print("Enter side 3 - ");
        int n3 = sc.nextInt();

        if(n1+n2>n3 && n2+n3>n1 && n3+n1>n2) System.out.println("Valid triangle");
        else System.out.println("Invalid triangle");
    }
}
