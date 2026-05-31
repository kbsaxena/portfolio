package arrays_and_arraylists.twoD;

public class RotateClockwise90 {
    public static void main(String[] args) {
        int[][] arr = {{2,7,4},{8,5,1},{9,6,3}};
        print(arr);

        // Transpose - Swap i,j element with j,i in same array
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<i;j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }
        System.out.println("After Transposing");
        print(arr);

        for(int i=0;i<arr.length;i++){
            for(int j=arr[0].length-1, k=0;k<j;j--,k++) {
                int temp = arr[i][k];
                arr[i][k] = arr[i][j];
                arr[i][j] = temp;
            }
        }
        System.out.println("After reversing each row");
        print(arr);
    }

    private static void print(int[][] arr){
        for(int[] row: arr){
            for(int ele: row){
                System.out.print(ele + " ");
            }
            System.out.println();
        }
        System.out.println("*********");
    }
}
