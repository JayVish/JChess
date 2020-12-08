package model.pieces;

public class King extends Piece {

    public King(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "King";
    }
}
