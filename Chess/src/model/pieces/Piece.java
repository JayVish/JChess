package model.pieces;

import model.Square;
import model.moves.ChessMove;

import java.io.Serializable;
import java.util.List;

public abstract class Piece {
    private int side;
    private boolean hasMoved;
    private int r;
    private int c;

    Piece(int s, int r, int c) {
        this.side = s;
        this.r = r;
        this.c = c;
        this.hasMoved = false;
    }

    protected int getSide() {
        return side;
    }

    public Square getSquare() {
        return new Square(this.r,  this.c);
    }

    public int getR() {
        return this.r;
    }

    public int getC() {
        return this.c;
    }

    public abstract String getPieceName();

    public void setPieceToMoved() {
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public String stringOfSide() {
        switch (side) {
            case 0:
                return "Black";
            case 1:
                return "White";
            default:
                return "";
        }
    }

    public abstract List<ChessMove> getChessMoves();

    public String getSpriteFilePath() {
        return "Sprites/" + stringOfSide() + "/" + getPieceName()+".png";
    }
}
