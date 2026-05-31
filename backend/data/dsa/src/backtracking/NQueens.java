package backtracking;

public class NQueens {
    public static void main(String[] args) {
        int n = 4;
        char[][] board = new char[n][n]; //null characters
        placeQueens(0, board);
    }

    private static void placeQueens(int row, char[][] board) {
        int n = board.length;
        if(row == n) { //print the config
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            return;
        }
        for(int j=0;j<n;j++){
            if(canBePlaced(row,j, board)){
                board[row][j] = 'Q';
                placeQueens(row+1, board);
                board[row][j] = '\0'; //backtracking
            }
        }
    }

    private static boolean canBePlaced(int row, int col, char[][] board) {
        int n = board.length;
        int i,j;
        //Travelling UP to see no queen is present
        i = row-1;
        j = col;
        while(i>=0){
            if(board[i][j] == 'Q') return false;
            i--;
        }

        //Travelling Diagonally Right UP to see no queen is present
        i = row-1;
        j = col+1;
        while(i>=0 && j<n){
            if(board[i][j] == 'Q') return false;
            i--;
            j++;
        }

        //Travelling Diagonally Left UP to see no queen is present
        i = row-1;
        j = col-1;
        while(i>=0 && j>=0){
            if(board[i][j] == 'Q') return false;
            i--;
            j--;
        }
        return true;
    }
}
