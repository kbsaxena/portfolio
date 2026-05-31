package utility;

import java.util.Scanner;

public class Input {
    private static Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    public static int getInt(String text){
        System.out.print(text);
        return sc.nextInt();
    }

    public static double getDouble(String text){
        System.out.print(text);
        return sc.nextDouble();
    }

    public static String getString(String text){
        System.out.print(text);
        return sc.next();
    }
}
