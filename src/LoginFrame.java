import java.awt.*;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private Map<String, String> adminCredentials = new HashMap<>();
    private Map<String, String> employeeCredentials = new HashMap<>();

    public LoginFrame() {
        loadCredentialsFromDB(); // Load login data from the database

        // Tambahkan kode ini untuk mengganti icon window & taskbar
        try {
            setIconImage(new ImageIcon("assets/icon.png").getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCredentialsFromDB() {
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT username, password, role FROM tb_akun";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username").trim();
                    String password = rs.getString("password").trim();
                    String role = rs.getString("role").trim();

                    if (role.equalsIgnoreCase("Admin")) {
                        adminCredentials.put(username, password);
                    } else if (role.equalsIgnoreCase("Employee")) {
                        employeeCredentials.put(username, password);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load credentials from database: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initialize() {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up frame properties
        setTitle("Rex's Rent Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen

        // Load Poppins font
        Font poppinsFont;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Regular.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (Exception e) {
            poppinsFont = new Font("Arial", Font.PLAIN, 16); // Fallback font
        }

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo
        JLabel userIcon = new JLabel();
        ImageIcon originalIcon = new ImageIcon("assets/logolandingpage.png"); // Path
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                (int) (originalIcon.getIconWidth() * 0.8), // 80% of original width
                (int) (originalIcon.getIconHeight() * 0.8), // 80% of original height
                Image.SCALE_SMOOTH);
        userIcon.setIcon(new ImageIcon(scaledImage));
        userIcon.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Let the logo take two columns
        mainPanel.add(userIcon, gbc);

        // Username label and text field
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust margins
        gbc.gridx = 0;
        gbc.gridy = 1; // Second row
        gbc.gridwidth = 1;

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        usernameLabel.setForeground(new Color(54, 69, 79)); // Dark gray text
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = Utility.styleTextField(true);
        usernameField.setColumns(20);
        usernameField.setFont(poppinsFont);
        usernameField.setBackground(new Color(220, 240, 255)); // Light blue background
        usernameField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        passwordLabel.setForeground(new Color(54, 69, 79)); // Dark gray text
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new Utility.RoundedPasswordField(20);
        passwordField.setFont(poppinsFont);
        passwordField.setBackground(new Color(220, 240, 255)); // Light blue background
        passwordField.setForeground(Color.BLACK); // Black text

        // panggil fungsi createPasswordTogglePanel
        JPanel passwordPanel = Utility.createPasswordTogglePanel(passwordField);

        // Tambahkan passwordPanel ke mainPanel (bukan passwordField langsung)
        gbc.gridx = 1;
        mainPanel.add(passwordPanel, gbc);

        // Role selection using radio buttons
        gbc.gridx = 0;
        gbc.gridy = 3;

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        mainPanel.add(roleLabel, gbc);

        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(new Color(240, 248, 255)); // Match background color
        JRadioButton adminRadio = new JRadioButton("Admin");
        adminRadio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminRadio.setFont(poppinsFont);
        adminRadio.setBackground(new Color(240, 248, 255));
        JRadioButton employeeRadio = new JRadioButton("Pegawai");
        employeeRadio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        employeeRadio.setFont(poppinsFont);
        employeeRadio.setBackground(new Color(240, 248, 255));
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(employeeRadio);
        rolePanel.add(adminRadio);
        rolePanel.add(employeeRadio);
        gbc.gridx = 1;
        mainPanel.add(rolePanel, gbc);

        // Exit button
        gbc.gridx = 0;
        gbc.gridy = 4;

        ImageIcon exitIcon = Utility.createUniformIcon("assets/logouthitam.png", 20, 20);

        JButton exitButton = Utility.styleButton("Exit", new Color(173, 216, 230));
        exitButton.setIcon(exitIcon); // Set the icon
        exitButton.setIconTextGap(8);
        exitButton.setFont(poppinsFont.deriveFont(Font.BOLD, 16f)); // Use Poppins font
        exitButton.setForeground(Color.BLACK); // Black text
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(exitButton.getPreferredSize().width, 38));
        exitButton.addActionListener(e -> System.exit(0)); // Close application
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(exitButton, gbc);

        // Login button
        gbc.gridx = 1;

        ImageIcon loginIcon = Utility.createUniformIcon("assets/login.png", 20, 20);

        JButton loginButton = Utility.styleButton("Login", new Color(255, 87, 51));
        loginButton.setIcon(loginIcon);
        loginButton.setIconTextGap(8);
        loginButton.setFont(poppinsFont.deriveFont(Font.BOLD, 16f)); // Use Poppins font
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(loginButton.getPreferredSize().width, 38)); // tingginy 38
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor
        mainPanel.add(loginButton, gbc);

        // Add ActionListener to the login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = adminRadio.isSelected() ? "Admin" : employeeRadio.isSelected() ? "Employee" : "";

            if (role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a role before logging in!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (role.equals("Admin")) {
                if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(null, "Successfully logged in as Admin!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.invokeLater(() -> new GUIAdmin().setVisible(true));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Admin username or password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (role.equals("Employee")) {
                if (employeeCredentials.containsKey(username) && employeeCredentials.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(null, "Successfully logged in as Employee!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.invokeLater(() -> new GUIPegawai().setVisible(true));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Employee username or password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().initialize());
    }
}