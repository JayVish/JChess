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
    public List<ChessMove> getChessMoves(ChessBoard board) {
        List<ChessMove> moves = new ArrayList<>();
        for (ChessMove m : getTranslateMoves(board)) {
            moves.add(m);
        }
        for (ChessMove m : getPawnTwoStepMoves(board)) {
            moves.add(m);
        }
        for (ChessMove m : getCaptureMoves(board)) {
            moves.add(m);
        }
        for (ChessMove m : getEnPassantMoves(board)) {
            moves.add(m);
        }
        for (ChessMove m : getPromotionMoves(board)) {
            moves.add(m);
        }

        return moves;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // north
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }

    public List<ChessMove> getPawnTwoStepMoves(ChessBoard board) {
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
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }

    public List<ChessMove> getCaptureMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
            && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }
        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
            && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }

    public List<ChessMove> getEnPassantMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        Square captureSquare;
        ChessMove currMove;

        // NW
        moveAmount = 1;
        currSquare = board.getLeftDiagonal(getSide(), getSquare(), moveAmount);
        captureSquare = board.getHorizontal(getSide(), getSquare(), -1);
        generateEnPassantIfAvailable(board, validMoves, currSquare, captureSquare);

        // NE
        moveAmount = 1;
        currSquare = board.getRightDiagonal(getSide(), getSquare(), moveAmount);
        captureSquare = board.getHorizontal(getSide(), getSquare(), 1);
        generateEnPassantIfAvailable(board, validMoves, currSquare, captureSquare);

        return validMoves;
    }

    private void generateEnPassantIfAvailable(ChessBoard board, List<ChessMove> validMoves,
                                              Square currSquare, Square captureSquare) {
        ChessMove currMove;
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null
            && board.inBounds(captureSquare) && board.getPieceAtSquare(captureSquare) instanceof Pawn
            && board.getLastMove() instanceof PawnTwoStep
            && board.getLastMove().getPiece() == board.getPieceAtSquare(captureSquare)) {

            currMove = new EnPassant(this, board.getPieceAtSquare(captureSquare), currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }
    }

    public List<ChessMove> getPromotionMoves(ChessBoard board) {
        List<ChessMove> validMoves = new ArrayList<>();
        int moveAmount;
        Square currSquare;
        ChessMove currMove;
        // north
        moveAmount = 1;
        currSquare = board.getVertical(getSide(), getSquare(), moveAmount);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null
                && board.getVertical(getSide(), getSquare(), 2) == null) {
            currMove = new Promotion(this, currSquare, board);
            if (!currMove.createsCheck())
                validMoves.add(currMove);
        }

        return validMoves;
    }
}
