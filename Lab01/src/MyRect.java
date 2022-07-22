import java.awt.*;

public class MyRect {
    private Color strokeColor;
    private int x, y, w, h;
    public static final int RED = 0xFF9999;
    public static final int GREEN = 0x99FF99;
    public static final int BLUE = 0x9999FF;

    /**
     * @param x &y specifies the upper-left corner.
     */
    public MyRect(int x, int y, int w, int h, int color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        strokeColor = new Color(color);
    }

    public void draw(Graphics g) {
        g.setColor(strokeColor);
        g.fillRoundRect(x, y, w, h, 20, 20);
    }
}
