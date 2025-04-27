import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Utility {
    public static JLabel styleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    public static JTextField styleTextField(boolean editable) {
        JTextField textField = new JTextField(20);
        textField.setEditable(editable);
        textField.setBackground(new Color(220, 230, 250));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }
    
    public static JButton styleButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public static JTable styleTable(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(30, 90, 220)); // Header background color
        table.getTableHeader().setForeground(Color.BLACK); // Header text color
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12)); // Header font
        table.setBackground(new Color(220, 230, 250)); // Table background color
        table.setRowHeight(30); // Row height
        table.setGridColor(new Color(200, 200, 200)); // Grid color
        return table;
    }
}