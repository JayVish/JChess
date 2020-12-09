package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

public class Translate extends ChessMove {
    private ChessBoard board;
    private Piece p;

    public Translate(Piece p, Square target, ChessBoard board) {
        super(p, target, board);

        this.p = p;
        this.board = board;
    }

    @Override
    public void makeMove() {
        p.addMoveToPiece();
        board.placePiece(getNewLocation(), getPiece());
        board.removePiece(getOldLocation());
    }

    @Override
    public void undoMove() {
        p.removeMoveFromPiece();
        board.placePiece(getOldLocation(), getPiece());
        board.removePiece(getNewLocation());
    }
}
