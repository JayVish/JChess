package model.pieces;

import model.Side;

public class Pawn extends Piece {

    public Pawn(Side s) {
        super(s);

    }

    @Override
    public String getPieceName() {
        return "Pawn";
    }
}
