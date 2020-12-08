package model;

import model.moves.*;
import model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {

    // make private
    public Piece[][] board;
    private int numTurns;
    // current playing player
    private int currentPlayer;
    // Ongoing, Checkmate, stalemate, draw
    private boolean gameState;

    // 0 is white, 1 is black
    private List<List<Piece>> activePieces;
    private List<List<Piece>> capturedPieces;

    private List<Piece> kings;

    // isInCheck (Side s)

    // 0 for White win, 1 for Black win, 2 for draw, and 3 for ongoing
    private int gameStatus;

    public ChessBoard() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Piece[8][8];

        int currSideToSet;
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

        // set kings
        kings = new ArrayList<>();
        kings.add(board[7][4]);
        kings.add(board[0][4]);

        // init active list
        activePieces = new ArrayList<>();

        // init white active pieces
        activePieces.add(new ArrayList<>());
        for (int r = 6; r <= 7; r++) {
            for (int c = 0; c < 8; c++) {
                activePieces.get(0).add(board[r][c]);
            }
        }

        // init black active pieces
        activePieces.add(new ArrayList<>());
        for (int r = 0; r <= 1; r++) {
            for (int c = 0; c < 8; c++) {
                activePieces.get(1).add(board[r][c]);
            }
        }

        // init captured list
        capturedPieces = new ArrayList<>();

        // init white captured pieces
        capturedPieces.add(new ArrayList<>());

        // init black captured pieces
        capturedPieces.add(new ArrayList<>());

        // init game state fields
        numTurns = 0;
        currentPlayer = 0;
        gameStatus = 3;
    }

    public Piece getSquare(Square loc) {
        return board[loc.getR()][loc.getC()];
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getOppositePlayer(int side) {
        return 1 - side;
    }

    public void addPieceToCaptured(int side, Piece p) {
        board[p.getR()][p.getC()] = null;
        capturedPieces.get(side).add(p);
    }

    public boolean inBounds(Square loc) {
        if (loc.getR() < 0 || loc.getR() >= 8) return false;
        if (loc.getC() < 0 || loc.getC() >= 8) return false;

        return true;
    }

    public void placePiece(Square loc, Square target) {
        board[target.getR()][target.getC()] = board[loc.getR()][loc.getC()];
    }

    // positive value goes up
    public Square getVertical(int side, Square loc, int count) {
        switch (side) {
            case 0:
                return new Square(loc.getR() - count, loc.getC());
            case 1:
                return new Square(loc.getR() + count, loc.getC());
            default:
                return null;
        }
    }

    // positive value goes right
    public Square getHorizontal(int side, Square loc, int count) {
        switch (side) {
            case 0:
                return new Square(loc.getR(), loc.getC() + count);
            case 1:
                return new Square(loc.getR(), loc.getC() - count);
            default:
                return null;
        }
    }

    public Square getDiagonal(int side, Square loc, int count) {
        return new Square(getVertical(side, loc, count).getR(),
                getHorizontal(side, loc, count).getC());
    }

    private void flipPlayer() {
        currentPlayer = 1 - currentPlayer;
    }

    // make move
    public boolean makeMove(Piece p, int r, int c) {
        if (p.makeChessMove(new Square(r, c))) {
            flipPlayer();
            return true;
        }

         return false;
    }

    // chess board is responsible for game states: check, checkmate, and draw

    public boolean doesPlayerHaveMove(int side) {
        for (Piece p : activePieces.get(side)) {
            if (p.getChessMoves().size() > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isPlayerInStalemate(int side) {
        return !isPlayerInCheck(side) && !doesPlayerHaveMove(side);
    }

    public boolean isPlayerInCheckmate(int side) {
        return isPlayerInCheck(side) && doesPlayerHaveMove(side);
    }

    public boolean isPlayerInCheck(int side) {
        int opponent = getOppositePlayer(side);

        for (Piece p : activePieces.get(opponent)) {
            for (ChessMove m : p.getChessMoves()) {
                // check if king can be captured by any opposing piece
                if (m instanceof Capture
                        && m.getNewLocation().equals(kings.get(opponent).getSquare())) {
                    return true;
                }
            }
        }

        return false;
    }

}
