package arrays_and_arraylists.twoD;

public class Row_With_Max_Sum {
    public static void main(String[] args) {
        int[][] arr = {{2,6,8},{8,5,1},{9,7,3}};

        int maxSum = Integer.MIN_VALUE;
        int row = -1;
        for(int i=0;i<arr.length;i++){
            int rowSum = 0;
            for(int j=0;j<arr[0].length;j++) {
                rowSum += arr[i][j];
            }
            if(rowSum>maxSum){
                maxSum = rowSum;
                row = i;
            }
        }

        System.out.println("Out of all rows, row " + row + " has the maximum sum = " + maxSum);

    }
}
