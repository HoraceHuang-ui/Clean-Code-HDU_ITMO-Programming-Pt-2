import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DiscountJournal {
    static DiscountDatasList dataList = new DiscountDatasList();
    static JList<String> jList = new JList<>();
    static JPanel windowPanel = new JPanel();
    static boolean filterOn = false;
    static ArrayList<String> filtered = new ArrayList<>();

    static JLabel mainTitle1 = new JLabel("No filter set.");
    static JLabel mainTitle2 = new JLabel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("Lab4, Huang Yiyu, 21321108");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setPreferredSize(new Dimension(600, 650));
            window.setBounds(300, 300, 600, 350);

            windowPanel.setLayout(new GridLayout(1, 3));

            jList = new JList<>();
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            DetailsPanel detailsPanel = new DetailsPanel();

            jList.addListSelectionListener(e -> {
                if (jList.getSelectedIndex() != -1)
                    detailsPanel.displayDetails(dataList.datas.get(filterOn ? dataList.filterIds.get(jList.getSelectedIndex()) : jList.getSelectedIndex()));
                else
                    detailsPanel.reset();
            });

            // Pre-added elements for testing
            dataList.add();
            dataList.edit(0, "Apple", "Apple", 5, new Date(122, 1, 1));
            dataList.add();
            dataList.edit(1, "Samsung", "Samsung", 6, new Date(122, 10, 10));
            dataList.add();
            dataList.edit(2, "Huawei", "Huawei", 7, new Date(122, 11, 11));
            jList.setListData(dataList.toArr());

            JScrollPane scrollPane = new JScrollPane(jList);

            windowPanel.add(scrollPane);
            CreateButtons();
            windowPanel.add(detailsPanel);

            window.add(windowPanel);
            window.pack();
            window.setVisible(true);
        });
    }
    private static void CreateButtons() {
        JPanel panel = new JPanel();
        GridLayout grid = new GridLayout(8, 1);
        grid.setVgap(10);
        panel.setLayout(grid);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(mainTitle1);
        panel.add(mainTitle2);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            dataList.add();
            EditDialog dialog = new EditDialog(dataList.count()-1, true);
            dialog.setVisible(true);
        });
        panel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            EditDialog dialog = new EditDialog(
                    filterOn ?
                            (jList.getSelectedIndex() == -1 ? -1 : dataList.filterIds.get(jList.getSelectedIndex()))
                            : jList.getSelectedIndex(), false);
            dialog.setVisible(true);
        });
        panel.add(editButton);
        
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            FilterDialog dialog = new FilterDialog();
            dialog.setVisible(true);
        });
        panel.add(filterButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (jList.getSelectedIndex() != -1) {
                dataList.delete(filterOn ? dataList.filterIds.get(jList.getSelectedIndex()) : jList.getSelectedIndex());
                if (filterOn) {
                    filtered.remove(jList.getSelectedIndex());
                }
                jList.setListData(filterOn ? filtered.toArray(new String[0]) : dataList.toArr());
            }
        });
        panel.add(deleteButton);

        JDialog notFoundDialog = new JDialog();
        notFoundDialog.setTitle("Error");
        notFoundDialog.add(new JLabel("    File not found!"));
        notFoundDialog.setBounds(400, 350, 200, 200);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Load from a .yml file...");
            fc.setFileFilter(new FileNameExtensionFilter("YAML/TXT File", "yml", "YML", "txt", "TXT"));
            if (fc.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION) {
                File source = fc.getSelectedFile();
                try {
                    dataList = MyYaml.loadFrom(source);
                    DiscountJournal.filterOn = false;
                    DiscountJournal.filtered.clear();
                    DiscountJournal.mainTitle1.setText("No filter set.");
                    DiscountJournal.mainTitle2.setText("");
                    DiscountJournal.jList.setListData(DiscountJournal.dataList.toArr());
                } catch (FileNotFoundException ex) {
                    notFoundDialog.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(loadButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save to a .yml file...");
            fc.setFileFilter(new FileNameExtensionFilter("YAML/TXT File", "yml", "YML", "txt", "TXT"));
            if (fc.showSaveDialog(saveButton) == JFileChooser.APPROVE_OPTION) {
                File target = fc.getSelectedFile();
                try {
                    MyYaml.saveTo(target, dataList);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(saveButton);

        windowPanel.add(panel);
    }
}

class EditDialog extends JDialog {
    public EditDialog(int id, boolean addMode) {
        super();
        setTitle(addMode ? "Add" : String.format("Edit data #%d", id+1));

        setBounds(400, 350, 200, 300);
        Container container = getContentPane();
        if (id == -1) {
            container.add(new JLabel("No data selected"));
            return;
        }
        container.setLayout(new GridLayout(9, 1));

        JTextField nameField = new JTextField(addMode ? "" : DiscountJournal.dataList.datas.get(id).name);
        JTextField shopField = new JTextField(addMode ? "" : DiscountJournal.dataList.datas.get(id).shop);
        JTextField discountField = new JTextField(addMode ? "" : String.format("%d", DiscountJournal.dataList.datas.get(id).discountPercent));
        JTextField dateField = new JTextField(addMode ? "" : String.format("%d/%d/%d",
                DiscountJournal.dataList.datas.get(id).expirationDate.getYear()+1900,
                DiscountJournal.dataList.datas.get(id).expirationDate.getMonth()+1,
                DiscountJournal.dataList.datas.get(id).expirationDate.getDate()));
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            int newId = DiscountJournal.dataList.edit(id, nameField.getText(), shopField.getText(), Integer.parseInt(discountField.getText()), DiscountDatasList.parseDate(dateField.getText()));
            if (addMode && DiscountJournal.filterOn) {
                DiscountJournal.dataList.filterIds.add(newId);
                DiscountJournal.filtered.add(DiscountJournal.dataList.listSource.get(newId));
            } else if (DiscountJournal.filterOn) {
                DiscountJournal.filtered.set(DiscountJournal.jList.getSelectedIndex(), DiscountJournal.dataList.listSource.get(newId));
            }
            DiscountJournal.jList.setListData(DiscountJournal.filterOn ? DiscountJournal.filtered.toArray(new String[0]) : DiscountJournal.dataList.toArr());
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            if (addMode)
                DiscountJournal.dataList.delete(DiscountJournal.dataList.count()-1);
            dispose();
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        buttons.add(okButton);
        buttons.add(cancelButton);

        container.add(new JLabel("Name:"));
        container.add(nameField);
        container.add(new JLabel("Shop:"));
        container.add(shopField);
        container.add(new JLabel("Discount Size:"));
        container.add(discountField);
        container.add(new JLabel("Date ( YYYY/m/d ):"));
        container.add(dateField);
        container.add(buttons);
    }
}

class FilterDialog extends JDialog {
    public FilterDialog() {
        super();
        setTitle("Filter");

        setBounds(400, 350, 400, 350);
        Container container = getContentPane();
        container.setLayout(new GridLayout(10, 1));

        JLabel title = new JLabel("Leave them blank to disable filter.");
        title.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));
        JTextField nameField = new JTextField();
        JTextField shopField = new JTextField();
        JTextField discountGreaterField = new JTextField();
        JTextField discountLessField = new JTextField();
        JTextField dateAfterField = new JTextField();
        JTextField dateBeforeField = new JTextField();

        GridLayout hGrid = new GridLayout(1, 2);
        hGrid.setHgap(10);

        JPanel discountFields = new JPanel();
        discountFields.setLayout(hGrid);
        discountFields.add(discountGreaterField);
        discountFields.add(discountLessField);

        JPanel dateFields = new JPanel();
        dateFields.setLayout(hGrid);
        dateFields.add(dateAfterField);
        dateFields.add(dateBeforeField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            DiscountJournal.filtered = DiscountJournal.dataList.filter(
                    nameField.getText(),
                    shopField.getText(),
                    discountGreaterField.getText().isEmpty() ? 0 : Integer.parseInt(discountGreaterField.getText()),
                    discountLessField.getText().isEmpty() ? 100 : Integer.parseInt(discountLessField.getText()),
                    dateAfterField.getText().isEmpty() ? null : DiscountDatasList.parseDate(dateAfterField.getText()),
                    dateBeforeField.getText().isEmpty() ? null : DiscountDatasList.parseDate(dateBeforeField.getText())
            );
            if (DiscountJournal.filtered.size() != DiscountJournal.dataList.count()) {
                DiscountJournal.filterOn = true;
                DiscountJournal.jList.setListData(DiscountJournal.filtered.toArray(new String[0]));
                DiscountJournal.mainTitle1.setText("Filter set for:");
                DiscountJournal.mainTitle2.setText(
                        (!nameField.getText().isEmpty() ? "Name, " : "") +
                        (!shopField.getText().isEmpty() ? "Shop, " : "") +
                        (!(discountGreaterField.getText().isEmpty() && discountLessField.getText().isEmpty()) ? "Size, " : "") +
                        (!(dateAfterField.getText().isEmpty() && dateBeforeField.getText().isEmpty()) ? "Date, " : ""));
                DiscountJournal.mainTitle2.setText(DiscountJournal.mainTitle2.getText().substring(0, DiscountJournal.mainTitle2.getText().length() - 2));
            } else {
                DiscountJournal.filterOn = false;
                DiscountJournal.dataList.clearFilter();
                DiscountJournal.filtered.clear();
                DiscountJournal.jList.setListData(DiscountJournal.dataList.toArr());
                DiscountJournal.mainTitle1.setText("No filter set.");
                DiscountJournal.mainTitle2.setText("");
            }
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
        });
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        buttons.add(okButton);
        buttons.add(cancelButton);

        container.add(title);
        container.add(new JLabel("Name containing:"));
        container.add(nameField);
        container.add(new JLabel("Shop containing:"));
        container.add(shopField);
        container.add(new JLabel("Discount Size ( >= / <= ):"));
        container.add(discountFields);
        container.add(new JLabel("Date ( YYYY/m/d, after / before ):"));
        container.add(dateFields);
        container.add(buttons);
    }
}