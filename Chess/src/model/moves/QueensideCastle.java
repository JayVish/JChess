package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class QueensideCastle extends Castle {
    private ChessBoard board;
    private Piece king;

    public QueensideCastle(Piece king, Piece rook, Square target, ChessBoard board) {
        super(king, rook, target, board.getHorizontal(king.getSide(), king.getSquare(), -2),
                board.getHorizontal(rook.getSide(), rook.getSquare(), 3), board);

        this.king = king;
        this.board = board;
    }

    @Override
    public List<Square> getChangedSquares() {
        List<Square> updatedSquares = new ArrayList<>();
        for (int count = 0; count >= -4; count--) {
            updatedSquares.add(board.getHorizontal(king.getSide(), getOldLocation(), count));
        }

        return updatedSquares;
    }
}
