package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public abstract class Castle extends ChessMove {
    private ChessBoard board;
    private Piece castledRook;
    private Square newKingPosition;
    private Square newRookPosition;
    private Piece king;

    public Castle(Piece king, Piece rook, Square target,
                  Square newKingPosition, Square newRookPosition, ChessBoard board) {
        super(king, target, board);
        this.castledRook = rook;

        this.newKingPosition = newKingPosition;
        this.newRookPosition = newRookPosition;
//        this.newKingPosition = board.getHorizontal(king.getSide(), king.getSquare(), -2);
//        this.newRookPosition = board.getHorizontal(castledRook.getSide(), castledRook.getSquare(), 3);
        this.king = king;
        this.board = board;
    }

    @Override
    public void makeMove() {
        king.addMoveToPiece();
        castledRook.addMoveToPiece();
        board.removePiece(getOldLocation());
        board.removePiece(getNewLocation());
        board.placePiece(newKingPosition, king);
        board.placePiece(newRookPosition, castledRook);
    }

    @Override
    public void undoMove() {
        king.removeMoveFromPiece();
        castledRook.removeMoveFromPiece();

        board.removePiece(newKingPosition);
        board.removePiece(newRookPosition);
        board.placePiece(getOldLocation(), getPiece());
        board.placePiece(getNewLocation(), castledRook);

    }
}
