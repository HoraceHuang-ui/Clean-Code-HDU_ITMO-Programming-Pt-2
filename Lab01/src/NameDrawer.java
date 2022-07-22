import javax.swing.*;
import java.awt.*;

public class NameDrawer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyPanel panel = new MyPanel();
            drawKanjis(panel);
            drawBGs(panel);

            generateWindow(panel);
        });
    }

    private static void drawKanjis(MyPanel res) {
        res.addLines(
                60,60,180,60,
                100,40,100,80,
                140,40,140,80,
                40,80,200,80,      // 艹一
                80,100,80,160,
                80,100,160,100,
                160,100,160,160,
                80,130,160,130,
                80,160,160,160,
                120,90,120,160,    // 由
                80,180,60,200,
                160,180,180,200,   // 八

                270,130,370,130,   // 一

                460,40,480,60,
                440,80,480,80,
                480,80,480,200,
                480,200,500,180,   // 言
                500,40,580,40,
                540,40,540,120,
                500,80,580,80,
                580,80,580,120,
                500,120,600,120,   // 五
                520,140,520,200,
                520,140,580,140,
                580,140,580,200,
                520,200,580,200    // 口
        );
    }
    private static void drawBGs(MyPanel res) {
        res.addRects(
                30,30,180,180,MyRect.RED,
                230,30,180,180,MyRect.GREEN,
                430,30,180,180,MyRect.BLUE
        );
    }

    private static void generateWindow(MyPanel panel) {
        JFrame window = new JFrame("21321108 Huang Yiyu");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(655, 280));
        window.getContentPane().add(panel);
        window.pack();
        window.setVisible(true);
    }
}
