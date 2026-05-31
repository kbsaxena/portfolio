package searching;

public class SearchIn2DArray {
    public static void main(String[] args) {
        int[][] arr = {{1, 5, 9}, {14, 20, 21}, {30, 34, 43}};
        int x = 14;

        System.out.println(searchMatrix(arr,x));
    }

    public static boolean searchMatrix(int[][] mat, int x) {
        int n = mat.length * mat[0].length;
        int col = mat[0].length;

        int lo = 0, hi = n-1;

        while(lo <= hi){
            int mid = (lo + hi)/2;
            int midRow = mid/col;
            int midCol = mid%col;

            if (mat[midRow][midCol] < x) lo = mid + 1;
            else if (mat[midRow][midCol] > x) hi = mid - 1;
            else return true;
        }

        return false;
    }

    public static boolean searchMatrixUpperBond(int[][] mat, int x) {
        int n = mat.length * mat[0].length;
        int col = mat[0].length;

        int lo = 0, hi = n-1;

        while(lo <= hi){
            int mid = (lo + hi)/2;
            int midRow = mid/col;
            int midCol = mid%col;

            if (mat[midRow][midCol] < x) lo = mid + 1;
            else if (mat[midRow][midCol] > x) hi = mid - 1;
            else return true;
        }

        return false;
    }
}

