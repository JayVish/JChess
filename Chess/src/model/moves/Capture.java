package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

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
    public void makeMove() {
        p.addMoveToPiece();
        board.placePiece(getNewLocation(), getPiece());
        board.removePiece(getOldLocation());
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
