package model.pieces;

import model.ChessBoard;
import model.Side;
import model.moves.ChessMove;

import java.util.List;

public class Knight extends Piece {

    public Knight(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Knight";
    }

    @Override
    public List<ChessMove> getChessMoves(ChessBoard board) {
        return null;
    }
}
