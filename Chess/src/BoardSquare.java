import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardSquare {
    private Color trueColor;
    private Color currColor;
    private Color moveChoiceColor;
    private boolean isMoveChoice;
    private int xLoc;
    private int yLoc;
    private int dim;

    public BoardSquare(boolean isLight, int dim, int xLoc, int yLoc) {
        if (isLight) {
            trueColor = Constants.LIGHT;
        } else {
            trueColor = Constants.DARK;
        }
        currColor = trueColor;
        moveChoiceColor = new Color(107, 111, 70);
        isMoveChoice = false;

        this.dim = dim;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public int getDim() {
        return dim;
    }

    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void highlightSquare() {
        currColor = new Color(138, 151, 111);
    }

    public void setIsMoveChoice() {
        isMoveChoice = true;
    }

    public boolean isMoveChoice() {
        return isMoveChoice;
    }

    public void setIsNotMoveChoice() {
        isMoveChoice = false;
    }

    public void dehighlightSquare() {
        currColor = trueColor;
    }

    public void draw(Graphics g, String pieceSpritePath) {
        g.setColor(currColor);
        g.fillRect(xLoc, yLoc, dim, dim);

        if (pieceSpritePath != null) {
            drawPiece(g, pieceSpritePath);
        }

        if (isMoveChoice) {
            g.setColor(moveChoiceColor);
            int circleRadius = (int) (.15*dim);
            g.fillOval(xLoc + dim/2 - circleRadius, yLoc + dim/2 - circleRadius, circleRadius*2, circleRadius*2);
            g.setColor(currColor);
        }
    }

    private void drawPiece(Graphics g, String pieceSpritePath) {
        BufferedImage pieceImg;
        try {
            pieceImg = ImageIO.read(new File(pieceSpritePath));
            g.drawImage(pieceImg, xLoc, yLoc, this.dim, this.dim, null);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
}
