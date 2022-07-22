import javax.swing.*;

public class MyCheckBox extends JCheckBox {
    public int val;
    public boolean x; // false = y
    public MyCheckBox(int v, boolean b) {
        super("");
        val = v;
        x = b;
        this.setText(String.format("%d", val));
    }
    public MyCheckBox() {
        super("");
        val = 0;
        x = false;
    }
}
