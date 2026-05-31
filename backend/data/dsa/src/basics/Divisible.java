package basics;

import java.util.Scanner;

public class Divisible {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number - ");
        int n = sc.nextInt();

        if (n%3 == 0 && n%5 == 0) System.out.println("Divisible by 3 and 5");
        else if (n%5 == 0) System.out.println("Only divisible by 5");
        else if (n%3 == 0) System.out.println("Only divisible by 3");
        else System.out.println("Not Divisible by 5 or 3");
    }
}
