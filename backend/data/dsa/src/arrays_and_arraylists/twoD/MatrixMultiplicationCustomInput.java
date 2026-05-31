package arrays_and_arraylists.twoD;

import utility.Input;

public class MatrixMultiplicationCustomInput {
    public static void main(String[] args) {
        int m = Input.getInt("Enter number of rows for matrix 1 - ");
        int n = Input.getInt("Enter number of columns for matrix 1 - ");
        int p = Input.getInt("Enter number of rows for matrix 2 - ");
        int q = Input.getInt("Enter number of columns for matrix 2 - ");

        if(n != p) System.out.println("Matrix Cant be Multiplied!!!");
        else{
            int[][] mat1 = generateMatrix(m, n);
            printMatrix(mat1);

            int[][] mat2 = generateMatrix(p, q);
            printMatrix(mat2);

            printMatrix(multiply(mat1, mat2));
        }
    }

    public static int[][] multiply(int[][] mat1, int[][] mat2) {
        int row = mat1.length;
        int col = mat2[0].length;

        int[][] c = new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                for(int k=0; k<mat1[0].length; k++){
                    c[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }

        return c;
    }

    public static int[][] generateMatrix(int rows, int columns){
        System.out.print("Enter Matrix data space separated - ");
        int[][] mat = new int[rows][columns];
        for(int i=0; i<rows; i++){
            for(int j=0; j<columns; j++){
                mat[i][j] = Input.getInt("");
            }
        }
        return mat;
    }

    public static void printMatrix(int[][] mat){
        System.out.println("Matrix Looks Like - ");
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[0].length; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }
}
