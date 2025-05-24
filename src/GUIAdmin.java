import java.awt.*;
import javax.swing.*;

public class GUIAdmin extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton historyButton;
    private JButton dataMobilButton;
    private JButton editLoginButton;
    private JButton dataKeuanganButton;
    private JButton statistikButton;
    private JButton signOutButton;
    private JLabel clockLabel;

    public GUIAdmin() {
        setTitle("Rex's Rents - Admin Dashboard");
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
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel userIcon = new JLabel(Utility.createUniformIcon("assets/logo.png", 32, 32)); // logo.png not resized
        userIcon.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(userIcon, gbc);

        ImageIcon logoutIcon = Utility.createUniformIcon("assets/logout.png", 20, 20);
        signOutButton = Utility.styleButton("Logout", Color.WHITE);
        signOutButton.setIcon(logoutIcon); // Set the icon
        signOutButton.setIconTextGap(8);
        signOutButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Text to the right of icon
        signOutButton.setVerticalTextPosition(SwingConstants.CENTER);

        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(true);
        signOutButton.addActionListener(e -> {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.initialize();
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        logoutPanel.add(signOutButton, gbc);

        headerPanel.add(userPanel, BorderLayout.WEST);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        // Menu Panel
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        ImageIcon historyIcon = Utility.createUniformIcon("assets/historiw.png", 20, 20);
        historyButton = new JButton("Histori Transaksi");
        historyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        historyButton.setIcon(historyIcon);
        historyButton.setIconTextGap(8);
        historyButton.setPreferredSize(new Dimension(200, 40));
        historyButton.setMargin(new Insets(8, 15, 8, 15));
        historyButton.setFocusPainted(false);

        ImageIcon datamobilIcon = Utility.createUniformIcon("assets/datamobil.png", 24, 20);
        dataMobilButton = new JButton("Data Mobil");
        dataMobilButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dataMobilButton.setIcon(datamobilIcon);
        dataMobilButton.setIconTextGap(8);
        dataMobilButton.setPreferredSize(new Dimension(150, 40));
        dataMobilButton.setMargin(new Insets(8, 15, 8, 15));
        dataMobilButton.setFocusPainted(false);

        ImageIcon editIcon = Utility.createUniformIcon("assets/edit.png", 24, 20);
        editLoginButton = new JButton("Edit Informasi Login Pegawai");
        editLoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editLoginButton.setIcon(editIcon);
        editLoginButton.setIconTextGap(8);
        editLoginButton.setPreferredSize(new Dimension(250, 40));
        editLoginButton.setMargin(new Insets(8, 15, 8, 15));
        editLoginButton.setFocusPainted(false);

        ImageIcon keuanganIcon = Utility.createUniformIcon("assets/keuangan.png", 20, 20);
        dataKeuanganButton = new JButton("Data Keuangan");
        dataKeuanganButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dataKeuanganButton.setIcon(keuanganIcon);
        dataKeuanganButton.setIconTextGap(8);
        dataKeuanganButton.setPreferredSize(new Dimension(180, 40));
        dataKeuanganButton.setMargin(new Insets(8, 15, 8, 15));
        dataKeuanganButton.setFocusPainted(false);

        ImageIcon statistikIcon = Utility.createUniformIcon("assets/datamobil.png", 24, 20);
        statistikButton = new JButton("Statistik");
        statistikButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        statistikButton.setIcon(statistikIcon);
        statistikButton.setIconTextGap(8);
        statistikButton.setPreferredSize(new Dimension(150, 40));
        statistikButton.setMargin(new Insets(8, 15, 8, 15));
        statistikButton.setFocusPainted(false);

        historyButton.setBackground(new Color(25, 83, 215));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorderPainted(false);

        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setBorderPainted(false);

        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setBorderPainted(false);

        dataKeuanganButton.setBackground(Color.WHITE);
        dataKeuanganButton.setBorderPainted(false);

        statistikButton.setBackground(Color.WHITE);
        statistikButton.setBorderPainted(false);

        menuPanel.add(historyButton);
        menuPanel.add(dataMobilButton);
        menuPanel.add(editLoginButton);
        menuPanel.add(dataKeuanganButton);
        menuPanel.add(statistikButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());

        JPanel historyPanel = HistoryTransaksiPanel.create();
        JPanel dataMobilPanel = DataMobilPanel.create();
        JPanel editLoginPanel = EditLoginPegawaiPanel.create();
        JPanel dataKeuanganPanel = DataKeuanganPanel.create();
        JPanel statistikPanel = StatistikPanel.create(); // Placeholder for Statistik panel

        contentPanel.add(historyPanel, "history");
        contentPanel.add(dataMobilPanel, "dataMobil");
        contentPanel.add(editLoginPanel, "editLogin");
        contentPanel.add(dataKeuanganPanel, "dataKeuangan");
        contentPanel.add(statistikPanel, "statistik");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        Utility.addButtonHoverEffect(historyButton);
        Utility.addButtonHoverEffect(dataMobilButton);
        Utility.addButtonHoverEffect(editLoginButton);
        Utility.addButtonHoverEffect(dataKeuanganButton);
        Utility.addButtonHoverEffect(statistikButton);

        historyButton.addActionListener(e -> switchPanel("history", historyButton));
        dataMobilButton.addActionListener(e -> switchPanel("dataMobil", dataMobilButton));
        editLoginButton.addActionListener(e -> switchPanel("editLogin", editLoginButton));
        dataKeuanganButton.addActionListener(e -> switchPanel("dataKeuangan", dataKeuanganButton));
        statistikButton.addActionListener(e -> switchPanel("statistik", statistikButton));

        getContentPane().add(mainPanel);
    }

    // Make this method package-private so AdminMenuBar can call it
    void switchPanel(String panelName, JButton selectedButton) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);

        // Reset all menu buttons to default (non-white) icons and styles
        historyButton.setBackground(Color.WHITE);
        historyButton.setForeground(Color.BLACK);
        historyButton.setIcon(Utility.createUniformIcon("assets/histori.png", 20, 20));

        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setForeground(Color.BLACK);
        dataMobilButton.setIcon(Utility.createUniformIcon("assets/datamobil.png", 24, 20));

        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setForeground(Color.BLACK);
        editLoginButton.setIcon(Utility.createUniformIcon("assets/edit.png", 24, 20));

        dataKeuanganButton.setBackground(Color.WHITE);
        dataKeuanganButton.setForeground(Color.BLACK);
        dataKeuanganButton.setIcon(Utility.createUniformIcon("assets/keuangan.png", 20, 20));

        statistikButton.setBackground(Color.WHITE);
        statistikButton.setForeground(Color.BLACK);
        statistikButton.setIcon(Utility.createUniformIcon("assets/statistik.png", 20, 20));

        // Set selected button to blue and use the 'w' (white) icon
        selectedButton.setBackground(new Color(25, 83, 215));
        selectedButton.setForeground(Color.WHITE);
        if (selectedButton == historyButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/historiw.png", 20, 20));
        } else if (selectedButton == dataMobilButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/datamobilw.png", 24, 20));
        } else if (selectedButton == editLoginButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/editw.png", 24, 20));
        } else if (selectedButton == dataKeuanganButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/keuanganw.png", 20, 20));
        } else if (selectedButton == statistikButton) {
            selectedButton.setIcon(Utility.createUniformIcon("assets/statistikw.png", 20, 20));
        }
    }
}