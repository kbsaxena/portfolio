package arrays_and_arraylists.twoD;

public class Snake_Pattern_Print_ColumnWise {
    public static void main(String[] args) {
        int[][] arr = {{2,7,4},{8,5,1},{9,6,3}};

        for(int j=0;j<arr[0].length;j++){
            if(j%2==0) {
                for (int i = 0; i < arr.length; i++) {
                    System.out.print(arr[i][j] + " ");
                }
            } else {
                for (int i = arr.length-1;i>=0; i--) {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
