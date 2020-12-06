import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardSquare2 extends JComponent {
    private Color color;
    private int DIM;
    private String currPieceSpritePath;

    public BoardSquare2(boolean isLight, int dim) {
        if (isLight) {
            color = Constants.LIGHT;
        } else {
            color = Constants.DARK;
        }

        this.DIM = dim;
    }

//    public void draw(Graphics g) {
//        g.setColor(color);
//        g.fillRect(xLoc, yLoc, DIM, DIM);
//    }

    public void setPiece(String pieceSpritePath) {
        if (currPieceSpritePath != pieceSpritePath) {
            currPieceSpritePath = pieceSpritePath;
            repaint();
        }
    }

    public void drawPiece(Graphics g) {
        BufferedImage pieceImg;
        try {
            pieceImg = ImageIO.read(new File(currPieceSpritePath));
            g.drawImage(pieceImg, 0, 0, this.DIM, this.DIM, null);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
        g.fillRect(0, 0, DIM, DIM);

        if (currPieceSpritePath != null) {
            drawPiece(g);
        }
    }

    /**
     * Returns the size of the board square
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIM, DIM);
    }
}
