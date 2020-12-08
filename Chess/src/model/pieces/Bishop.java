package model.pieces;

public class Bishop extends Piece {

    public Bishop(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Bishop";
    }
}
