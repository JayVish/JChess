package model.pieces;

import model.Side;

public class Knight extends Piece {

    public Knight(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Knight";
    }
}
