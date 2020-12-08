package model.pieces;

public class Queen extends Piece {

    public Queen(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Queen";
    }
}
