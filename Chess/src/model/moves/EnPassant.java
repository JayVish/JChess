package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

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
    public void makeMove() {
        p.addMoveToPiece();
        board.placePiece(getNewLocation(), getPiece());
        board.removePiece(getOldLocation());
        board.addPieceToCaptured(board.getOppositePlayer(getPiece().getSide()), getCapturedPiece());
        board.removePiece(getCapturedPiece().getSquare());
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
