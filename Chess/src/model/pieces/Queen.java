package model.pieces;

import model.Side;

public class Queen extends Piece {

    public Queen(Side s) {
        super(s);

    }

    @Override
    public String getPieceName() {
        return "Queen";
    }
}
