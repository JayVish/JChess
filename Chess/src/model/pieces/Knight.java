package model.pieces;

import model.ChessBoard;
import model.Square;
import model.moves.Capture;
import model.moves.ChessMove;
import model.moves.Translate;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(int s, int r, int c) {
        super(s, r, c);
    }

    @Override
    public String getPieceName() {
        return "Knight";
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

        return moves;
    }

    public List<ChessMove> getTranslateMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        Square currSquare;
        ChessMove currMove;
        // check 8 moves in 4 regions
        // top right
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 2), 1);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 1), 2);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        // bottom right
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -1), 2);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -2), 1);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        // top left
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 2), -1);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 1), -2);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        // bottom left
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -1), -2);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -2), -1);
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) == null) {
            currMove = new Translate(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }

        return validMoves;
    }

    public List<ChessMove> getCaptureMoves(ChessBoard board, boolean isCheckConcern) {
        List<ChessMove> validMoves = new ArrayList<>();
        Square currSquare;

        // Top Right
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 2), 1);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 1), 2);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);

        // bottom right
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -1), 2);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -2), 1);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);

        // top left
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 2), -1);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), 1), -2);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);

        // bottom left
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -1), -2);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);
        currSquare = board.getHorizontal(getSide(), board.getVertical(getSide(), getSquare(), -2), -1);
        testCurrentCaptureSquare(board, validMoves, currSquare, isCheckConcern);

        return validMoves;
    }

    private void testCurrentCaptureSquare(ChessBoard board, List<ChessMove> validMoves,
                                          Square currSquare, boolean isCheckConcern) {
        ChessMove currMove;
        if (board.inBounds(currSquare) && board.getPieceAtSquare(currSquare) != null
                && board.getPieceAtSquare(currSquare).getSide() != getSide()) {
            currMove = new Capture(this, currSquare, board);
            addMove(validMoves, currMove, isCheckConcern);
        }
    }

    private void addMove(List<ChessMove> validMoves, ChessMove currMove, boolean isCheckConcern) {
        if (!isCheckConcern || !currMove.createsCheck())
            validMoves.add(currMove);
    }
}
