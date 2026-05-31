package arrays_and_arraylists.twoD;

public class Snake_Pattern_Print {
    public static void main(String[] args) {
        int[][] arr = {{2,7,4},{8,5,1},{9,6,3}};

        for(int i=0;i<arr.length;i++){
            if(i%2==0){
                for(int j=0;j<arr[0].length;j++) {
                    System.out.print(arr[i][j] + " ");
                }
            } else {
                for(int j=arr[0].length-1;j>=0;j--) {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
