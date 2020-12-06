package model.pieces;

import model.Side;

public class Bishop extends Piece {

    public Bishop(Side s) {
        super(s);

    }

    @Override
    public String getPieceName() {
        return "Bishop";
    }
}
