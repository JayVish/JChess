package model.pieces;

import model.Side;

public class Rook extends Piece {

    public Rook(Side s) {
        super(s);

    }

    @Override
    public String getPieceName() {
        return "Rook";
    }
}
