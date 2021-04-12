//Program to implement Hill Climbing with random restart to solve N-queens problem
import java.util.Random;


public class HillClimbingSearch implements Runnable {
    private int n ;
    private int heuristic = 0;
    private int presentHeuristic;
    private NQueen[] finalSolution;
    
    public  HillClimbingSearch (int size) {
        n = size;	
        finalSolution = null;
    }
    
    public NQueen[] getFinalSolution() {
        return finalSolution;
    }

    //Method to create a new random board
    public NQueen[] generateBoard() {
        NQueen[] startBoard = new NQueen[n];
        Random rndm = new Random();
        for(int i=0; i<n; i++){
            startBoard[i] = new NQueen(rndm.nextInt(n), i);
        }
        return startBoard;
    }

    //Method to print the Current State
    public  void printState (NQueen[] state) {
        //Creating temporary board from the present board
        int[][] tempBoard = new int[n][n];
        for (int i=0; i<n; i++) {
            //Get the positions of Queen from the Present board and set those positions as 1 in temp board
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
        System.out.println();
        for (int i=0; i<n; i++) {
            for (int j= 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to find Heuristics of a state
    public  int findHeuristic (NQueen[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].ifConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    // Method to get the next board with lower heuristic
    public NQueen[] nextBoard (NQueen[] presentBoard) {
        NQueen[] nextBoard = new NQueen[n];
        NQueen[] tmpBoard = new NQueen[n];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<n; i++) {
            //  Copy present board as best board and temp board
            nextBoard[i] = new NQueen(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        //  Iterate each column
        for (int i=0; i<n; i++) {
            if (i>0)
                tmpBoard[i-1] = new NQueen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new NQueen (0, tmpBoard[i].getColumn());
            //  Iterate each row
            for (int j=0; j<n; j++) {
                //Get the heuristic
                tempH = findHeuristic(tmpBoard);
                //Check if temp board better than best board
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                    //  Copy the temp board as best board
                    for (int k=0; k<n; k++) {
                        nextBoard[k] = new NQueen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //Move the queen
                if (tmpBoard[i].getRow()!=n-1)
                    tmpBoard[i].move();
            }
        }
        //Check whether the present bord and the best board found have same heuristic
        //Then randomly generate new board and assign it to best board
        if (bestHeuristic == presentHeuristic) {
            nextBoard = generateBoard();
            heuristic = findHeuristic(nextBoard);
        } else
            heuristic = bestHeuristic;
        return nextBoard;
    }
    
    @Override
    public void run(){
        NQueen[] presentBoard = generateBoard();
        presentHeuristic = findHeuristic(presentBoard);

        // test if the present board is the solution board
        while (presentHeuristic != 0) {
            //  Get the next board
            presentBoard = nextBoard(presentBoard);
            presentHeuristic  = heuristic;
        }
        finalSolution = presentBoard;
	}   
}