package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.List;

public abstract class ChessMove {

    private Piece p;
    private Square oldLoc;
    private Square newLoc;
    private ChessBoard board;
    private Piece capturedPiece;

    public ChessMove(Piece p, ChessBoard board) {
        this.p = p;
        this.oldLoc = new Square(p.getR(), p.getC());
        this.board = board;
    }

    public Square getOldLocation() {
        return oldLoc;
    }

    public Square getNewLocation() {
        return newLoc;
    }

    public Piece getPiece() {
        return p;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public abstract void makeMove();

    public abstract void undoMove();

//    public abstract List<Square> generateValidMoves();

    // public abstract void makeMove(int r, int c);

    // every move needs to know if their move creates a check

    // should every move have an "undo" capacity
}
