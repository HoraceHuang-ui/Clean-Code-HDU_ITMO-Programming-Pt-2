import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class MyPanel extends JPanel {
    public ArrayList<MyLine> lines = new ArrayList<>();
    public ArrayList<MyRect> rects = new ArrayList<>();

    public void addLines(int... n) {
        if (n.length % 4 != 0) {
            throw new IllegalArgumentException("n % 4 != 0");
        }
        for (int i = 0; i < n.length; i += 4) {
            lines.add(new MyLine(n[i], n[i+1], n[i+2], n[i+3]));
        }
    }

    public void addRects(int... n) {
        if (n.length % 5 != 0) {
            throw new IllegalArgumentException("n % 5 != 0");
        }
        for (int i = 0; i < n.length; i += 5) {
            rects.add(new MyRect(n[i], n[i+1], n[i+2], n[i+3], n[i+4]));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        for (MyRect rect : rects) {
            rect.draw(g);
        }
        for (MyLine l : lines) {
            l.draw(g);
        }
    }
}