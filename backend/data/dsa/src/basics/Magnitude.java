package basics;

import java.util.Scanner;

public class Magnitude {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number - ");
        int number = sc.nextInt();

        if (number <= 69 && number >= -69) System.out.println("Magnitude is smaller than 69");
        else System.out.println("Magnitude is not smaller than 69");
    }
}
