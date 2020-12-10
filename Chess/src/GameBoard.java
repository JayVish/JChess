import model.ChessBoard;
import model.Square;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * This class instantiates a Chess object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework.  This
 * framework is very effective for turn-based games.
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with 
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private ChessBoard chess; // model for the game
    private JLabel status; // current status text
    private BoardSquare[][] board2; // board to paint
    private BoardSquare2[][] board;
    private Mode currMode; // curr state in game
    private Square highlightedSquare;

    // Game constants
    public static final int BOARD_DIM = 800;
    public static final int SQUARE_DIM = BOARD_DIM/8;

    private List<CapturePanel> capturePanels;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JPanel game) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        
        chess = new ChessBoard(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        addMouseListener(new Mouse());

        game.setLayout(new BorderLayout());
        game.add(this, BorderLayout.CENTER);

        capturePanels = new ArrayList<>();
        // captured white pieces
        capturePanels.add(new CapturePanel(0, chess));
        // captured black pieces
        capturePanels.add(new CapturePanel(1, chess));
        game.add(capturePanels.get(0), BorderLayout.NORTH);
        game.add(capturePanels.get(1), BorderLayout.SOUTH);
        reset();
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        // clear panel
        // long t1 = System.currentTimeMillis();
        removeAll();

        chess.reset();
        status.setText("White's Turn");

        // repaint capture panel
        repaintCapturePanels();

        // generate initial colored board
        board2 = new BoardSquare[8][8];
        board = new BoardSquare2[8][8];
        setLayout(new GridLayout(8,8));
        for (int r = 0; r < 8; r++) {
            boolean isLight = r%2 == 0;
            for (int c = 0; c < 8; c++) {
                board2[r][c] = new BoardSquare(isLight, SQUARE_DIM, c*SQUARE_DIM, r*SQUARE_DIM);
                board[r][c] = new BoardSquare2(isLight, SQUARE_DIM, chess, r, c);

                add(board[r][c]);
                isLight = !isLight;
            }
        }

        // resetting doesn't work w/o this and only god knows why
        revalidate();
        repaint();
        // System.out.println((System.currentTimeMillis() - t1)/1000.0);

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

        // set mode
        currMode = new MoveStartMode();
    }

    public void undo() {
        List<Square> updateSquares = chess.undoMove();
        for (Square s : updateSquares) {
            board[s.getR()][s.getC()].repaint();
        }
        updateStatus();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        // removes any previous modifiers
        clearBoardOfAllIndicators();

        // update capture panel
        repaintCapturePanels();

        Square attackedKing;
        if (chess.isGameOver()) {
            switch (chess.getGameStatus()) {
                case 0:
                    status.setText("White Wins!");
                    attackedKing = chess.getPositionOfKing(1);
                    board[attackedKing.getR()][attackedKing.getC()].setIsInCheckmate();
                    board[attackedKing.getR()][attackedKing.getC()].repaint();
                    break;
                case 1: status.setText("Black Wins!");
                    attackedKing = chess.getPositionOfKing(0);
                    board[attackedKing.getR()][attackedKing.getC()].setIsInCheckmate();
                    board[attackedKing.getR()][attackedKing.getC()].repaint();
                    break;
                case 2: status.setText("Stalemate!");
                    break;
            }
        } else {
            switch (chess.getGameStatus()) {
                case 3:
                    attackedKing = chess.getPositionOfKing(1);
                    board[attackedKing.getR()][attackedKing.getC()].setIsInCheck();
                    board[attackedKing.getR()][attackedKing.getC()].repaint();
                    break;
                case 4:
                    attackedKing = chess.getPositionOfKing(0);
                    board[attackedKing.getR()][attackedKing.getC()].setIsInCheck();
                    board[attackedKing.getR()][attackedKing.getC()].repaint();
                    break;
            }
            if (chess.getCurrentPlayer() == 0) {
                status.setText("White's Turn");
            } else {
                status.setText("Black's Turn");
            }
        }
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board.  This approach
     * will not be sufficient for most games, because it is not
     * modular.  All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods.  Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        gamePanel.repaint();
//
////        long t1 = System.currentTimeMillis();
////        // Draw board squares
////        for (int r = 0; r < 8; r++) {
////            for (int c = 0; c < 8; c++) {
////                String pieceSpritePath = chess.getSprite(r, c);
////
////                board[r][c].draw(g, pieceSpritePath);
////            }
////        }
////        System.out.println((System.currentTimeMillis() - t1)/1000.0);
//
//    }

    private void repaintCapturePanels() {
        for (CapturePanel cp : capturePanels) {
            cp.repaint();
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_DIM, BOARD_DIM);
    }

    private Square getSquareFromPoint(Point p) {
        return new Square(p.y/SQUARE_DIM, p.x/SQUARE_DIM);
    }

    interface Mode extends MouseListener, MouseMotionListener { }

    private void clearBoardOfAllIndicators() {
        if (highlightedSquare != null) {
            board[highlightedSquare.getR()][highlightedSquare.getC()].dehighlightSquare();
            board[highlightedSquare.getR()][highlightedSquare.getC()].repaint();
        }
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boolean needToRepaint = false;
                if (board[r][c].isMoveChoice()) {
                    board[r][c].setIsNotMoveChoice();
                    needToRepaint = true;
                }
                if (board[r][c].isInCheck()) {
                    board[r][c].setIsNotInCheck();
                    needToRepaint = true;
                }

                if (needToRepaint) {
                    board[r][c].repaint();
                }
            }
        }
    }

    private void clearBoardOfPriorTurnIndicators() {
        if (highlightedSquare != null) {
            board[highlightedSquare.getR()][highlightedSquare.getC()].dehighlightSquare();
            board[highlightedSquare.getR()][highlightedSquare.getC()].repaint();
        }
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boolean needToRepaint = false;
                if (board[r][c].isMoveChoice()) {
                    board[r][c].setIsNotMoveChoice();
                    needToRepaint = true;
                }

                if (needToRepaint) {
                    board[r][c].repaint();
                }
            }
        }
    }

    class MoveStartMode extends MouseAdapter implements Mode {
        public MoveStartMode() {
            // dehighlight previously chosen square if necessary
            clearBoardOfPriorTurnIndicators();
        }

        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            Square clicked = getSquareFromPoint(p);

            if (chess.canMovePiece(clicked.getR(), clicked.getC())) {
                board[clicked.getR()][clicked.getC()].highlightSquare();
                board[clicked.getR()][clicked.getC()].repaint();
                currMode = new MoveEndMode(clicked);
                highlightedSquare = clicked;
                List<Square> validMoves = chess.getValidSquares(clicked.getR(), clicked.getC());
                for (Square s : validMoves) {
                    board[s.getR()][s.getC()].setIsMoveChoice();
                    board[s.getR()][s.getC()].repaint();
                }
            }
        }
    }

    class MoveEndMode extends MouseAdapter implements Mode {
        Square start;

        // location of chosen piece to move
        MoveEndMode (Square start) {
            this.start = start;
        }

        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            Square clicked = getSquareFromPoint(p);

//            if (chess.isValidMove(start.getR(), start.getC(), clicked.getR(), clicked.getC())) {

            List<Square> changedSquares = chess.makeMove(start.getR(), start.getC(), clicked.getR(), clicked.getC());
            for (Square s : changedSquares) {
                board[s.getR()][s.getC()].repaint();
            }

            // is game over?
            if (chess.isGameOver()) {
                currMode = new GameOverMode();
            } else {
                updateStatus();
                // reset move regardless of if valid
                currMode = new MoveStartMode();
            }
        }
    }

    class GameOverMode extends MouseAdapter implements Mode {
        public GameOverMode() {
            clearBoardOfAllIndicators();
            updateStatus();
        }
    }

    private class Mouse extends MouseAdapter {

        public void mousePressed(MouseEvent arg0) {
            currMode.mousePressed(arg0);
            // repaint();
        }

        public void mouseDragged(MouseEvent arg0) {
            currMode.mouseDragged(arg0);
            // repaint();
        }

        public void mouseReleased(MouseEvent arg0) {
            currMode.mouseReleased(arg0);
            // repaint();
        }
    }


//    private class model.Square {
//        private int r;
//        private int c;
//
//        public model.Square(int r, int c) {
//            this.r = r;
//            this.c = c;
//        }
//
//        public int getR() {
//            return r;
//        }
//
//        public int getC() {
//            return c;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            model.Square square = (model.Square) o;
//            return r == square.r &&
//                    c == square.c;
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(r, c);
//        }
//    }
}