package basics;

import java.util.Scanner;

public class SumOfDigits {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Number - ");
        int n = sc.nextInt();

        int reverse = 0 ;
        int sum = 0;
        while(n != 0){
            reverse = (reverse * 10) + (n % 10);
            sum += n%10;
            n /= 10;
        }

        System.out.println("Reverse Number is - " + reverse);
        System.out.print("Sum of digits is - " + sum);
    }
}
