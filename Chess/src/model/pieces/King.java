package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "King";
    }

    @Override
    public List<ChessMove> getChessMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> moves = new ArrayList<>();
        for (ChessMove m : getTranslateMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getCaptureMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getCastleMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        return moves;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // N
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // E
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // SE
        moveAmount = -1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // S
        moveAmount = -1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // SW
        moveAmount = -1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // W
        moveAmount = -1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        return validMoves;
    }

    public List<ChessMove> getCaptureMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // N
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // E
        moveAmount = 1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // SE
        moveAmount = -1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // S
        moveAmount = -1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // SW
        moveAmount = -1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // W
        moveAmount = -1;
        currSquare = board.getHorizontal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        return validMoves;
    }

    public List<ChessMove> getCastleMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;

        // set direction based on color of piece
        int direction = 1;
        if (getSide() == 1) {
            direction = -1;
        }

        // Kingside castle
        currSquare = board.getHorizontal(getSide(), getSquare(), direction*3);
        if (!hasMoved() && board.getPieceAtSquare(currSquare) != null && board.getPieceAtSquare(currSquare) instanceof Rook
            && !board.getPieceAtSquare(currSquare).hasMoved()) {
            Piece rookToCastle = board.getPieceAtSquare(currSquare);

            // white has kingside to the right
            // black has queenside to the right

            // check if any relevant squares are threatened
            boolean noThreat = true;
            if (isCheckConcern) {
                for (int count = 0; count <= 2; count++) {
                    noThreat = noThreat
                            && !board.isSquareThreatened(getSide(), board.getHorizontal(getSide(), getSquare(), direction*count));
                }
            }

            boolean areSquaresEmpty = true;
            for (int count = 1; count <= 2; count++) {
                areSquaresEmpty = areSquaresEmpty
                        && board.getPieceAtSquare(board.getHorizontal(getSide(), getSquare(), direction*count)) == null;
            }

            if (noThreat && areSquaresEmpty) {
                currMove = new KingsideCastle(this, rookToCastle,
                        rookToCastle.getSquare(), board);
                validMoves.add(currMove);
            }
        }

        // Queenside castle
        currSquare = board.getHorizontal(getSide(), getSquare(), direction*-4);
        if (!hasMoved() && board.getPieceAtSquare(currSquare) != null && board.getPieceAtSquare(currSquare) instanceof Rook
                && !board.getPieceAtSquare(currSquare).hasMoved()) {
            Piece rookToCastle = board.getPieceAtSquare(currSquare);
            // check if any relevant squares are threatened
            boolean noThreat = true;
            if (isCheckConcern) {
                for (int count = 0; count >= -2; count--) {
                    noThreat = noThreat
                            && !board.isSquareThreatened(getSide(), board.getHorizontal(getSide(), getSquare(), direction*count));
                }
            }

            boolean areSquaresEmpty = true;
            for (int count = -1; count >= -3; count--) {
                areSquaresEmpty = areSquaresEmpty
                        && board.getPieceAtSquare(board.getHorizontal(getSide(), getSquare(), direction*count)) == null;
            }

            if (noThreat && areSquaresEmpty) {
                currMove = new QueensideCastle(this, rookToCastle,
                        rookToCastle.getSquare(), board);
                validMoves.add(currMove);
            }
        }

        return validMoves;
    }

        private void addMove(List<ChessMove> validMoves, ChessMove currMove, boolean isCheckConcern) {
        if (!isCheckConcern || !currMove.createsCheck())
            validMoves.add(currMove);
    }
}
