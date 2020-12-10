import model.ChessBoard;
import model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
public class CapturePanel extends JPanel {
    private ChessBoard chess; // model for the game
    // color of pieces
    private int side;

    // Panel dimensions
    public static final int WIDTH = 800;
    public static final int HEIGHT = 50;
    public static final int SQUARE_DIM = HEIGHT;

    private BoardSquare2[][] board;

    /**
     * Initializes the game board.
     */
    public CapturePanel(int side, ChessBoard chess) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.side = side;
        this.chess = chess;

        reset();
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        // clear panel
        // long t1 = System.currentTimeMillis();
        removeAll();
        revalidate();
        repaint();
        // System.out.println((System.currentTimeMillis() - t1)/1000.0);
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

        List<String> capturedSprites = chess.getCapturedSprites(side);
        // Draw board squares
        for (int i = 0; i < capturedSprites.size(); i++) {
            drawPiece(g, i * (SQUARE_DIM - 5), capturedSprites.get(i));
        }

        int score = chess.getCapturedScore(side);
        String scoreToShow = "+" + score;
        g.setFont(new Font("TimesRoman", Font.BOLD, SQUARE_DIM));
        int scoreWidth = g.getFontMetrics().stringWidth(scoreToShow);
        int scoreHeight = g.getFontMetrics().getHeight();

        g.drawString("+" + score, WIDTH - scoreWidth - 10, 42);
    }

    private void drawPiece(Graphics g, int xLoc, String pieceSpritePath) {
        BufferedImage pieceImg;
        try {
            pieceImg = ImageIO.read(new File(pieceSpritePath));
            g.drawImage(pieceImg, xLoc, 0, SQUARE_DIM, SQUARE_DIM, null);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

}