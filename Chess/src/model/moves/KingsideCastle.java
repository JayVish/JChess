package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class KingsideCastle extends Castle {
    private ChessBoard board;
    private Piece castledRook;
    private Square newKingPosition;
    private Square newRookPosition;
    private Piece king;

    public KingsideCastle(Piece king, Piece rook, Square target, ChessBoard board) {
        super(king, rook, target, board.getHorizontal(king.getSide(), king.getSquare(), 2),
                board.getHorizontal(rook.getSide(), rook.getSquare(), -2), board);
        //super(king, target, board);
        this.castledRook = rook;
        this.newKingPosition = board.getHorizontal(king.getSide(), king.getSquare(), 2);
        this.newRookPosition = board.getHorizontal(castledRook.getSide(), castledRook.getSquare(), -2);


        this.king = king;
        this.board = board;
    }

    @Override
    public List<Square> getChangedSquares() {
        List<Square> updatedSquares = new ArrayList<>();
        for (int count = 0; count <= 3; count++) {
            updatedSquares.add(board.getHorizontal(king.getSide(), getOldLocation(), count));
        }

        return updatedSquares;
    }

//    @Override
//    public void makeMove() {
//        king.addMoveToPiece();
//        castledRook.addMoveToPiece();
//        board.removePiece(getOldLocation());
//        board.removePiece(getNewLocation());
//        board.placePiece(newKingPosition, king);
//        board.placePiece(newRookPosition, castledRook);
//    }
//
//    @Override
//    public void undoMove() {
//        king.removeMoveFromPiece();
//        castledRook.removeMoveFromPiece();
//
//        board.removePiece(newKingPosition);
//        board.removePiece(newRookPosition);
//        board.placePiece(getOldLocation(), getPiece());
//        board.placePiece(getNewLocation(), castledRook);
//
//    }
}
