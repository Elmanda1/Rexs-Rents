import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        addButtonHoverEffect(button);
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

    public static void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(Color.WHITE)) {
                    button.setBackground(new Color(240, 240, 240));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground().equals(new Color(240, 240, 240))) {
                    button.setBackground(Color.WHITE);
                }
            }
        });
    }

    public static Connection connectDB() {
        Connection conn = null;

        try {
            // Open connection to MySQL using mysql-connector-j-8.0.31.jar
            String database = "jdbc:mysql://localhost:3306/db_rexrents";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(database, username, password);

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
                if (rs.next()) {
                    System.out.println("Connected to database: " + rs.getString(1));
                }
            }
        } catch (SQLException e) {
            // Log detailed error message
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Catch any other exceptions
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        return conn; // Ensure the connection is returned without closing it here
    }

    public static class PlaceholderTextField extends JTextField {
        private final String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setText(placeholder);
            setForeground(Color.GRAY);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().equals(PlaceholderTextField.this.placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(PlaceholderTextField.this.placeholder);
                        setForeground(Color.GRAY);
                    }
                }
            });
        }
    }
}