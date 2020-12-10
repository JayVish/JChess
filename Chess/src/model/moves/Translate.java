package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;

import java.util.ArrayList;
import java.util.List;

public class Translate extends ChessMove {
    private ChessBoard board;
    private Piece p;

    public Translate(Piece p, Square target, ChessBoard board) {
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
        board.removePiece(getOldLocation());
        board.placePiece(getNewLocation(), getPiece());
    }

    @Override
    public void undoMove() {
        p.removeMoveFromPiece();
        board.removePiece(getNewLocation());
        board.placePiece(getOldLocation(), getPiece());
    }
}
