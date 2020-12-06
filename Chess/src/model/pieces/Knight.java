package model.pieces;

import model.Side;

public class Knight extends Piece {

    public Knight(Side s) {
        super(s);

    }

    @Override
    public String getPieceName() {
        return "Knight";
    }
}
