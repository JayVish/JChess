package model.pieces;

import model.ChessBoard;
import model.moves.ChessMove;

import java.util.List;

public class King extends Piece {

    public King(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "King";
    }

    @Override
    public List<ChessMove> getChessMoves(ChessBoard board) {
        return null;
    }
}
