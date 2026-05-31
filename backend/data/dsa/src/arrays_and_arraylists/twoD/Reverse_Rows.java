package arrays_and_arraylists.twoD;

public class Reverse_Rows {
    public static void main(String[] args) {
        int[][] arr = {{2,7,4},{8,5,1},{9,6,3}};

        for(int i=0;i<arr.length;i++){
            for(int j=arr[0].length-1;j>=0;j--) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
