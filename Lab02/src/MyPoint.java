import java.awt.*;

public class MyPoint {
    public int x, y;
    public Color color;

    public MyPoint(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 10, 10);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 10, 10);
    }
}
