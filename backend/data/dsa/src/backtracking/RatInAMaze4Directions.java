package backtracking;

public class RatInAMaze4Directions {
    public static void main(String[] args) {
        int m = 3, n = 3;
        boolean[][] isVisited = new boolean[m][n];
        ways(0, 0, m-1, n-1, isVisited, "");
    }

    private static void ways(int row, int col, int x, int y, boolean[][] isVisited, String s) {
        if(row==x && col==y){
            System.out.println(s);
            return;
        }
        if(row<0 || col<0 || row>x || col>y || isVisited[row][col]) return;
        isVisited[row][col] = true;
        ways(row-1,col,x,y,isVisited,s+'U'); //UP
        ways(row,col-1,x,y,isVisited,s+'L'); //LEFT
        ways(row,col+1,x,y,isVisited,s+'R'); //RIGHT
        ways(row+1,col,x,y,isVisited,s+'D'); //DOWN
        isVisited[row][col] = false; //Backtracking
    }
}
