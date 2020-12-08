import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardSquare {

    private Color trueColor;
    private Color currColor;
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

        this.dim = dim;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public void highlightSquare() {
        currColor = Color.GREEN;
    }

    public void dehighlightSquare() {
        currColor = trueColor;
    }

    public void draw(Graphics g) {
        g.setColor(currColor);
        g.fillRect(xLoc, yLoc, dim, dim);
    }

    public void drawPiece(Graphics g, String pieceSpritePath) {
        BufferedImage pieceImg;
        try {
            pieceImg = ImageIO.read(new File(pieceSpritePath));
            g.drawImage(pieceImg, xLoc, yLoc, this.dim, this.dim, null);
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
}
