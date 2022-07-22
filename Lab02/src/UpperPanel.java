import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class UpperPanel extends JPanel {
    public ArrayList<MyLine> lines = new ArrayList<>();
    public ArrayList<MyRect> rects = new ArrayList<>();
    public ArrayList<MyTriangle> tris = new ArrayList<>();
    public ArrayList<MyPoint> points = new ArrayList<>();

    public void addLines(int... n) {
        if (n.length % 4 != 0) {
            throw new IllegalArgumentException("n % 4 != 0");
        }
        for (int i = 0; i < n.length; i += 4) {
            lines.add(new MyLine(n[i], n[i+1], n[i+2], n[i+3]));
        }
    }

    public void addRects(int... n) {
        if (n.length % 4 != 0) {
            throw new IllegalArgumentException("n % 4 != 0");
        }
        for (int i = 0; i < n.length; i += 5) {
            rects.add(new MyRect(n[i], n[i+1], n[i+2], n[i+3]));
        }
    }

    public void addTriangles(int ...n) {
        if (n.length % 6 != 0) {
            throw new IllegalArgumentException("n % 6 != 0");
        }
        for (int i = 0; i < n.length; i += 6) {
            tris.add(new MyTriangle(n[i], n[i+1], n[i+2], n[i+3], n[i+4], n[i+5]));
        }
    }
    public void addPoint(int x, int y, Color color) {
        points.add(new MyPoint(x, y, color));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(1));
        for (MyRect rect : rects) {
            rect.draw(g);
        }
        for (MyTriangle t : tris) {
            t.draw(g);
        }
        g2.setStroke(new BasicStroke(2));
        for (MyLine l : lines) {
            l.draw(g);
        }
        g2.setStroke(new BasicStroke(1));
        for (MyPoint p : points) {
            p.draw(g);
        }
    }
}