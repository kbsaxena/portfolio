package graphs;

import java.util.LinkedList;
import java.util.Queue;

public class NumberOfIslands {
    static int rows;
    static int cols;
    public class Pair{
        int i;
        int j;
        Pair(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
    public int numIslands(char[][] grid) {
        rows = grid.length;
        cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    bfs(i, j, visited, grid);
                    count++;
                }
            }
        }
        return count;

    }

    private void bfs(int i, int j, boolean[][] visited, char[][] grid) {
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(i, j));
        visited[i][j] = true;

        while (q.size() > 0) {
            Pair front = q.remove();
            int x = front.i;
            int y = front.j;

            //Up
            if(x-1>=0 && grid[x-1][y] == '1' && !visited[x-1][y]){
                q.add(new Pair(x-1,y));
                visited[x-1][y] = true;
            }

            //Down
            if(x+1<rows && grid[x+1][y] == '1' && !visited[x+1][y]){
                q.add(new Pair(x+1,y));
                visited[x+1][y] = true;
            }

            //Left
            if(y-1>=0 && grid[x][y-1] == '1' && !visited[x][y-1]){
                q.add(new Pair(x,y-1));
                visited[x][y-1] = true;
            }

            //Right
            if(y+1<cols && grid[x][y+1] == '1' && !visited[x][y+1]){
                q.add(new Pair(x,y+1));
                visited[x][y+1] = true;
            }
        }

    }
}
