package recursion;

public class RecursionOnArray {
    public static void main(String[] args) {
        int[] arr = {1,2,3,10,4,5};
        System.out.println(sum(arr,0));
        System.out.println(max(arr,0));
        System.out.println(min(arr,0));
        System.out.println(product(arr,0));
    }

    private static int sum(int[] arr, int i) {
        if(i==arr.length) return 0;
        return arr[i] + sum(arr, i+1);
    }

    private static int max(int[] arr, int i) {
        if(i==arr.length) return Integer.MIN_VALUE;
        return Math.max(arr[i], max(arr, i+1));
    }

    private static int min(int[] arr, int i) {
        if(i==arr.length) return Integer.MAX_VALUE;
        return Math.min(arr[i], max(arr, i+1));
    }

    private static int product(int[] arr, int i) {
        if(i==arr.length) return 1;
        return arr[i] * product(arr, i+1);
    }

}
