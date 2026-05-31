package basics;

import java.util.Scanner;

public class IntegerOrNot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double d = sc.nextDouble();
        int x = (int) d; //typecast

        if (d == x)
            System.out.println("Number is Integer");
        else
            System.out.println("Number is not Integer");
    }
}
