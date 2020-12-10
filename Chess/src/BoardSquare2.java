import model.ChessBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardSquare2 extends JComponent {
    private ChessBoard chess;
    private Color trueColor;
    private Color currColor;
    private Color moveChoiceColor;
    private Color checkColor;
    private Color checkmateColor;
    private boolean isMoveChoice;
    private boolean isInCheck;
    private int dim;
    private int r;
    private int c;

    public BoardSquare2(boolean isLight, int dim, ChessBoard chess, int r, int c) {
        if (isLight) {
            trueColor = Constants.LIGHT;
        } else {
            trueColor = Constants.DARK;
        }
        currColor = trueColor;
        moveChoiceColor = new Color(107, 111, 70);
        isMoveChoice = false;
        checkColor = new Color(217, 80, 62);
        checkmateColor = Color.RED;

        this.chess = chess;
        this.dim = dim;
        // this.xLoc = xLoc;
        // this.yLoc = yLoc;
        this.r = r;
        this.c = c;
    }

    public void highlightSquare() {
        currColor = new Color(138, 151, 111);
    }

    public boolean isMoveChoice() {
        return isMoveChoice;
    }

    public void setIsMoveChoice() {
        isMoveChoice = true;
    }

    public void setIsNotMoveChoice() {
        isMoveChoice = false;
    }

    public void setIsInCheckmate() {
        currColor = checkmateColor;
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    public void setIsInCheck() {
        isInCheck = true;
    }

    public void setIsNotInCheck() {
        isInCheck = false;
    }

    public void dehighlightSquare() {
        currColor = trueColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(currColor);
        //g.setColor(new Color(0, (int) (Math.random()*255), 0));
        g.fillRect(0, 0, dim, dim);

        if (isInCheck) {
            g.setColor(checkColor);
            g.fillOval(0, 0, dim, dim);
        }

        if (chess.getSprite(r, c) != null) {
            drawPiece(g, chess.getSprite(r, c));
        }

        if (isMoveChoice) {
            g.setColor(moveChoiceColor);
            int circleRadius = (int) (.15*dim);
            g.fillOval(dim/2 - circleRadius, dim/2 - circleRadius, circleRadius*2, circleRadius*2);
            g.setColor(currColor);
        }
    }

    private void drawPiece(Graphics g, String pieceSpritePath) {
        BufferedImage pieceImg;
        try {
            pieceImg = ImageIO.read(new File(pieceSpritePath));
            g.drawImage(pieceImg, 0, 0, this.dim, this.dim, null);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    /**
     * Returns the size of the board square
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dim, dim);
    }
}
