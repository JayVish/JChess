package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Pawn";
    }

    @Override
    public List<ChessMove> getChessMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> moves = new ArrayList<>();
        for (ChessMove m : getTranslateMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getPawnTwoStepMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getCaptureMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getEnPassantMoves(board, isCheckConcern)) {
            moves.add(m);
        }
        for (ChessMove m : getPromotionMoves(board, isCheckConcern)) {
            moves.add(m);
        }

        // pawn promotion needs to trigger after a capture as well
        return moves;
    }


    public List<ChessMove> getPromotionMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // check if on right row
        if (!board.inBounds(board.getVertical(getSide(), getSquare(), 2))) {
            // N
            moveAmount = 1;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
                currMove = new Promotion(this, currSquare, board);
                addMove(validMoves, currMove, isCheckConcern);
            }
            // NW
            moveAmount = 1;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                    && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
                currMove = new Promotion(this, board.getPieceAtSquare(currSquare), currSquare, board);
                if (!isCheckConcern || !currMove.createsCheck())
                    validMoves.add(currMove);
            }
            // NE
            moveAmount = 1;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                    && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
                currMove = new Promotion(this, board.getPieceAtSquare(currSquare), currSquare, board);
                if (!isCheckConcern || !currMove.createsCheck())
                    validMoves.add(currMove);
            }
        }

        return validMoves;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // don't double count promotion moves
        if (board.inBounds(board.getVertical(getSide(), getSquare(), 2))) {
            // north
            moveAmount = 1;
            currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
                currMove = new Translate(this, currSquare, board);
                if (!isCheckConcern || !currMove.createsCheck())
                    validMoves.add(currMove);
            }
        }

        return validMoves;
    }

    public List<ChessMove> getPawnTwoStepMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // north
        moveAmount = 2;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (!hasMoved() && board.getPieceAtSquare(board.getVertical(getSide(), getSquare(), 1)) == null
                && board.getPieceAtSquare(currSquare) == null) {
            currMove = new PawnTwoStep(this, currSquare, board);
            if (!isCheckConcern || !currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }

    public List<ChessMove> getCaptureMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // don't double count promotion moves
        if (board.inBounds(board.getVertical(getSide(), getSquare(), 2))) {
            // NW
            moveAmount = 1;
            currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                    && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
                currMove = new Capture(this, currSquare, board);
                if (!isCheckConcern || !currMove.createsCheck())
                    validMoves.add(currMove);
            }
            // NE
            moveAmount = 1;
            currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
            if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                    && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
                currMove = new Capture(this, currSquare, board);
                if (!isCheckConcern || !currMove.createsCheck())
                    validMoves.add(currMove);
            }
        }

        return validMoves;
    }

    public List<ChessMove> getEnPassantMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        Square captureSquare;
        ChessMove currMove;

        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        captureSquare = board.getHorizontal(getSide(), getSquare(), -1);
        generateEnPassantIfAvailable(board, validMoves, currSquare, captureSquare, isCheckConcern);

        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        captureSquare = board.getHorizontal(getSide(), getSquare(), 1);
        generateEnPassantIfAvailable(board, validMoves, currSquare, captureSquare, isCheckConcern);

        return validMoves;
    }

    private void generateEnPassantIfAvailable(ChessBoard board, List<ChessMove> validMoves,
                                              Square currSquare, Square captureSquare,
                                              boolean isCheckConcern) {
        ChessMove currMove;
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null
            && board.inBounds(captureSquare) && board.getPieceAtSquare(captureSquare) instanceof Pawn
            && board.getLastMove() instanceof PawnTwoStep
            && board.getLastMove().getPiece() == board.getPieceAtSquare(captureSquare)) {

            currMove = new EnPassant(this, board.getPieceAtSquare(captureSquare), currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
    }

    private void addMove(List<ChessMove> validMoves, ChessMove currMove, boolean isCheckConcern) {
        if (!isCheckConcern || !currMove.createsCheck())
            validMoves.add(currMove);
    }
}
