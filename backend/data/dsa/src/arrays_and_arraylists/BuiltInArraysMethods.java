package arrays_and_arraylists;

import java.util.Arrays;

public class BuiltInArraysMethods {
    public static void main(String[] args) {
        int[] arr = {9,4,0,42,5,6,1,22,4,111};
        print(arr);
        Arrays.sort(arr, 2, 8); //sorts between 2 and 7 index
        print(arr);

        int[] arr1 = new int[5];
        Arrays.fill(arr1, 101);
        print(arr1);

    }

    private static void print(int[] arr){
        for(int i: arr) System.out.print(i + " ");
        System.out.println();
    }
}
