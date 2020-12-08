package model.moves;

import model.ChessBoard;
import model.pieces.Piece;

public class Capture extends ChessMove {

    private ChessBoard board;

    public Capture(Piece p, ChessBoard board) {
        super(p, board);

        this.board = board;
    }

    @Override
    public void makeMove() {
        board.placePiece(getOldLocation(), getNewLocation());
    }

    @Override
    public void undoMove() {
        board.placePiece(getNewLocation(), getOldLocation());
    }
}
