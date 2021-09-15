import java.util.*; 
import java.io.*;   
public final class GameOfLife2 {

    public final static int DEAD    = 0x00;
    public final static int LIVE    = 0x01;
    
    public final static void main(String[] args) {

        GameOfLife2 gof = new GameOfLife2(); 
        gof.test(20);
        
    }

    private void test(int nrIterations) {
        
        int[][] board = {{DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD, DEAD, DEAD, LIVE, LIVE, DEAD}
                         }; 
        
        
        printBoard(board);

        for (int i = 0 ; i < nrIterations ; i++) {
            System.out.println();
            board = getNextBoard(board);            
        }      

        printBoard(board);
    }

    private void printBoard(int[][] board) {

        for (int i = 0, e = board.length ; i < e ; i++) {

            for (int j = 0, f = board[i].length ; j < f ; j++) {
                System.out.print(Integer.toString(board[i][j]) + ",");
            } 
            System.out.println();
        }
    }

    public int[][] getNextBoard(int[][] board) {

        if (board.length == 0 || board[0].length == 0) {
            throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
        }

        int nrRows = board.length;
        int nrCols = board[0].length;

        int[][] buf = new int[nrRows][nrCols];

        for (int row = 0 ; row < nrRows ; row++) {
	
            for (int col = 0 ; col < nrCols ; col++) {
                buf[row][col] = getNewCellState(board[row][col], getLiveNeighbours(row, col, board));
            }
        }   
        return buf;
    }

    
    private int getLiveNeighbours(int cellRow, int cellCol, int[][] board) {

        int liveNeighbours = 0;
        int rowEnd = Math.min(board.length , cellRow + 2);
        int colEnd = Math.min(board[0].length, cellCol + 2);

        for (int row = Math.max(0, cellRow - 1) ; row < rowEnd ; row++) {
            
            for (int col = Math.max(0, cellCol - 1) ; col < colEnd ; col++) {
                
                if ((row != cellRow || col != cellCol) && board[row][col] == LIVE) {
                    liveNeighbours++;
                }
            }
        }
        return liveNeighbours;
    }

    private int getNewCellState(int curState, int liveNeighbours) {

        int newState = curState;

        switch (curState) {
        case LIVE:

            if (liveNeighbours < 2) {
                newState = DEAD;
            }

            if (liveNeighbours == 2 || liveNeighbours == 3) {
                newState = LIVE;
            }

            if (liveNeighbours > 3) {
                newState = DEAD;
            }
            break;

        case DEAD:
            if (liveNeighbours == 3) {
                newState = LIVE;
            }
            break;

        default:
            throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
        }			
        return newState;
    }
}
