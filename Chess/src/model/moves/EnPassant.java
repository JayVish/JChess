package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class EnPassant extends ChessMove {
    private ChessBoard board;
    private Piece p;

    public EnPassant(Piece p, Piece captured, Square target, ChessBoard board) {
        super(p, target, board);
        setCapturedPiece(captured);
        this.p = p;

        this.board = board;
    }

    @Override
    public List<Square> getChangedSquares() {
        List<Square> updatedSquares = new ArrayList<>();
        updatedSquares.add(getOldLocation());
        updatedSquares.add(getNewLocation());
        updatedSquares.add(getCapturedPiece().getSquare());

        return updatedSquares;
    }

    @Override
    public void makeMove() {
        p.addMoveToPiece();
        board.removePiece(getOldLocation());
        board.removePiece(getCapturedPiece().getSquare());
        board.placePiece(getNewLocation(), getPiece());
        board.addPieceToCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
    }

    @Override
    public void undoMove() {
        p.removeMoveFromPiece();
        // need to handle differently from capture logic
        board.removePiece(getNewLocation());
        board.placePiece(getOldLocation(), getPiece());
        board.placePiece(getCapturedPiece().getSquare(), getCapturedPiece());
        board.removePieceFromCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
    }
}
