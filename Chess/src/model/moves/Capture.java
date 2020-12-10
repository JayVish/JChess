package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Capture extends ChessMove {
    private ChessBoard board;
    private Piece p;

    public Capture(Piece p, Square target, ChessBoard board) {
        super(p, target, board);
        setCapturedPiece(board.getPieceAtSquare(getNewLocation()));
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
        board.removePiece(getNewLocation());
        board.placePiece(getNewLocation(), getPiece());
        board.addPieceToCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
        // don't need to clear square on board because piece stack on top of it
    }

    @Override
    public void undoMove() {
        p.removeMoveFromPiece();
        board.placePiece(getOldLocation(), getPiece());
        board.placePiece(getNewLocation(), getCapturedPiece());
        board.removePieceFromCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
    }
}
