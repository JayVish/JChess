import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardSquare {

    private Color color;
    private int xLoc;
    private int yLoc;
    private int dim;

    public BoardSquare(boolean isLight, int dim, int xLoc, int yLoc) {
        if (isLight) {
            color = Constants.LIGHT;
        } else {
            color = Constants.DARK;
        }

        this.dim = dim;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public void draw(Graphics g) {
        g.setColor(color);
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
