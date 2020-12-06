package model.pieces;

import model.Side;

public class King extends Piece {

    public King(Side s) {
        super(s);
    }

    @Override
    public String getPieceName() {
        return "King";
    }
}
