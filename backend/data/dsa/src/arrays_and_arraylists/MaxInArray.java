package arrays_and_arraylists;

public class MaxInArray {
    public static void main(String[] args) {
        int[] arr = {3,5,9,1,0,90,105};
        int max = Integer.MIN_VALUE;

        for (int j : arr) {
            max = Math.max(max, j);
        }

        System.out.println("Max in array is - " + max);
    }
}
