import java.awt.*;
import java.io.File;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private Admin admin;
    private Pegawai pegawai;

    public LoginFrame(Admin admin, Pegawai pegawai) {
        this.admin = admin;
        this.pegawai = pegawai;
        initialize(); // Memanggil metode initialize di konstruktor
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

        // Set frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        add(mainPanel, BorderLayout.CENTER);

        // GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Landing Page");
        titleLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 37f)); // Larger font for title
        titleLabel.setForeground(new Color(255, 87, 51)); // Orange color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        usernameLabel.setForeground(new Color(54, 69, 79)); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(poppinsFont);
        usernameField.setBackground(new Color(220, 240, 255)); // Light blue background
        usernameField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        passwordLabel.setForeground(new Color(54, 69, 79)); // Dark gray text
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(poppinsFont);
        passwordField.setBackground(new Color(220, 240, 255)); // Light blue background
        passwordField.setForeground(Color.BLACK); // Black text
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        // Role selection using radio buttons
        JLabel roleLabel = new JLabel("Select Role");
        roleLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 16f));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(roleLabel, gbc);

        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(new Color(240, 248, 255)); // Match background color
        JRadioButton adminRadio = new JRadioButton("Admin");
        adminRadio.setFont(poppinsFont);
        adminRadio.setBackground(new Color(240, 248, 255));
        JRadioButton employeeRadio = new JRadioButton("Pegawai");
        employeeRadio.setFont(poppinsFont);
        employeeRadio.setBackground(new Color(240, 248, 255));
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(employeeRadio);
        rolePanel.add(adminRadio);
        rolePanel.add(employeeRadio);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(rolePanel, gbc);

        // Exit button
        JButton exitButton = new JButton("Keluar");
        exitButton.setFont(poppinsFont.deriveFont(Font.BOLD, 16f)); // Gunakan font Poppins
        exitButton.setBackground(new Color(173, 216, 230)); // Light blue button
        exitButton.setForeground(Color.BLACK); // Black text
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0)); // Menutup aplikasi
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(exitButton, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(poppinsFont.deriveFont(Font.BOLD, 16f)); // Gunakan font Poppins
        loginButton.setBackground(new Color(255, 87, 51)); // Orange button
        loginButton.setForeground(Color.WHITE); // White text
        loginButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(loginButton, gbc);

        // Tambahkan ActionListener ke tombol Login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = adminRadio.isSelected() ? "Admin" : employeeRadio.isSelected() ? "Employee" : "";

            // Panggil logika validasi
            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih Role!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (role.equals("Admin") && admin.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Login sukses sebagai Admin!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> new GUIAdmin().setVisible(true)); // Panggil menu admin
                dispose();
            } else if (role.equals("Employee") && pegawai.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Login sukses sebagai Employee!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> new PegawaiDashboard().setVisible(true)); // Panggil menu pegawai
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Pengisian Invalid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Set frame visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        // Buat objek Pegawai dan Admin
        Pegawai pegawai = new Pegawai("pegawai", "12345");
        Admin admin = new Admin("admin", "admin123", pegawai);

        // Buka GUI LoginFrame dengan objek admin dan pegawai
        new LoginFrame(admin, pegawai);
    }
}