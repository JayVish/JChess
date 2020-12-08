import model.Chess;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
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
public class GameBoard extends JComponent {

    private Chess chess; // model for the game
    private JLabel status; // current status text
    private BoardSquare[][] board; // board to paint
    private Mode currMode; // curr state in game
    private Square highlightedSquare;

    // Game constants
    public static final int BOARD_DIM = 800;
    public static final int SQUARE_DIM = BOARD_DIM/8;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        
        chess = new Chess(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks.  Updates the model, then updates the game board
         * based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                
                // updates the model given the coordinates of the mouseclick
                chess.playTurn(p.x / 100, p.y / 100);
                
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });

        addMouseListener(new Mouse());
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        chess.reset();
        status.setText("Player 1's Turn");

        // generate initial colored board
        board = new BoardSquare[8][8];
        for (int r = 0; r < 8; r++) {
            boolean isLight = r%2 == 0;
            for (int c = 0; c < 8; c++) {
                board[r][c] = new BoardSquare(isLight, SQUARE_DIM, c*SQUARE_DIM, r*SQUARE_DIM);

                isLight = !isLight;
            }
        }

        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();

        // set mode
        currMode = new MoveStartMode();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (chess.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }
        
        int winner = chess.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        } else if (winner == 3) {
            status.setText("It's a tie.");
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
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw board squares
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c].draw(g);

                String piece = chess.getCell(r, c);
                if (piece != null) {
                    board[r][c].drawPiece(g, piece);
                }
            }
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

    class MoveStartMode extends MouseAdapter implements Mode {
        public MoveStartMode() {
            // dehighlight previously chosen square if necessary
            if (highlightedSquare != null) {
                board[highlightedSquare.getR()][highlightedSquare.getC()].dehighlightSquare();
                repaint();
            }
        }

        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            Square clicked = getSquareFromPoint(p);

            if (chess.canMovePiece(clicked.getR(), clicked.getC())) {

                board[clicked.getR()][clicked.getC()].highlightSquare();
                currMode = new MoveEndMode(clicked);
                highlightedSquare = clicked;

                repaint();
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

            chess.makeMove(start.getR(), start.getC(), clicked.getR(), clicked.getC());

            // is game over?
            if (chess.isGameOver()) {
                currMode = new GameOverMode(chess.getGameStatus());
            } else {
                // reset move regardless of if valid
                currMode = new MoveStartMode();
            }
        }
    }

    class GameOverMode extends MouseAdapter implements Mode {
        public GameOverMode(int gameStatus) {
            switch (gameStatus) {
                case 0: System.out.println("White Wins!");
                case 1: System.out.println("Black Wins!");
                case 2: System.out.println("Stalemate!");
                case 3: System.out.println("The game is ongoing.");
            }
        }
    }

    private class Mouse extends MouseAdapter {

        public void mousePressed(MouseEvent arg0) {
            currMode.mousePressed(arg0);
            repaint();
        }

        public void mouseDragged(MouseEvent arg0) {
            currMode.mouseDragged(arg0);
            repaint();
        }

        public void mouseReleased(MouseEvent arg0) {
            currMode.mouseReleased(arg0);
            repaint();
        }
    }


    private class Square {
        private int r;
        private int c;

        public Square(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Square square = (Square) o;
            return r == square.r &&
                    c == square.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }
}