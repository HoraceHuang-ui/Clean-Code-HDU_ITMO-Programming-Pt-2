import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CoordChecker {
    public static final int BLUE = 0x9999FF;
    public static UpperPanel uPanel = new UpperPanel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("21321108 Huang Yiyu");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(700, 1200));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));

            LowerPanel lPanel = new LowerPanel();
            drawRef(uPanel);

            panel.add(uPanel);
            panel.add(lPanel);
            window.add(panel);
            window.pack();
            window.setVisible(true);
        });
    }

    private static void drawRef(UpperPanel panel) {
        // Shapes
        panel.addRects(190, 290, 100, 100);
        panel.addTriangles(
                190, 290, 290, 190, 290, 290,
                290, 190, 390, 190, 390, 290,
                490, 290, 490, 390, 290, 390
        );

        // Axis
        panel.addLines(
                40, 290, 540, 290,
                290, 40, 290, 540
        );
    }
}
class LowerPanel extends JPanel {
    int x, y;
    public LowerPanel() {
        this.setLayout(new GridLayout(1, 5));
        this.add(new JLabel("x:"));

        JPanel slctX = new JPanel();
        slctX.setLayout(new GridLayout(21, 1));
        ArrayList<MyCheckBox> cbs = new ArrayList<>();
        for (int i = -10; i < 11; i++) {
            cbs.add(new MyCheckBox(i, true));
            int finalI = i;
            cbs.get(i + 10).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cbs.get(finalI + 10).x) {
                        x = cbs.get(finalI + 10).val;
                        System.out.println(x);
                    } else {
                        y = cbs.get(finalI + 10).val;
                        System.out.println(y);
                    }
                    for (int i = 0; i < cbs.size(); i++) {
                        if (cbs.get(i).isSelected() && i != finalI + 10) {
                            cbs.get(i).setSelected(false);
                        }
                    }
                }
            });
            slctX.add(cbs.get(i + 10));
        }
        this.add(slctX);


        this.add(new JLabel("y:"));
        JPanel slctY = new JPanel();
        slctY.setLayout(new GridLayout(21, 1));
        ArrayList<MyCheckBox> cbs_y = new ArrayList<>();
        for (int i = -10; i < 11; i++) {
            cbs_y.add(new MyCheckBox(i, false));
            int finalI = i;
            cbs_y.get(i + 10).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cbs_y.get(finalI + 10).x) {
                        x = cbs_y.get(finalI + 10).val;
                        System.out.println(x);
                    } else {
                        y = cbs_y.get(finalI + 10).val;
                        System.out.println(y);
                    }
                    for (int i = 0; i < cbs_y.size(); i++) {
                        if (cbs_y.get(i).isSelected() && i != finalI + 10) {
                            cbs_y.get(i).setSelected(false);
                        }
                    }
                }
            });
            slctY.add(cbs_y.get(i + 10));
        }
        this.add(slctY);

        JButton check = new JButton("Check");
        check.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CoordChecker.uPanel.addPoint((int)(((float)x+12.5)*20) + 35, (int)(((float)(-y)+12.5)*20) + 35, checkInside());
                CoordChecker.uPanel.repaint();
            }
        });
        this.add(check);
    }

    public Color checkInside() {
        if (x == 0 || y == 0) {
            if (x == 0 && y >= -5 && y <= 5)
                return Color.YELLOW;
            else if (y == 0 && (x == -5 || x == 5 || x == 10))
                return Color.YELLOW;
            else if (y == 0 && x > -5 && x < 0)
                return Color.GREEN;
            else
                return Color.RED;
        }
        if (x > 0 && y > 0) {
            if (x < 5 && y < 5 && y > -x + 5)
                return Color.GREEN;
            else if (y == -x + 5 || (x == 5 && y <= 5) || (x <= 5 && y == 5))
                return Color.YELLOW;
            else
                return Color.RED;
        }
        else if (x > 0 && y < 0) {
            if (x < 10 && y > -5 && x > 2 * y + 10)
                return Color.GREEN;
            else if (x == 2 * y + 10 || (x == 10 && y >= -5) || (x <= 10 && y == -5))
                return Color.YELLOW;
            else
                return Color.RED;
        }
        else if (x < 0 && y < 0) {
            if (x > -5 && y > -5)
                return Color.GREEN;
            else if ((x == -5 && y >= -5) || (x >= -5 && y == -5))
                return Color.YELLOW;
            else
                return Color.RED;
        }
        else {
            if (y < x + 5)
                return Color.GREEN;
            else if (y == x + 5)
                return Color.YELLOW;
            else
                return Color.RED;
        }
    }
}
