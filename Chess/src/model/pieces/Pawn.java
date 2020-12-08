package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.Capture;
import model.moves.ChessMove;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece implements Capture {

    public Pawn(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Pawn";
    }

    public List<Square> translate(Piece[][] board) {
        return null;
        // how to check if this move creates a check
        // EVERY move needs to see if a discovered check is created

        /*
        Create a game state object like drawing modes?

         */
    }

    public boolean makeMove(Square target) {

        return true;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        // north
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getSquare(currSquare) == null) {
           // check if check is created
        }

        return validMoves;

    }
}
