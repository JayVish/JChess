package model.pieces;

import model.ChessBoard;
import model.Square;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Rook";
    }

    public List<Square> getTranslateMoves(ChessBoard board, Square loc) {
        List<Square> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        // translate
        // north
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        while(board.inBounds(currSquare) && board.getSquare(currSquare) == null) {
            validMoves.add(new Square(currSquare));
            moveAmount++;
        }
        // south
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), -moveAmount);
        while(board.inBounds(currSquare) && board.getSquare(currSquare) == null) {
            validMoves.add(new Square(currSquare));
            moveAmount++;
        }
        // east
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        while(board.inBounds(currSquare) && board.getSquare(currSquare) == null) {
            validMoves.add(new Square(currSquare));
            moveAmount++;
        }
        // west
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), -moveAmount);
        while(board.inBounds(currSquare) && board.getSquare(currSquare) == null) {
            validMoves.add(new Square(currSquare));
            moveAmount++;
        }

        return validMoves;
    }
}
