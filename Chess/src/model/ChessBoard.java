package model;

import model.moves.*;
import model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class ChessBoard {

    // make private
    public Piece[][] board;
    private int numTurns;
    // current playing player
    private int currentPlayer;
    // Ongoing, Checkmate, stalemate, draw
    private boolean gameState;

    // 0 is white, 1 is black
    private List<Set<Piece>> activePieces;
    private List<List<Piece>> capturedPieces;

    // Position of kings on board
    private List<Piece> kings;

    // List of game moves
    private List<ChessMove> listOfGameMoves;

    // 0 for White win, 1 for Black win, 2 for draw, and 3 for black in check
    // 4 for white in check, and 5 for ongoing
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

        board[7][0] = new Rook(currSideToSet, 7, 0);
        board[7][1] = new Knight(currSideToSet, 7, 1);
        board[7][2] = new Bishop(currSideToSet, 7, 2);
        board[7][3] = new Queen(currSideToSet, 7, 3);
        board[7][4] = new King(currSideToSet, 7, 4);
        board[7][5] = new Bishop(currSideToSet, 7, 5);
        board[7][6] = new Knight(currSideToSet, 7, 6);
        board[7][7] = new Rook(currSideToSet, 7, 7);

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
        activePieces.add(new HashSet<>());
        for (int r = 6; r <= 7; r++) {
            for (int c = 0; c < 8; c++) {
                activePieces.get(0).add(board[r][c]);
            }
        }

        // init black active pieces
        activePieces.add(new HashSet<>());
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

        // init move list
        listOfGameMoves = new ArrayList<>();

        // init game state fields
        numTurns = 0;
        currentPlayer = 0;
        gameStatus = 5;
    }

    public ChessMove getLastMove() {
        if (listOfGameMoves.size() == 0) return null;

        return listOfGameMoves.get(listOfGameMoves.size() - 1);
    }

    public Piece getPieceAtSquare(Square loc) {
        return board[loc.getR()][loc.getC()];
    }

    public String getSprite(int r, int c) {
        if (board[r][c] != null) {
            return board[r][c].getSpriteFilePath();
        } else {
            return null;
        }
    }

    public List<Square> getValidSquares(int r, int c) {
        List<Square> validSquares = new ArrayList<>();
        if (canMovePiece(r, c)) {
            for (ChessMove m : board[r][c].getChessMoves(this, true)) {
                validSquares.add(m.getNewLocation());
            }
        }

        return validSquares;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getOppositePlayer(int side) {
        return 1 - side;
    }

    public Square getPositionOfKing(int side) {
        return kings.get(side).getSquare();
    }

    public void addPieceToCaptured(int side, Piece p) {
        capturedPieces.get(side).add(p);
    }

    public void removePieceFromCaptured(int side, Piece capturedPiece) {
        capturedPieces.get(side).remove(capturedPiece);
    }

    public void placePiece(Square loc, Piece p) {
        board[loc.getR()][loc.getC()] = p;
        activePieces.get(p.getSide()).add(p);
        p.setPosition(loc);
    }

    public void removePiece(Square loc) {
        activePieces.get(getPieceAtSquare(loc).getSide()).remove(getPieceAtSquare(loc));
        board[loc.getR()][loc.getC()] = null;
    }

    public boolean inBounds(Square loc) {
        if (loc.getR() < 0 || loc.getR() >= 8) return false;
        if (loc.getC() < 0 || loc.getC() >= 8) return false;

        return true;
    }

    // positive value goes up
    public Square getVertical(int side, Square loc, int count) {
        return switch (side) {
            case 0 -> new Square(loc.getR() - count, loc.getC());
            case 1 -> new Square(loc.getR() + count, loc.getC());
            default -> null;
        };
    }

    // positive value goes right
    public Square getHorizontal(int side, Square loc, int count) {
        return switch (side) {
            case 0 -> new Square(loc.getR(), loc.getC() + count);
            case 1 -> new Square(loc.getR(), loc.getC() - count);
            default -> null;
        };
    }

    public Square getRightDiagonal(int side, Square loc, int count) {
        return new Square(getVertical(side, loc, count).getR(),
                getHorizontal(side, loc, count).getC());
    }

    public Square getLeftDiagonal(int side, Square loc, int count) {
        return new Square(getVertical(side, loc, count).getR(),
                getHorizontal(side, loc, -count).getC());
    }

    private void flipPlayer() {
        currentPlayer = 1 - currentPlayer;
    }

    // make move
//    public boolean makeMove(int r1, int c1, int r2, int c2) {
//        ChessMove m = board[r1][c1].makeChessMove(this, new Square(r2, c2));
//        if (m != null) {
//            listOfGameMoves.add(m);
//            flipPlayer();
//            return true;
//        }
//
//         return false;
//    }

    public List<Square> makeMove(int r1, int c1, int r2, int c2) {
        List<Square> changedSquares = new ArrayList<>();
        ChessMove m = board[r1][c1].makeChessMove(this, new Square(r2, c2));
        if (m != null) {
            listOfGameMoves.add(m);
            changedSquares.addAll(m.getChangedSquares());
            flipPlayer();
            numTurns++;
            setGameStatus();
        }

        printBoard();

        return changedSquares;
    }

    public List<Square> undoMove() {
        List<Square> changedSquares = new ArrayList<>();
        ChessMove m = getLastMove();
        if (m != null) {
            m.undoMove();
            listOfGameMoves.remove(m);
            changedSquares.addAll(m.getChangedSquares());
            flipPlayer();
            numTurns--;
            setGameStatus();
        }

        printBoard();

        return changedSquares;
    }

    // chess board is responsible for game states: check, checkmate, and draw

    public boolean doesPlayerHaveMove(int side) {
        List<Piece> activePiecesCopy = new ArrayList<>();
        activePiecesCopy.addAll(activePieces.get(side));

        for (Piece p : activePiecesCopy) {
            if (p.getChessMoves(this, true).size() > 0) {
                return true;
            }
        }

        return false;
    }

    // Game state

    public boolean isGameOver() {
        return gameStatus == 0 || gameStatus == 1 || gameStatus == 2;
    }

    private void setGameStatus() {
        if (isPlayerInCheckmate(currentPlayer)) {
            gameStatus = getOppositePlayer(currentPlayer);
        } else if(isPlayerInStalemate(currentPlayer)) {
            gameStatus = 2;
        } else if(isPlayerInCheck(1)){
            gameStatus = 3;
        } else if(isPlayerInCheck(0)) {
            gameStatus = 4;
        } else {
            gameStatus = 5;
        }
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public int checkWinner() {
        return 1;
    }

    public boolean isPlayerInStalemate(int side) {
        return !isPlayerInCheck(side) && !doesPlayerHaveMove(side);
    }

    public boolean isPlayerInCheckmate(int side) {
        return isPlayerInCheck(side) && !doesPlayerHaveMove(side);
    }

    public boolean isPlayerInCheck(int side) {
        return isSquareThreatened(side, kings.get(side).getSquare());
    }

    public boolean isSquareThreatened(int side, Square loc) {
        int opponent = getOppositePlayer(side);

        for (Piece p : activePieces.get(opponent)) {
            for (ChessMove m : p.getChessMoves(this, false)) {
                // check if square can be captured by any opposing piece
                // excludes en passant
                // m.hasCapturedPiece() && <- remove from if
                if (m.getNewLocation().equals(loc)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canMovePiece(int r, int c) {
        return board[r][c] != null && board[r][c].getSide() == currentPlayer;
    }

    private void printBoard() {
        System.out.println("Turn Number: " + numTurns);
        for (int r = 0; r < 8; r++) {
            System.out.print("|");
            for (int c = 0; c < 8; c++) {
                if (board[r][c] == null) {
                    System.out.print("_");
                } else if(board[r][c] instanceof Pawn) {
                    System.out.print("P");
                } else if(board[r][c] instanceof Rook) {
                    System.out.print("R");
                } else if(board[r][c] instanceof Bishop) {
                    System.out.print("B");
                } else if(board[r][c] instanceof Knight) {
                    System.out.print("N");
                } else if(board[r][c] instanceof Queen) {
                    System.out.print("Q");
                } else if(board[r][c] instanceof King) {
                    System.out.print("K");
                }
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("Game Status: " + gameStatus);
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {
        // for debugging purposes only
        ChessBoard board = new ChessBoard();
        board.makeMove(6, 4, 5, 4);

        board.makeMove(1,0,2,0);

        // bishop move
        board.makeMove(7, 5, 4, 2);

        board.makeMove(2,0,3,0);

        // queen move
        board.makeMove(7, 3, 5, 5);

        board.makeMove(3, 0, 4, 0);

        // checkmate move
        board.makeMove(5, 5, 1, 5);


    }

}
