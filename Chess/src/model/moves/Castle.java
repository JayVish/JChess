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

    public Castle(Piece king, Piece rook, Square target, ChessBoard board) {
        super(king, target, board);
        this.castledRook = rook;

//        this.newKingPosition = newKingPosition;
//        this.newRookPosition = newRookPosition;
//        this.newKingPosition = board.getHorizontal(king.getSide(), king.getSquare(), -2);
//        this.newRookPosition = board.getHorizontal(castledRook.getSide(), castledRook.getSquare(), 3);
        this.king = king;
        this.board = board;
    }

    public void setNewKingPosition(Square loc) {
        this.newKingPosition = new Square(loc);
    }

    public void setNewRookPosition(Square loc) {
        this.newRookPosition = new Square(loc);
    }

    @Override
    public List<Square> getChangedSquares() {
        List<Square> updatedSquares = new ArrayList<>();
        Square currSquare = getOldLocation();
        updatedSquares.add(currSquare);
        int count = 1;
        while (board.inBounds(currSquare)) {
            updatedSquares.add(currSquare);
            currSquare = board.getHorizontal(king.getSide(), getOldLocation(), count);
            count++;
        }
        currSquare = getOldLocation();
        count = -1;
        while (board.inBounds(currSquare)) {
            updatedSquares.add(currSquare);
            currSquare = board.getHorizontal(king.getSide(), getOldLocation(), count);
            count--;
        }
//        for (int count = 0; count >= -4; count--) {
//            updatedSquares.add(board.getHorizontal(king.getSide(), getOldLocation(), count));
//        }

        return updatedSquares;
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
