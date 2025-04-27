import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUIAdmin extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton historyButton;
    private JButton dataMobilButton;
    private JButton editLoginButton;
    private JButton signOutButton;
    private ArrayList<Mobil> daftarMobil = new ArrayList<>(Mobil.readFromCSV("daftarmobil.csv"));

    public GUIAdmin() {
        setTitle("Rex's Rents - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Set frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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

        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setOpaque(false);

        JPanel logoutPanel = new JPanel(new GridBagLayout());
        logoutPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // User Icon
        JLabel userIcon = new JLabel(new ImageIcon("logo.png")); // Replace with the path to your user icon
        userIcon.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(userIcon, gbc);

        // SignOut Button
        signOutButton = Utility.styleButton("Logout", Color.WHITE);
        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(true);
        signOutButton.addActionListener(e -> {
            dispose(); // Tutup GUIAdmin
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

        historyButton = new JButton("Histori Transaksi");
        historyButton.setPreferredSize(new Dimension(150, 40));
        historyButton.setMargin(new Insets(8, 15, 8, 15));
        historyButton.setFocusPainted(false);

        dataMobilButton = new JButton("Data Mobil");
        dataMobilButton.setPreferredSize(new Dimension(150, 40));
        dataMobilButton.setMargin(new Insets(8, 15, 8, 15));
        dataMobilButton.setFocusPainted(false);

        editLoginButton = new JButton("Edit Informasi Login Pegawai");
        editLoginButton.setPreferredSize(new Dimension(250, 40));
        editLoginButton.setMargin(new Insets(8, 15, 8, 15));
        editLoginButton.setFocusPainted(false);

        // Set default selected button
        historyButton.setBackground(new Color(25, 83, 215));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorderPainted(false);

        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setBorderPainted(false);

        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setBorderPainted(false);

        menuPanel.add(historyButton);
        menuPanel.add(dataMobilButton);
        menuPanel.add(editLoginButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());

        // Add panels for each menu item
        JPanel historyPanel = historyTransaksi();
        JPanel dataMobilPanel = dataMobil();
        JPanel editLoginPanel = editLoginPegawai();

        contentPanel.add(historyPanel, "history");
        contentPanel.add(dataMobilPanel, "dataMobil");
        contentPanel.add(editLoginPanel, "editLogin");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH); // Add header and menu panels to the main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Add hover effects to menu buttons
        Utility.addButtonHoverEffect(historyButton);
        Utility.addButtonHoverEffect(dataMobilButton);
        Utility.addButtonHoverEffect(editLoginButton);

        // Set action listeners
        historyButton.addActionListener(e -> switchPanel("history", historyButton));
        dataMobilButton.addActionListener(e -> switchPanel("dataMobil", dataMobilButton));
        editLoginButton.addActionListener(e -> switchPanel("editLogin", editLoginButton));

        getContentPane().add(mainPanel);
    }

    private JPanel historyTransaksi() {
        ArrayList<Transaksi> transaksiList = Transaksi.readFromCSV("transaksi.csv"); // Perbaiki path
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"ID Transaksi", "Pelanggan", "Model Mobil", "Merk Mobil", "Durasi (Hari)",
                "Total Harga" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = Utility.styleTable(tableModel);
        table.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with transaction data
        tableModel.setRowCount(0); // Clear table
        // Format Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Transaksi t : transaksiList) {
            tableModel.addRow(new Object[] {
                    t.getIdTransaksi(), t.getPelanggan().getNama(),
                    t.getModelMobil(), t.getMerkMobil(), t.getDurasiSewa(),
                    formatRupiah.format(t.getTotalHarga()) // Format harga total ke Rupiah
            });
        }

        // Label Panel
        JPanel pnlLabels = new JPanel();
        pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS)); // Vertical alignment for labels

        JLabel lblTotalTransaksi = Utility.styleLabel("Total Transaksi: " + transaksiList.size());
        JLabel lblTotalHarga = Utility.styleLabel(
                "Total Pendapatan: "
                        + formatRupiah.format(transaksiList.stream().mapToDouble(Transaksi::getTotalHarga).sum()));

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

    private JPanel dataMobil() {
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
        JLabel idMobilLabel = Utility.styleLabel("ID Mobil");
        JLabel modelLabel = Utility.styleLabel("Model");
        JLabel merkLabel = Utility.styleLabel("Merk");
        JLabel hargaSewaLabel = Utility.styleLabel("Harga Sewa");
        JLabel statusLabel = Utility.styleLabel("Status");

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
        JTextField idMobilField = Utility.styleTextField(false);
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
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Available", "Unavailable" });
        statusComboBox.setBackground(new Color(220, 230, 250));
        statusComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        formPanel.add(statusComboBox, gbc);

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        JButton tambahButton = Utility.styleButton("Tambah", (new Color(0, 153, 76))); // Green for "Tambah"
        JButton simpanButton = Utility.styleButton("Simpan", (Color.RED)); // Red for "Simpan"
        JButton deleteButton = Utility.styleButton("Delete", (new Color(255, 102, 0))); // Orange for "Delete"

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(tambahButton);
        buttonPanel.add(simpanButton);
        buttonPanel.add(deleteButton);

        formPanel.add(buttonPanel, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = { "ID", "Model", "Merk", "Harga Sewa", "Status" };
        DefaultTableModel mobilTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable mobilTable = Utility.styleTable(mobilTableModel);
        JScrollPane scrollPane = new JScrollPane(mobilTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with Mobil data
        mobilTableModel.setRowCount(0); // Clear table
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        for (Mobil m : daftarMobil) {
            mobilTableModel.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    formatRupiah.format(m.getHargaSewa()), // Format harga sewa ke Rupiah
                    m.isTersedia() ? "Available" : "Unavailable"
            });
        }

        // Generate the next ID Mobil
        String nextIdMobil = generateNextIdMobil(mobilTableModel);
        idMobilField.setText(nextIdMobil);

        // Add selection listener to the table
        mobilTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mobilTable.getSelectedRow();
                if (selectedRow != -1) {
                    idMobilField.setText(mobilTableModel.getValueAt(selectedRow, 0).toString());
                    modelField.setText(mobilTableModel.getValueAt(selectedRow, 1).toString());
                    merkField.setText(mobilTableModel.getValueAt(selectedRow, 2).toString());
                    hargaSewaField.setText(mobilTableModel.getValueAt(selectedRow, 3).toString().replace("Rp", "")
                            .replace(".", "").trim());
                    statusComboBox.setSelectedItem(mobilTableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        tambahButton.addActionListener(e -> {
            String id = idMobilField.getText();
            String model = modelField.getText();
            String merk = merkField.getText();
            String hargaSewa = hargaSewaField.getText();
            String status = statusComboBox.getSelectedItem().toString();
        
            // Validate input
            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (!model.matches("[a-zA-Z0-9 ]+")) { // Model hanya boleh mengandung huruf, angka, dan spasi
                JOptionPane.showMessageDialog(null, "Model hanya boleh mengandung huruf, angka, dan spasi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (!merk.matches("[a-zA-Z0-9 ]+")) { // Merk hanya boleh mengandung huruf, angka, dan spasi
                JOptionPane.showMessageDialog(null, "Merk hanya boleh mengandung huruf, angka, dan spasi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (!hargaSewa.matches("\\d+(\\.\\d+)?")) { // Harga sewa harus berupa angka
                JOptionPane.showMessageDialog(null, "Harga Sewa harus berupa angka!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            double harga;
            try {
                harga = Double.parseDouble(hargaSewa);
                if (harga <= 0) {
                    JOptionPane.showMessageDialog(null, "Harga Sewa harus lebih besar dari 0.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Harga Sewa harus berupa angka.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            // Check if ID already exists
            for (Mobil mobil : daftarMobil) {
                if (mobil.getIdMobil().equals(id)) {
                    JOptionPane.showMessageDialog(null, "ID Mobil sudah ada. Silakan gunakan ID baru.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        
            // Add new mobil to the list and table
            Mobil newMobil = new Mobil(id, model, merk, harga, status.equals("Available"));
            daftarMobil.add(newMobil);
        
            mobilTableModel.addRow(new Object[] {
                    newMobil.getIdMobil(), newMobil.getModel(), newMobil.getMerk(),
                    newMobil.getHargaSewa(), newMobil.isTersedia() ? "Available" : "Unavailable"
            });
        
            // Save mobil data to CSV
            Mobil.writeToCSV(daftarMobil);
        
            JOptionPane.showMessageDialog(null, "Data mobil berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        
            // Reset form
            modelField.setText("");
            merkField.setText("");
            hargaSewaField.setText("");
            statusComboBox.setSelectedIndex(0);
            idMobilField.setText(generateNextIdMobil(mobilTableModel));
        });

        // Add action listener to "Simpan" button
        simpanButton.addActionListener(e -> {
            int selectedRow = mobilTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih mobil dari tabel terlebih dahulu",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String model = modelField.getText();
            String merk = merkField.getText();
            String hargaSewa = hargaSewaField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            // Validasi input
            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!model.matches("[a-zA-Z0-9 ]+")) {
                JOptionPane.showMessageDialog(null, "Model hanya boleh mengandung huruf, angka, dan spasi.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!merk.matches("[a-zA-Z0-9 ]+")) {
                JOptionPane.showMessageDialog(null, "Merk hanya boleh mengandung huruf, angka, dan spasi.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga;
            try {
                // Hapus simbol atau format yang tidak valid (contoh: "Rp", ".", ",")
                harga = Double.parseDouble(hargaSewa.replace("Rp", "").replace(".", "").replace(",", "").trim());
                if (harga <= 0) {
                    JOptionPane.showMessageDialog(null, "Harga Sewa harus lebih besar dari 0.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Harga Sewa harus berupa angka.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Perbarui data di daftarMobil
            Mobil updatedMobil = daftarMobil.get(selectedRow);
            updatedMobil.setModel(model);
            updatedMobil.setMerk(merk);
            updatedMobil.setHargaSewa(harga);
            updatedMobil.setStatus(status.equals("Available"));

            // Perbarui data di tabel
            mobilTableModel.setValueAt(model, selectedRow, 1);
            mobilTableModel.setValueAt(merk, selectedRow, 2);
            mobilTableModel.setValueAt(harga, selectedRow, 3);
            mobilTableModel.setValueAt(status, selectedRow, 4);

            // Simpan perubahan ke file CSV
            Mobil.writeToCSV(daftarMobil);

            JOptionPane.showMessageDialog(null, "Data mobil berhasil diupdate",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add action listener to "Delete" button
        deleteButton.addActionListener(e -> {
            int selectedRow = mobilTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih mobil dari tabel terlebih dahulu",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove from table and daftarMobil
                mobilTableModel.removeRow(selectedRow);
                daftarMobil.remove(selectedRow);

                Mobil.writeToCSV(daftarMobil); // Save changes to CSV

                JOptionPane.showMessageDialog(null, "Data mobil berhasil dihapus",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields and generate the next ID
                modelField.setText("");
                merkField.setText("");
                hargaSewaField.setText("");
                statusComboBox.setSelectedIndex(0);
                idMobilField.setText(generateNextIdMobil(mobilTableModel));
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel editLoginPegawai() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load Poppins font
        Font poppinsFont;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Regular.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (Exception e) {
            poppinsFont = new Font("Arial", Font.PLAIN, 16); // Fallback font
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Change Employee Login Information");
        titleLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 30f)); // Larger font for title
        titleLabel.setForeground(new Color(255, 87, 51)); // Orange color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        // Username label and field
        gbc.gridwidth = 1; // Reset to one column
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = Utility.styleLabel("Username Pegawai:");
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        usernameField.setFont(poppinsFont);
        panel.add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = Utility.styleLabel("Password Pegawai:");
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(poppinsFont);
        panel.add(passwordField, gbc);

        // Save button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        JButton saveButton = Utility.styleButton("Simpan", new Color(255, 87, 51)); // Orange color
        panel.add(saveButton, gbc);

        // Add action listener for save button
        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validasi input
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

            // Simpan perubahan ke login.csv
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.csv"))) {
                writer.write("Username;Password;Role");
                writer.newLine();
                writer.write("admin" + ";" + "admin" + ";Admin");
                writer.newLine();
                writer.write(username + ";" + password + ";Employee");
                writer.newLine();
                JOptionPane.showMessageDialog(GUIAdmin.this, "Data login pegawai berhasil diubah dan disimpan.",
                        "Simpan Data Pegawai", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(GUIAdmin.this, "Gagal menyimpan data ke login.csv: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
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

        // Highlight selected button
        selectedButton.setBackground(new Color(25, 83, 215));
        selectedButton.setForeground(Color.WHITE);
    }

    private String generateNextIdMobil(DefaultTableModel tableModel) {
        int maxId = 0;
        for (Mobil mobil : daftarMobil) {
            String id = mobil.getIdMobil();
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
}