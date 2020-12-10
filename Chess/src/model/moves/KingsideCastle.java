package model.moves;

import model.ChessBoard;
import model.Square;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class KingsideCastle extends Castle {
    private ChessBoard board;
    private Piece king;

    public KingsideCastle(Piece king, Piece rook, Square target, ChessBoard board) {
        super(king, rook, target, board);

        int direction = 1;
        if (king.getSide() == 1) {
            direction = -1;
        }

        setNewKingPosition(board.getHorizontal(king.getSide(), king.getSquare(), direction*2));
        setNewRookPosition(board.getHorizontal(rook.getSide(), rook.getSquare(), direction*-2));

        this.king = king;
        this.board = board;
    }

//    @Override
//    public List<Square> getChangedSquares() {
//        List<Square> updatedSquares = new ArrayList<>();
//        for (int count = 0; count <= 3; count++) {
//            updatedSquares.add(board.getHorizontal(king.getSide(), getOldLocation(), count));
//        }
//
//        return updatedSquares;
//    }
}
