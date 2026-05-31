package arrays_and_arraylists.twoD;

public class Searchin2DArray {
    public static void main(String[] args) {

    }

    public boolean searchMatrix(int[][] arr, int x) {
        int m = arr.length, n = arr[0].length;
        int i = 0, j = n-1;
        while(i<m && j>=0){
            if(arr[i][j]>x) j--; //Go Left
            else if(arr[i][j]<x) i++; //Go Down
            else return true;
        }
        return false;
    }
}
