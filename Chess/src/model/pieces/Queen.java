package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.Capture;
import model.moves.ChessMove;
import model.moves.Translate;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(int s, int r, int c) {
        super(s, r, c);
    }

    public Queen(int s, Square loc) {
        super(s, loc);
    }

    @Override
    public String getPieceName() {
        return "Queen";
    }

    @Override
    public List<ChessMove> getChessMoves(ChessBoard board) {
        List<ChessMove> moves = new ArrayList<>();
        for (ChessMove m : getTranslateMoves(board)) {
            moves.add(m);
        }
        for (ChessMove m : getCaptureMoves(board)) {
            moves.add(m);
        }

        return moves;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;

        // diagonal moves
        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount++;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        }

        // SW
        moveAmount = -1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount--;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        }

        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount++;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        }

        // SE
        moveAmount = -1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount--;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        }

        // parallel moves
        // N
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount++;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        }

        // S
        moveAmount = -1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount--;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        }

        // E
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount++;
            currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        }

        // W
        moveAmount = -1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
            moveAmount--;
            currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        }


        return validMoves;
    }

    public List<ChessMove> getCaptureMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;

        // diagonal moves
        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount++;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);

        }

        // SW
        moveAmount = -1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount--;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount++;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        // SE
        moveAmount = -1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount--;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        // parallel moves
        // N
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount++;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);

        }

        // S
        moveAmount = -1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount--;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        // E
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount++;
            currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        // W
        moveAmount = -1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        while (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            moveAmount--;
            currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        }
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }
}
