import java.awt.*;
import javax.swing.*;

public class GUIPegawai extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton tambahTransaksiButton;
    private JButton dataPelangganButton;
    private JButton kembalikanMobilButton;
    private JButton signOutButton;
    private JTextField idPelangganField;
    private JTable pelangganTable;

    public GUIPegawai() {
        setTitle("Rex's Rents - Employee Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Tambahkan kode ini untuk mengganti icon window & taskbar
        try {
            setIconImage(new ImageIcon("assets/icon.png").getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        mainPanel = new JPanel(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel clockPanel = Utility.createClockPanel();
        headerPanel.add(clockPanel, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setOpaque(false);

        JPanel logoutPanel = new JPanel(new GridBagLayout());
        logoutPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // User Icon
        JLabel userIcon = new JLabel(Utility.createUniformIcon("assets/logo.png", 32, 32)); // logo.png not resized
        userIcon.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(userIcon, gbc);

        // SignOut Button
        ImageIcon logoutIcon = Utility.createUniformIcon("assets/logout.png", 20, 20);
        signOutButton = Utility.styleButton("Logout", Color.WHITE);
        signOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutButton.setIcon(logoutIcon); // Set the icon
        signOutButton.setIconTextGap(8);
        signOutButton.setPreferredSize(new Dimension(130, 40));
        signOutButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Text to the right of icon
        signOutButton.setVerticalTextPosition(SwingConstants.CENTER);

        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(true);
        signOutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        signOutButton.setFocusPainted(false);
        // Saat logout di GUIPegawai
        signOutButton.addActionListener(e -> {
            dispose(); // Tutup GUIPegawai
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.initialize(); // Pastikan initialize() dipanggil
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span the button across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        logoutPanel.add(signOutButton, gbc);

        headerPanel.add(userPanel, BorderLayout.WEST);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        // Menu Panel
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        ImageIcon tambahTransaksiIcon = Utility.createUniformIcon("assets/tambahtransaksiw.png", 20, 20);
        tambahTransaksiButton = new JButton("Tambah Transaksi");
        tambahTransaksiButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tambahTransaksiButton.setIcon(tambahTransaksiIcon);
        tambahTransaksiButton.setIconTextGap(8);
        tambahTransaksiButton.setPreferredSize(new Dimension(200, 40));
        tambahTransaksiButton.setMargin(new Insets(8, 15, 8, 15));
        tambahTransaksiButton.setFocusPainted(false);

        ImageIcon dataPelangganIcon = Utility.createUniformIcon("assets/customer.png", 24, 20);
        dataPelangganButton = new JButton("Data Pelanggan");
        dataPelangganButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dataPelangganButton.setIcon(dataPelangganIcon);
        dataPelangganButton.setIconTextGap(8);
        dataPelangganButton.setPreferredSize(new Dimension(180, 40));
        dataPelangganButton.setMargin(new Insets(8, 15, 8, 15));
        dataPelangganButton.setFocusPainted(false);

        ImageIcon kembalikanMobilIcon = Utility.createUniformIcon("assets/return.png", 15, 20);
        kembalikanMobilButton = new JButton("Kembalikan Mobil");
        kembalikanMobilButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kembalikanMobilButton.setIcon(kembalikanMobilIcon);
        kembalikanMobilButton.setIconTextGap(8);
        kembalikanMobilButton.setPreferredSize(new Dimension(180, 40));
        kembalikanMobilButton.setMargin(new Insets(8, 15, 8, 15));
        kembalikanMobilButton.setFocusPainted(false);

        // Set default selected button
        tambahTransaksiButton.setBackground(new Color(25, 83, 215));
        tambahTransaksiButton.setForeground(Color.WHITE);
        tambahTransaksiButton.setBorderPainted(false);

        dataPelangganButton.setBackground(Color.WHITE);
        dataPelangganButton.setBorderPainted(false);

        kembalikanMobilButton.setBackground(Color.WHITE);
        kembalikanMobilButton.setBorderPainted(false);

        menuPanel.add(tambahTransaksiButton);
        menuPanel.add(dataPelangganButton);
        menuPanel.add(kembalikanMobilButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());

        // Add panels for each menu item
        JPanel tambahTransaksi = TambahTransaksiPanel.create(contentPanel);
        JPanel dataPelanggan = DataPelangganPanel.create();
        JPanel kembalikanMobil = KembalikanMobilPanel.create();

        contentPanel.add(tambahTransaksi, "NewTransaction");
        contentPanel.add(dataPelanggan, "dataPelanggan");
        contentPanel.add(kembalikanMobil, "KembalikanMobil");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH); // Add header and menu panels to the main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add hover effects to menu buttons
        Utility.addButtonHoverEffect(tambahTransaksiButton);
        Utility.addButtonHoverEffect(dataPelangganButton);
        Utility.addButtonHoverEffect(kembalikanMobilButton);

        // Set action listeners
        tambahTransaksiButton.addActionListener(e -> switchPanel("NewTransaction", tambahTransaksiButton));
        dataPelangganButton.addActionListener(e -> switchPanel("dataPelanggan", dataPelangganButton));
        kembalikanMobilButton.addActionListener(e -> switchPanel("KembalikanMobil", kembalikanMobilButton));

        getContentPane().add(mainPanel);
    }

    private void switchPanel(String panelName, JButton selectedButton) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);

        // Reset all menu buttons to default (non-white) icons and styles
        tambahTransaksiButton.setBackground(Color.WHITE);
        tambahTransaksiButton.setForeground(Color.BLACK);
        tambahTransaksiButton.setIcon(Utility.createUniformIcon("assets/tambahtransaksi.png", 20, 20));

        dataPelangganButton.setBackground(Color.WHITE);
        dataPelangganButton.setForeground(Color.BLACK);
        dataPelangganButton.setIcon(Utility.createUniformIcon("assets/customer.png", 20, 20));

        kembalikanMobilButton.setBackground(Color.WHITE);
        kembalikanMobilButton.setForeground(Color.BLACK);
        kembalikanMobilButton.setIcon(Utility.createUniformIcon("assets/return.png", 20, 20));

        // Set selected button to blue and use the 'w' (white) icon
        selectedButton.setBackground(new Color(25, 83, 215));
        selectedButton.setForeground(Color.WHITE);
        if (selectedButton == tambahTransaksiButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/tambahtransaksiw.png", 20, 20));
        } else if (selectedButton == dataPelangganButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/customerw.png", 20, 20)); // No 'w' version,
                                                                                               // fallback
        } else if (selectedButton == kembalikanMobilButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/returnw.png", 20, 20)); // No 'w' version, fallback
        }

        // Jika panel Customer Data dibuka, load data dan generate next ID pelanggan
        if (panelName.equals("dataPelanggan")) {
            if (pelangganTable != null) {
                String nextIdPelanggan = PegawaiUtility.generateNextIdPelanggan();
                idPelangganField.setText(nextIdPelanggan);
            }
        }
    }
}