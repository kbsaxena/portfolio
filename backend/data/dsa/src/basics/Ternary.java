package basics;

import java.util.Scanner;

public class Ternary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number - ");
        int a = sc.nextInt();

        int b = (a>=0)?100:99;
        System.out.println(b);
    }
}
