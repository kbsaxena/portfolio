package arrays_and_arraylists;

public class MinInArray {
    public static void main(String[] args) {
        int[] arr = {3,5,9,1,0,90,105};
        int min = Integer.MAX_VALUE;

        for (int j : arr) {
            min = Math.min(min, j);
        }

        System.out.println("Min in array is - " + min);
    }
}
