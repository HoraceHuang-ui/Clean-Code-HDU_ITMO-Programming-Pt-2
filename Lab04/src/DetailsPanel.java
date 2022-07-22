import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class DetailsPanel extends JPanel {
    JLabel nameDetails = new JLabel("Name: ");
    JTextField shopDetails = new JTextField("Shop: ");
    JRadioButton sizeDetails = new JRadioButton("Discount Size: ");
    JTextArea dateDetails = new JTextArea("Expiration Date: ");

    public DetailsPanel() {
        shopDetails.setEditable(false);
        dateDetails.setEditable(false);
        sizeDetails.setSelected(true);
        sizeDetails.addActionListener(e -> {
            if (!sizeDetails.isSelected()) {
                sizeDetails.setSelected(true);
            }
        });

        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(10);
        setLayout(layout);
        add(nameDetails);
        add(shopDetails);
        add(sizeDetails);
        add(dateDetails);
    }

    public void displayDetails(String name, String shop, int size, Date date) {
        nameDetails.setText("Name: " + name);
        shopDetails.setText("Shop: " + shop);
        sizeDetails.setText("Discount Size: " + size);
        dateDetails.setText(String.format("Expiration Date: %d/%d/%d", date.getYear() + 1900, date.getMonth() + 1, date.getDate()));
    }

    public void displayDetails(DiscountData data) {
        displayDetails(data.name, data.shop, data.discountPercent, data.expirationDate);
    }

    public void reset() {
        nameDetails.setText("Name: ");
        shopDetails.setText("Shop: ");
        sizeDetails.setText("Discount Size: ");
        dateDetails.setText("Expiration Date: ");
    }
}
