package arrays_and_arraylists;

import java.util.Arrays;

public class ShallowAndDeepCopy {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};

        //Shallow Copy
        int[] b = arr; //b is the shallow copy of arr
        // any modification to b will cause modification in arr
        System.out.println(arr[0]);
        b[0] = 20;
        System.out.println(arr[0]); //modifies the value in arr

        //Deep Copy
        //Way 1
        int[] c = Arrays.copyOf(arr, arr.length);
        System.out.println(arr[0]);
        c[0] = 20;
        System.out.println(arr[0]); //does not modify the value in arr

        //Way 2
        int[] d = new int[arr.length];
        for(int i = 0; i<arr.length; i++){
            d[i] = arr[i];
        }

    }
}
