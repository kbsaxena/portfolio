package arrays_and_arraylists.twoD;

public class Min_Out_of_Max_Each_Row {
    public static void main(String[] args) {
        int[][] arr = {{2,7,4},{8,5,1},{9,6,3}};

        int min = Integer.MAX_VALUE;
        for(int i=0;i<arr.length;i++){
            int rowMax = 0;
            for(int j=0;j<arr[0].length;j++) {
                rowMax = Math.max(rowMax, arr[i][j]);
            }
            min = Math.min(min,rowMax);
        }

        System.out.println("Out of the maximum values from each row, the minimum is "
                + min);

    }
}
