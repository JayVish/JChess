package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;
import model.pieces.Queen;

import java.util.ArrayList;
import java.util.List;

public class Promotion extends ChessMove {
    private ChessBoard board;
    private Piece p;
    private Piece promotedPiece;

    public Promotion(Piece p, Square target, ChessBoard board) {
        super(p, target, board);
        this.p = p;
        promotedPiece = new Queen(getPiece().getSide(), getNewLocation());

        this.board = board;
    }

    public Promotion(Piece p, Piece capturedPiece, Square target, ChessBoard board) {
        super(p, target, board);
        setCapturedPiece(capturedPiece);
        this.p = p;
        promotedPiece = new Queen(getPiece().getSide(), getNewLocation());

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
        board.placePiece(getNewLocation(), promotedPiece);
        if (hasCapturedPiece()) {
            board.addPieceToCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
        }
    }

    @Override
    public void undoMove() {
        p.removeMoveFromPiece();
        board.removePiece(promotedPiece.getSquare());
        board.placePiece(getOldLocation(), getPiece());
        if (hasCapturedPiece()) {
            board.placePiece(getNewLocation(), getCapturedPiece());
            board.removePieceFromCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
        }
        // need to undo prior move (translate, capture) that got you here as well
        // there must have been a prior move to get here
        // board.getLastMove().undoMove();
    }
}
