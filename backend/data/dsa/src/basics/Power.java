package basics;

import java.util.Scanner;

public class Power {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number - ");
        int a = sc.nextInt();
        System.out.print("Enter the power - ");
        int b = sc.nextInt();

        boolean isNeg = false;
        if(b<0){
            isNeg = true;
            b = -b;
        }
        double result = 1;
        for(int i = 1; i<=b; i++){
            if(a == 1) break;
            result *= a;
        }

        if(isNeg) result = 1/result;
        if(a==0 && b==0) System.out.println("Not defined");
        else System.out.println(a + " to the power of " + b + " is - " + result);
    }
}
