import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Utility {
    public static JLabel styleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    // Rounded JTextField
    public static class RoundedTextField extends JTextField {
        private int arc = 20;

        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 180));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
        }
    }

    public static JTextField styleTextField(boolean editable) {
        JTextField textField = new RoundedTextField(20);
        textField.setEditable(editable);
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        textField.setBackground(new Color(220, 230, 250));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return textField;
    }

    // Rounded JButton
    public static class RoundedButton extends JButton {
        private int arc = 20;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setBorder(BorderFactory.createEmptyBorder()); // Remove outline
            setFocusable(false); // Remove focus outline
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static JButton styleButton(String text, Color backgroundColor) {
        JButton button = new RoundedButton(text);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder()); // Remove outline
        button.setFocusable(false); // Remove focus outline
        button.setFocusPainted(false); // Remove focus painted outline
        button.setBorderPainted(false); // Remove border painting
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

    // Utility method to create a uniformly sized icon (except for logo.png and
    // logolandingpage.png)
    public static ImageIcon createUniformIcon(String path, int width, int height) {
        if (path.endsWith("logo.png") || path.endsWith("logolandingpage.png")) {
            return new ImageIcon(path); // Do not resize logo icons
        }
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public static class PlaceholderTextField extends JTextField {
        private final String placeholder;
        private final int arc = 20;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setText(placeholder);
            setForeground(Color.GRAY);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

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

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 180));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
        }
    }

    public static JPanel createClockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 30));
        clockLabel.setForeground(new Color(255, 102, 0));
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setForeground(new Color(255, 102, 0));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set Locale ke Indonesia
        Locale locale = new Locale("id", "ID");

        // Timer untuk update jam dan tanggal setiap detik
        Timer timer = new Timer(1000, e -> {
            java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", locale);
            String currentTime = timeFormat.format(new java.util.Date());
            String currentDate = dateFormat.format(new java.util.Date());
            clockLabel.setText(currentTime);
            dateLabel.setText(currentDate);
        });
        timer.start();

        // Set awal
        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", locale);
        String currentTime = timeFormat.format(new java.util.Date());
        String currentDate = dateFormat.format(new java.util.Date());
        clockLabel.setText(currentTime);
        dateLabel.setText(currentDate);

        panel.add(clockLabel);
        panel.add(dateLabel);

        return panel;
    }

    // untuk password show hide
    public static JPanel createPasswordTogglePanel(JPasswordField passwordField) {
        // Create panel with BorderLayout to place field and button horizontally
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.setOpaque(false);

        // Get the original preferred size of the password field
        Dimension passwordSize = passwordField.getPreferredSize();

        // Add password field to panel
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        // Create toggle button for show/hide password
        JToggleButton showPassword = new RoundedToggleButton("Tampilkan", new Color(173, 216, 230));
        showPassword.setFont(new Font("Arial", Font.PLAIN, 12)); // Adjust font
        showPassword.setFocusPainted(false);
        showPassword.setBackground(new Color(173, 216, 230)); // Light blue button
        showPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set a fixed size for the button so it doesn't change the panel size too much
        Dimension buttonSize = showPassword.getPreferredSize();
        showPassword.setPreferredSize(buttonSize);

        // Add action listener to change password visibility
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0); // Show actual password
                showPassword.setText("Hide");
            } else {
                passwordField.setEchoChar('\u2022'); // Hide password with bullet
                showPassword.setText("Show");
            }
        });

        // Add button to panel
        passwordPanel.add(showPassword, BorderLayout.EAST);

        // Set a fixed preferred size for the entire panel (password field width +
        // button width + gap)
        int panelWidth = passwordSize.width + buttonSize.width + 5; // 5 is the gap
        int panelHeight = Math.max(passwordSize.height, buttonSize.height);
        passwordPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Ensure the layout respects these sizes
        passwordPanel.setMinimumSize(passwordPanel.getPreferredSize());
        passwordPanel.setMaximumSize(passwordPanel.getPreferredSize());

        return passwordPanel;
    }

    // Rounded JPasswordField
    public static class RoundedPasswordField extends JPasswordField {
        private int arc = 20;

        public RoundedPasswordField(int columns) {
            super(columns);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 180));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
        }
    }

    // Rounded JToggleButton
    public static class RoundedToggleButton extends JToggleButton {
        private int arc = 20;

        public RoundedToggleButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        public RoundedToggleButton(String text, Color bg) {
            this(text);
            setBackground(bg);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Rounded JComboBox
    public static class RoundedComboBox<T> extends JComboBox<T> {
        private int arc = 20;

        public RoundedComboBox(T[] items) {
            super(items);
            // Use default styling, just set border to rounded
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
    }

    public static <T> JComboBox<T> styleComboBox(T[] items) {
        JComboBox<T> comboBox = new RoundedComboBox<>(items);
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Do not change background, foreground, font, or opaque
        comboBox.setBorder(BorderFactory.createEmptyBorder()); // Remove default outline
        comboBox.setFocusable(false); // Remove focus outline
        return comboBox;
    }

    // Add this class after the other inner classes
    public static class RoundedPanel extends JPanel {
        private int arc = 50;

        public RoundedPanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    // Add this utility method
    public static JPanel createRoundedPanel(LayoutManager layout, Color backgroundColor) {
        RoundedPanel panel = new RoundedPanel(layout);
        panel.setBackground(backgroundColor);
        return panel;
    }
}