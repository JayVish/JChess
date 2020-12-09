package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class PawnTwoStep extends ChessMove {
    private ChessBoard board;
    private Piece p;

    public PawnTwoStep(Piece p, Square target, ChessBoard board) {
        super(p, target, board);

        this.p = p;
        this.board = board;
    }

    @Override
    public List<Square> getChangedSquares() {
        List<Square> updatedSquares = new ArrayList<>();
        updatedSquares.add(getOldLocation());
        updatedSquares.add(getNewLocation());

        return updatedSquares;
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
