package model.pieces;

import model.Side;

public abstract class Piece {

    private String side;
    private boolean hasMoved;

    Piece(Side s) {
        switch (s) {
            case BLACK:
                side = "Black";
                break;
            case WHITE:
                side = "White";
                break;
        }

        hasMoved = false;
    }

    public abstract String getPieceName();

    public void setPieceToMoved() {
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public String getSpriteFilePath() {
        return "Sprites/" + side + "/" + getPieceName()+".png";
    }
}
