import java.awt.*;

public class MyRect {
    private int x, y, w, h;

    /**
     * @param x &y specifies the upper-left corner.
     */
    public MyRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x + w, y);
        g.drawLine(x + w, y, x + w, y + h);
        g.drawLine(x + w, y + h, x, y + h);
        g.drawLine(x, y + h, x, y);

        g.setColor(new Color(CoordChecker.BLUE));
        g.fillRect(x, y, w, h);
    }
}
