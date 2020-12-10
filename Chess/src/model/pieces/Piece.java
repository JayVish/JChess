package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.ChessMove;

import java.util.List;

public abstract class Piece {
    private int side;
    private int moveCount;
    private Square position;

    Piece(int side, int r, int c) {
        this.side = side;
        this.position = new Square(r, c);
        this.moveCount = 0;
    }

    Piece(int side, Square loc) {
        this.side = side;
        this.position = new Square(loc);
        this.moveCount = 0;
    }

    public int getSide() {
        return side;
    }

    public Square getSquare() {
        return new Square(this.getR(),  this.getC());
    }

    public int getR() {
        return position.getR();
    }

    public int getC() {
        return position.getC();
    }

    public abstract String getPieceName();

    public void addMoveToPiece() {
        moveCount++;
    }

    public void removeMoveFromPiece() {
        moveCount--;
    }

    public void setPosition(Square loc) {
        position = new Square(loc);
    }

    public boolean hasMoved() {
        return moveCount != 0;
    }

    public String stringOfSide() {
        switch (side) {
            case 0:
                return "White";
            case 1:
                return "Black";
            default:
                return "";
        }
    }

    public abstract List<ChessMove> getChessMoves(ChessBoard board, boolean isCheckConcern);

    public ChessMove makeChessMove(ChessBoard board, Square target) {
        List<ChessMove> possibleMoves = getChessMoves(board, true);
        for (ChessMove m : possibleMoves) {
            if (m.getNewLocation().equals(target)) {
                m.makeMove();
                return m;
            }
        }

        return null;
    }

    public String getSpriteFilePath() {
        return "Sprites/" + stringOfSide() + "/" + getPieceName()+".png";
    }
}
