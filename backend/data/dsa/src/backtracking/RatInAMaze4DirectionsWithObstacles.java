package backtracking;

import java.util.ArrayList;
import java.util.Collections;

public class RatInAMaze4DirectionsWithObstacles {
    public ArrayList<String> ratInMaze(int[][] maze) {
        ArrayList<String> ans = new ArrayList<>();
        int n = maze.length;
        ways(0, 0, n-1, n-1, maze, "", ans);
        Collections.sort(ans);
        return ans;
    }

    private static void ways(int row, int col, int x, int y, int[][] maze, String s, ArrayList<String> ans) {
        if(row==x && col==y){
            ans.add(s);
            return;
        }
        if(row<0 || col<0 || row>x || col>y || maze[row][col]<=0) return;
        maze[row][col] = -1;
        ways(row-1,col,x,y,maze,s+'U',ans); //UP
        ways(row,col-1,x,y,maze,s+'L',ans); //LEFT
        ways(row,col+1,x,y,maze,s+'R',ans); //RIGHT
        ways(row+1,col,x,y,maze,s+'D',ans); //DOWN
        maze[row][col] = 1; //Backtracking
    }
}
