package model; /**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import model.pieces.*;

/**
 * This class is a model for Chess.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games.  We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:  
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Chess {

    private Piece[][] board;
    private int numTurns;
    private boolean playerWhite;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Chess() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. Returns true 
     * if the move is successful and false if a player tries
     * to play in a location that is taken or after the game
     * has ended. If the turn is successful and the game has 
     * not ended, the player is changed. If the turn is 
     * unsuccessful or the game has ended, the player is not 
     * changed.
     * 
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean makeMove(int r1, int c1, int r2, int c2, int test) {
//        if (board[r][c] != 0 || gameOver) {
//            return false;
//        }

//        if (player1) {
//            board[r][c] = 1;
//        } else {
//            board[r][c] = 2;
//        }

        numTurns++;
        if (checkWinner() == 0) {
            playerWhite = !playerWhite;
        }
        return true;
    }

    /**
     * checkWinner checks whether the game has reached a win 
     * condition. checkWinner only looks for horizontal wins.
     * 
     * @return 0 if nobody has won yet, 1 if player 1 has won, and
     *            2 if player 2 has won, 3 if the game hits stalemate
     */
    public int checkWinner() {
        // Check horizontal win
//        for (int i = 0; i < board.length; i++) {
//            if (board[i][0] == board[i][1] &&
//                board[i][1] == board[i][2] &&
////                board[i][1] != 0
//                ) {
//                 gameOver = true;
//                if (player1) {
//                    return 1;
//                } else {
//                    return 2;
//                }
//            }
//        }
        
        if (numTurns >= 9) {
            gameOver = true;
            return 3;
        } else {
            return 0;
        }
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 2) { 
                    System.out.print(" | "); 
                }
            }
            if (i < 2) {
                System.out.println("\n---------"); 
            }
        }
    }
    
    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Piece[8][8];

        int currSideToSet;

        // init black's pieces
        currSideToSet = 1;

        board[0][0] = new Rook(currSideToSet, 0, 0);
        board[0][1] = new Knight(currSideToSet, 0, 1);
        board[0][2] = new Bishop(currSideToSet, 0, 2);
        board[0][3] = new Queen(currSideToSet, 0, 3);
        board[0][4] = new King(currSideToSet, 0, 4);
        board[0][5] = new Bishop(currSideToSet, 0, 5);
        board[0][6] = new Knight(currSideToSet, 0, 6);
        board[0][7] = new Rook(currSideToSet, 0, 7);

        for (int c = 0; c < 8; c++) {
            board[1][c] = new Pawn(currSideToSet, 1, c);
        }

        // init white's pieces
        currSideToSet = 0;

        board[7][0] = new Rook(currSideToSet, 0, 0);
        board[7][1] = new Knight(currSideToSet, 0, 1);
        board[7][2] = new Bishop(currSideToSet, 0, 2);
        board[7][3] = new Queen(currSideToSet, 0, 3);
        board[7][4] = new King(currSideToSet, 0, 4);
        board[7][5] = new Bishop(currSideToSet, 0, 5);
        board[7][6] = new Knight(currSideToSet, 0, 6);
        board[7][7] = new Rook(currSideToSet, 0, 7);

        for (int c = 0; c < 8; c++) {
            board[6][c] = new Pawn(currSideToSet, 6, c);
        }

        numTurns = 0;
        playerWhite = true;
        gameOver = false;
    }
    
    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     * false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return playerWhite;
    }
    
    /**
     * getCell is a getter for the contents of the
     * cell specified by the method arguments.
     * 
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents
     *         of the corresponding cell on the 
     *         game board.  0 = empty, 1 = Player 1,
     *         2 = Player 2
     */
    public String getCell(int r, int c) {
        if (board[r][c] != null) {
            return board[r][c].getSpriteFilePath();
        } else {
            return null;
        }
    }
    
    /**
     * This main method illustrates how the model is
     * completely independent of the view and
     * controller.  We can play the game from start 
     * to finish without ever creating a Java Swing 
     * object.
     * 
     * This is modularity in action, and modularity 
     * is the bedrock of the  Model-View-Controller
     * design framework.
     * 
     * Run this file to see the output of this
     * method in your console.
     */
//    public static void main(String[] args) {
//        Chess c = new Chess();
//
//        c.makeMove(1, 1);
//        c.printGameState();
//
//        c.makeMove(0, 0);
//        c.printGameState();
//
//        c.makeMove(0, 2);
//        c.printGameState();
//
//        c.makeMove(2, 0);
//        c.printGameState();
//
//        c.makeMove(1, 0);
//        c.printGameState();
//
//        c.makeMove(1, 2);
//        c.printGameState();
//
//        c.makeMove(0, 1);
//        c.printGameState();
//
//        c.makeMove(2, 2);
//        c.printGameState();
//
//        c.makeMove(2, 1);
//        c.printGameState();
//        System.out.println();
//        System.out.println();
//        System.out.println("Winner is: " + c.checkWinner());
//    }

    public boolean canMovePiece(int r, int c) {
        return true;
    }

    public boolean isValidMove(int r1, int c1, int r2, int c2) {
        return board[r2][c2] == null;
    }

    public void makeMove(int r1, int c1, int r2, int c2) {
        if (!isValidMove(r1, c1, r2, c2)) return;

        playerWhite = !playerWhite;
        // throwaway logic for now
        board[r2][c2] = board[r1][c1];
        board[r1][c1] = null;
    }

    public boolean isInCheck(int color) {
        return false;
    }

    public boolean isGameOver() {
        return false;
    }

    /*
    0: white wins
    1: black wins
    2: stalemate
    3: ongoing
     */
    public int getGameStatus() {
        return 0;

    }
}
