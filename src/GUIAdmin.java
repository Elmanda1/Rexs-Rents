import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIAdmin extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JPanel editDataMobilPanel;
    private JButton historyButton;
    private JButton dataMobilButton;
    private JButton editLoginButton;
    private JButton editDataMobilButton;
    private JLabel userLabel;
    private JButton signOutButton;
    private ArrayList<Mobil> daftarMobil = Mobil.readFromCSV();
    private ArrayList<Transaksi> daftarTransaksi = Transaksi.readFromCSV();

    public GUIAdmin() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 83, 215));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        userLabel = new JLabel("Admin");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));

        signOutButton = new JButton("Logout");
        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(false);
        signOutButton.setBorderPainted(false);
        signOutButton.addActionListener(e -> System.exit(0)); // Logout action

        userPanel.add(userLabel);
        userPanel.add(signOutButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);

        // Menu Panel
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        historyButton = new JButton("Histori Transaksi");
        historyButton.setPreferredSize(new Dimension(150, 40));
        historyButton.setMargin(new Insets(8, 15, 8, 15));
        historyButton.setFocusPainted(false);

        dataMobilButton = new JButton("Data Mobil");
        dataMobilButton.setPreferredSize(new Dimension(150, 40));
        dataMobilButton.setMargin(new Insets(8, 15, 8, 15));
        dataMobilButton.setFocusPainted(false);

        editLoginButton = new JButton("Edit Login Pegawai");
        editLoginButton.setPreferredSize(new Dimension(180, 40));
        editLoginButton.setMargin(new Insets(8, 15, 8, 15));
        editLoginButton.setFocusPainted(false);

        editDataMobilButton = new JButton("Edit Data Mobil");
        editDataMobilButton.setPreferredSize(new Dimension(150, 40));
        editDataMobilButton.setMargin(new Insets(8, 15, 8, 15));
        editDataMobilButton.setFocusPainted(false);

        // Set default selected button
        historyButton.setBackground(new Color(25, 83, 215));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorderPainted(false);

        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setBorderPainted(false);

        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setBorderPainted(false);

        editDataMobilButton.setBackground(Color.WHITE);
        editDataMobilButton.setBorderPainted(false);

        menuPanel.add(historyButton);
        menuPanel.add(dataMobilButton);
        menuPanel.add(editLoginButton);
        menuPanel.add(editDataMobilButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());

        // Add panels for each menu item
        JPanel historyPanel = createHistoryPanel();
        JPanel dataMobilPanel = tambahDataMobil();
        JPanel editLoginPanel = createEditLoginPanel();
        editDataMobilPanel = editDataMobil();

        contentPanel.add(historyPanel, "history");
        contentPanel.add(dataMobilPanel, "dataMobil");
        contentPanel.add(editLoginPanel, "editLogin");
        contentPanel.add(editDataMobilPanel, "editDataMobil");

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.PAGE_START);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add hover effects to menu buttons
        addButtonHoverEffect(historyButton);
        addButtonHoverEffect(dataMobilButton);
        addButtonHoverEffect(editLoginButton);
        addButtonHoverEffect(editDataMobilButton);

        // Set action listeners
        historyButton.addActionListener(e -> switchPanel("history", historyButton));
        dataMobilButton.addActionListener(e -> switchPanel("dataMobil", dataMobilButton));
        editLoginButton.addActionListener(e -> switchPanel("editLogin", editLoginButton));
        editDataMobilButton.addActionListener(e -> switchPanel("editDataMobil", editDataMobilButton));

        getContentPane().add(mainPanel);
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Histori Transaksi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"No", "ID Transaksi", "Pelanggan", "Model Mobil", "Durasi (Hari)", "Total Harga"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 90, 220));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setBackground(new Color(220, 230, 250));
        table.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with transaction data
        tableModel.setRowCount(0); // Clear table
        int no = 1;
        double totalHarga = 0; // Variable to store the total harga
        for (Transaksi t : daftarTransaksi) {
            tableModel.addRow(new Object[]{
                    no++, t.getIdTransaksi(), t.getPelanggan().getNama(),
                    t.getMobil().getModel(), t.getDurasiSewa(), t.getTotalHarga()
            });
            totalHarga += t.getTotalHarga(); // Add the totalHarga of each transaction
        }

        // Label Panel
        JPanel pnlLabels = new JPanel();
        pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS)); // Vertical alignment for labels

        JLabel lblTotalTransaksi = new JLabel("Total Transaksi: " + daftarTransaksi.size());
        JLabel lblTotalHarga = new JLabel("Total Pendapatan: Rp " + totalHarga);

        // Align labels to the right
        lblTotalTransaksi.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblTotalHarga.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnlLabels.add(lblTotalTransaksi);
        pnlLabels.add(lblTotalHarga);

        // Wrap the labels in a FlowLayout panel to push them to the right
        JPanel pnlLabelsWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlLabelsWrapper.add(pnlLabels);

        // Add Table and Labels to Main Panel
        panel.add(pnlLabelsWrapper, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel tambahDataMobil() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 15);

        // Form fields
        JLabel idMobilLabel = new JLabel("ID Mobil");
        JLabel modelLabel = new JLabel("Model");
        JLabel merkLabel = new JLabel("Merk");
        JLabel hargaSewaLabel = new JLabel("Harga Sewa");
        JLabel statusLabel = new JLabel("Status");

        formPanel.add(idMobilLabel, gbc);

        gbc.gridy++;
        formPanel.add(modelLabel, gbc);

        gbc.gridy++;
        formPanel.add(merkLabel, gbc);

        gbc.gridy++;
        formPanel.add(hargaSewaLabel, gbc);

        gbc.gridy++;
        formPanel.add(statusLabel, gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;

        // ID Mobil Field (auto-generated)
        JTextField idMobilField = new JTextField(10);
        idMobilField.setEditable(false);
        idMobilField.setBackground(new Color(220, 230, 250));
        idMobilField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(idMobilField, gbc);

        gbc.gridy++;
        JTextField modelField = new JTextField(20);
        modelField.setBackground(new Color(220, 230, 250));
        modelField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        JTextField merkField = new JTextField(15);
        merkField.setBackground(new Color(220, 230, 250));
        merkField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(merkField, gbc);

        gbc.gridy++;
        JTextField hargaSewaField = new JTextField(16);
        hargaSewaField.setBackground(new Color(220, 230, 250));
        hargaSewaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(hargaSewaField, gbc);

        gbc.gridy++;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Unavailable"});
        statusComboBox.setBackground(new Color(220, 230, 250));
        statusComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        formPanel.add(statusComboBox, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);
        JButton tambahButton = new JButton("Tambah");
        tambahButton.setPreferredSize(new Dimension(100, 35));
        tambahButton.setBackground(Color.RED);
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setBorderPainted(false);
        tambahButton.setFocusPainted(false);
        formPanel.add(tambahButton, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = {"ID", "Model", "Merk", "Harga Sewa", "Status"};
        DefaultTableModel mobilTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable mobilTable = new JTable(mobilTableModel);
        mobilTable.getTableHeader().setBackground(new Color(30, 90, 220));
        mobilTable.getTableHeader().setForeground(Color.WHITE);
        mobilTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        mobilTable.setBackground(new Color(220, 230, 250));
        mobilTable.setRowHeight(30);
        mobilTable.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(mobilTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with Mobil data
        mobilTableModel.setRowCount(0); // Clear table
        AtomicInteger no = new AtomicInteger(1); // Use AtomicInteger for thread-safe increment
        for (Mobil m : daftarMobil) {
            mobilTableModel.addRow(new Object[]{
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    m.getHargaSewa(), m.isTersedia() ? "Available" : "Unavailable"
            });
        }

        // Generate the next ID Mobil
        String nextIdMobil = generateNextIdMobil(mobilTableModel);
        idMobilField.setText(nextIdMobil);

        // Add action listener to the tambah button
        tambahButton.addActionListener(e -> {
            String id = idMobilField.getText();
            String model = modelField.getText();
            String merk = merkField.getText();
            String hargaSewa = hargaSewaField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            // Validation
            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double harga = Double.parseDouble(hargaSewa);
                Mobil newMobil = new Mobil(id, model, merk, harga, status.equals("Available"));
                daftarMobil.add(newMobil);

                // Update table
                mobilTableModel.addRow(new Object[]{
                        newMobil.getIdMobil(), newMobil.getModel(), newMobil.getMerk(),
                        newMobil.getHargaSewa(), newMobil.isTersedia() ? "Available" : "Unavailable"
                });

                JOptionPane.showMessageDialog(null, "Data mobil berhasil ditambahkan",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields and generate the next ID
                modelField.setText("");
                merkField.setText("");
                hargaSewaField.setText("");
                statusComboBox.setSelectedIndex(0);
                idMobilField.setText(generateNextIdMobil(mobilTableModel));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Harga Sewa harus berupa angka",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    // Helper method to generate the next ID Mobil
    private String generateNextIdMobil(DefaultTableModel tableModel) {
        int maxId = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String id = tableModel.getValueAt(i, 0).toString();
            if (id.startsWith("M")) {
                try {
                    int numericPart = Integer.parseInt(id.substring(1));
                    maxId = Math.max(maxId, numericPart);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return "M" + (maxId + 1);
    }

    private JPanel createEditLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username Pegawai:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password Pegawai:");
        JTextField passwordField = new JTextField(15);
        JButton saveButton = new JButton("Simpan");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        // Create an instance of Admin
        Pegawai pegawai = new Pegawai("pegawai", "password");
        Admin admin = new Admin("admin", "admin123", pegawai);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate input
            if (username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(GUIAdmin.this, "Username tidak boleh kosong.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.trim().isEmpty() || password.length() < 5) {
                JOptionPane.showMessageDialog(GUIAdmin.this, "Password harus minimal 5 karakter.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            pegawai.setUsername(username);
            pegawai.setPassword(password);

            JOptionPane.showMessageDialog(GUIAdmin.this, "Data login pegawai berhasil diubah.",
                    "Simpan Data Pegawai", JOptionPane.INFORMATION_MESSAGE);
        });

        return panel;
    }

    private JPanel editDataMobil() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 15);

        // Form fields
        JLabel idMobilLabel = new JLabel("ID Mobil");
        JLabel modelLabel = new JLabel("Model");
        JLabel merkLabel = new JLabel("Merk");
        JLabel hargaSewaLabel = new JLabel("Harga Sewa");
        JLabel statusLabel = new JLabel("Status");

        formPanel.add(idMobilLabel, gbc);

        gbc.gridy++;
        formPanel.add(modelLabel, gbc);

        gbc.gridy++;
        formPanel.add(merkLabel, gbc);

        gbc.gridy++;
        formPanel.add(hargaSewaLabel, gbc);

        gbc.gridy++;
        formPanel.add(statusLabel, gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;

        JTextField idMobilField = new JTextField(10);
        idMobilField.setText("Pilih pada tabel atau kosongkan saja");
        idMobilField.setEditable(false);
        idMobilField.setBackground(new Color(220, 230, 250));
        idMobilField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(idMobilField, gbc);

        gbc.gridy++;
        JTextField modelField = new JTextField(20);
        modelField.setBackground(new Color(220, 230, 250));
        modelField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        JTextField merkField = new JTextField(15);
        merkField.setBackground(new Color(220, 230, 250));
        merkField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(merkField, gbc);

        gbc.gridy++;
        JTextField hargaSewaField = new JTextField(16);
        hargaSewaField.setBackground(new Color(220, 230, 250));
        hargaSewaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(hargaSewaField, gbc);

        gbc.gridy++;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Unavailable"});
        statusComboBox.setBackground(new Color(220, 230, 250));
        statusComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        formPanel.add(statusComboBox, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);
        JButton simpanButton = new JButton("Simpan");
        simpanButton.setPreferredSize(new Dimension(100, 35));
        simpanButton.setBackground(Color.RED);
        simpanButton.setForeground(Color.WHITE);
        simpanButton.setBorderPainted(false);
        simpanButton.setFocusPainted(false);
        formPanel.add(simpanButton, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = {"ID", "Model", "Merk", "Harga Sewa", "Status"};
        DefaultTableModel mobilTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable mobilTable = new JTable(mobilTableModel);
        mobilTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mobilTable.getTableHeader().setBackground(new Color(30, 90, 220));
        mobilTable.getTableHeader().setForeground(Color.BLACK);
        mobilTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        mobilTable.setBackground(new Color(220, 230, 250));
        mobilTable.setRowHeight(30);
        mobilTable.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(mobilTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Populate the table with Mobil data
        mobilTableModel.setRowCount(0); // Clear table
        AtomicInteger no = new AtomicInteger(1); // Use AtomicInteger for thread-safe increment
        for (Mobil m : daftarMobil) {
            mobilTableModel.addRow(new Object[]{
                    no.getAndIncrement(), m.getIdMobil(), m.getModel(), m.getMerk(),
                    m.getHargaSewa(), m.isTersedia() ? "Available" : "Unavailable"
            });
        }

        // Add selection listener to the table
        mobilTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mobilTable.getSelectedRow();
                if (selectedRow != -1) {
                    idMobilField.setText(mobilTableModel.getValueAt(selectedRow, 0).toString());
                    modelField.setText(mobilTableModel.getValueAt(selectedRow, 1).toString());
                    merkField.setText(mobilTableModel.getValueAt(selectedRow, 2).toString());
                    hargaSewaField.setText(mobilTableModel.getValueAt(selectedRow, 3).toString());
                    statusComboBox.setSelectedItem(mobilTableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        // Add action listener to the simpan button
        simpanButton.addActionListener(e -> {
            int selectedRow = mobilTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih mobil dari tabel terlebih dahulu",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = idMobilField.getText();
            String model = modelField.getText();
            String merk = merkField.getText();
            String hargaSewa = hargaSewaField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            // Validation
            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update table model
            mobilTableModel.setValueAt(model, selectedRow, 1);
            mobilTableModel.setValueAt(merk, selectedRow, 2);
            mobilTableModel.setValueAt(hargaSewa, selectedRow, 3);
            mobilTableModel.setValueAt(status, selectedRow, 4);

            JOptionPane.showMessageDialog(null, "Data mobil berhasil diupdate",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private void addButtonHoverEffect(JButton button) {
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

    private void switchPanel(String panelName, JButton selectedButton) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);

        // Reset button styles
        historyButton.setBackground(Color.WHITE);
        historyButton.setForeground(Color.BLACK);
        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setForeground(Color.BLACK);
        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setForeground(Color.BLACK);
        editDataMobilButton.setBackground(Color.WHITE);
        editDataMobilButton.setForeground(Color.BLACK);

        // Highlight selected button
        selectedButton.setBackground(new Color(25, 83, 215));
        selectedButton.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIAdmin());
    }
}