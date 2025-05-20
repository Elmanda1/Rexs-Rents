import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
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
    private JButton dataKeuanganButton;
    private JButton signOutButton;
    private JLabel clockLabel;

    public GUIAdmin() {
        setTitle("Rex's Rents - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

        ImageIcon historyIcon = Utility.createUniformIcon("assets/histori.png", 20, 20);
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

        historyButton.setBackground(new Color(25, 83, 215));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorderPainted(false);

        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setBorderPainted(false);

        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setBorderPainted(false);

        dataKeuanganButton.setBackground(Color.WHITE);
        dataKeuanganButton.setBorderPainted(false);

        menuPanel.add(historyButton);
        menuPanel.add(dataMobilButton);
        menuPanel.add(editLoginButton);
        menuPanel.add(dataKeuanganButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());

        JPanel historyPanel = historyTransaksi();
        JPanel dataMobilPanel = dataMobil();
        JPanel editLoginPanel = editLoginPegawai();
        JPanel dataKeuanganPanel = dataKeuangan();

        contentPanel.add(historyPanel, "history");
        contentPanel.add(dataMobilPanel, "dataMobil");
        contentPanel.add(editLoginPanel, "editLogin");
        contentPanel.add(dataKeuanganPanel, "dataKeuangan");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        Utility.addButtonHoverEffect(historyButton);
        Utility.addButtonHoverEffect(dataMobilButton);
        Utility.addButtonHoverEffect(editLoginButton);

        historyButton.addActionListener(e -> switchPanel("history", historyButton));
        dataMobilButton.addActionListener(e -> switchPanel("dataMobil", dataMobilButton));
        editLoginButton.addActionListener(e -> switchPanel("editLogin", editLoginButton));
        dataKeuanganButton.addActionListener(e -> switchPanel("dataKeuangan", dataKeuanganButton));

        getContentPane().add(mainPanel);
    }

    private JPanel historyTransaksi() {
        List<Transaksi> transaksiList = Transaksi.getAllTransaksi();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {
                "ID Transaksi", "Tanggal", "Nama Pelanggan", "Model Mobil",
                "Merk Mobil", "Durasi (Hari)", "Harga Sewa", "Denda"
        };
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

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Transaksi t : transaksiList) {
            tableModel.addRow(new Object[] {
                    t.getIdTransaksi(),
                    t.getTanggal(),
                    t.getPelanggan().getNama(),
                    t.getMobil().getModel(),
                    t.getMobil().getMerk(),
                    t.getDurasiSewa(),
                    formatRupiah.format(t.getTotalHarga()),
                    formatRupiah.format(t.getDenda())
            });
        }

        // Add search functionality for History Transaksi (live search)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new Utility.PlaceholderTextField("Search Transaksi...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchField.getText().trim().toLowerCase();
                tableModel.setRowCount(0);
                for (Transaksi t : Transaksi.getAllTransaksi()) {
                    // Gabungkan semua kolom jadi satu string untuk pencarian
                    String all = (t.getIdTransaksi() + " " +
                            t.getTanggal() + " " +
                            t.getPelanggan().getNama() + " " +
                            t.getMobil().getModel() + " " +
                            t.getMobil().getMerk() + " " +
                            t.getDurasiSewa() + " " +
                            t.getTotalHarga() + " " +
                            t.getDenda()).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search transaksi...") || all.contains(keyword)) {
                        tableModel.addRow(new Object[] {
                                t.getIdTransaksi(),
                                t.getTanggal(),
                                t.getPelanggan().getNama(),
                                t.getMobil().getModel(),
                                t.getMobil().getMerk(),
                                t.getDurasiSewa(),
                                formatRupiah.format(t.getTotalHarga()),
                                formatRupiah.format(t.getDenda())
                        });
                    }
                }
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }
        });

        return panel;
    }

    private JPanel dataMobil() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(14, 20, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

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

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 0;
        gbc.ipady = 10;

        JTextField idMobilField = Utility.styleTextField(false);
        formPanel.add(idMobilField, gbc);

        gbc.gridy++;
        JTextField modelField = Utility.styleTextField(true);
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        JTextField merkField = Utility.styleTextField(true);
        formPanel.add(merkField, gbc);

        gbc.gridy++;
        JTextField hargaSewaField = Utility.styleTextField(true);
        formPanel.add(hargaSewaField, gbc);

        gbc.gridy++;
        JComboBox<String> statusComboBox = new JComboBox<>(new String[] { "Available", "Unavailable" });
        formPanel.add(statusComboBox, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        ImageIcon tambahIcon = Utility.createUniformIcon("assets/add.png", 15, 15);
        ImageIcon simpanIcon = Utility.createUniformIcon("assets/save.png", 15, 15);
        ImageIcon deleteIcon = Utility.createUniformIcon("assets/delete.png", 15, 15);

        JButton tambahButton = Utility.styleButton("Tambah", new Color(0, 153, 76)); // Green for "Tambah"
        tambahButton.setIcon(tambahIcon);
        tambahButton.setIconTextGap(5);

        JButton simpanButton = Utility.styleButton("Simpan", Color.RED); // Red for "Simpan"
        simpanButton.setIcon(simpanIcon);
        simpanButton.setIconTextGap(5);

        JButton deleteButton = Utility.styleButton("Delete", new Color(255, 102, 0)); // Orange for "Delete"
        deleteButton.setIcon(deleteIcon);
        deleteButton.setIconTextGap(5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(tambahButton);
        buttonPanel.add(simpanButton);
        buttonPanel.add(deleteButton);

        formPanel.add(buttonPanel, gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        tablePanel.setPreferredSize(new Dimension(500, 600)); // Standardized size

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

        mobilTableModel.setRowCount(0);
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        for (Mobil m : Mobil.getAllMobil()) {
            mobilTableModel.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    formatRupiah.format(m.getHargaSewa()),
                    m.isTersedia() ? "Available" : "Unavailable"
            });
        }

        idMobilField.setText(Mobil.generateNextId());

        tambahButton.addActionListener(e -> {
            String id = idMobilField.getText();
            String model = modelField.getText();
            String merk = merkField.getText();
            String hargaSewa = hargaSewaField.getText();
            String status = statusComboBox.getSelectedItem().toString();

            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!hargaSewa.matches("\\d+(\\.\\d+)?")) {
                JOptionPane.showMessageDialog(null, "Harga Sewa harus berupa angka!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if ID already exists
            for (Mobil mobil : Mobil.getAllMobil()) {
                if (mobil.getIdMobil().equals(id)) {
                    JOptionPane.showMessageDialog(null, "ID Mobil sudah ada. Silakan gunakan ID baru.",
                            "Error", JOptionPane.ERROR_MESSAGE);

                    // Reset form
                    modelField.setText("");
                    merkField.setText("");
                    hargaSewaField.setText("");
                    statusComboBox.setSelectedIndex(0);
                    idMobilField.setText(Mobil.generateNextId());
                    return;
                }
            }
            double harga = Double.parseDouble(hargaSewa);
            boolean isAvailable = status.equals("Available");

            String result = Mobil.addToDatabase(id, model, merk, harga, isAvailable);
            System.out.println(result);

            refreshMobilTable(mobilTableModel);

            JOptionPane.showMessageDialog(null, "Data mobil berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            modelField.setText("");
            merkField.setText("");
            hargaSewaField.setText("");
            statusComboBox.setSelectedIndex(0);
            idMobilField.setText(Mobil.generateNextId());
        });

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

            // Validasi input
            if (model.trim().isEmpty() || merk.trim().isEmpty() || hargaSewa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double harga = Double.parseDouble(hargaSewa);
            boolean isAvailable = status.equals("Available");

            // Ambil foto dari data lama (jika ada)
            Mobil mobilLama = Mobil.getAllMobil().stream()
                    .filter(m -> m.getIdMobil().equals(id))
                    .findFirst()
                    .orElse(null);

            String foto = (mobilLama != null) ? mobilLama.getFoto() : "";

            Mobil mobil = new Mobil(id, model, merk, harga, isAvailable, foto);
            String result = Mobil.updateInDatabase(mobil);
            System.out.println(result);

            refreshMobilTable(mobilTableModel);

            JOptionPane.showMessageDialog(null, "Data mobil berhasil diperbarui!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            idMobilField.setText(Mobil.generateNextId());
            modelField.setText("");
            merkField.setText("");
            hargaSewaField.setText("");
            statusComboBox.setSelectedIndex(0);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = mobilTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih mobil dari tabel terlebih dahulu",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = mobilTableModel.getValueAt(selectedRow, 0).toString();
            String result = Mobil.deleteFromDatabase(id);
            System.out.println(result);

            refreshMobilTable(mobilTableModel);

            JOptionPane.showMessageDialog(null, "Data mobil berhasil dihapus!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            idMobilField.setText(Mobil.generateNextId());
            modelField.setText("");
            merkField.setText("");
            hargaSewaField.setText("");
            statusComboBox.setSelectedIndex(0);
        });

        mobilTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mobilTable.getSelectedRow();
                if (selectedRow != -1) {
                    idMobilField.setText(mobilTableModel.getValueAt(selectedRow, 0).toString());
                    modelField.setText(mobilTableModel.getValueAt(selectedRow, 1).toString());
                    merkField.setText(mobilTableModel.getValueAt(selectedRow, 2).toString());
                    hargaSewaField.setText(mobilTableModel.getValueAt(selectedRow, 3).toString().replace("Rp", "")
                            .replace(",", "").replace(".", "").replaceAll("\\d{2}$", ""));
                    statusComboBox.setSelectedItem(mobilTableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        // Add search functionality for Data Mobil (live search)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new Utility.PlaceholderTextField("Search Mobil...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchField.getText().trim();
                mobilTableModel.setRowCount(0);
                if (keyword.isEmpty() || keyword.equals("Search Mobil...")) {
                    for (Mobil m : Mobil.getAllMobil()) {
                        mobilTableModel.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(),
                                formatRupiah.format(m.getHargaSewa()),
                                m.isTersedia() ? "Available" : "Unavailable"
                        });
                    }
                } else {
                    for (Mobil m : Mobil.search(keyword)) {
                        mobilTableModel.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(),
                                formatRupiah.format(m.getHargaSewa()),
                                m.isTersedia() ? "Available" : "Unavailable"
                        });
                    }
                }
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }
        });

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 7));
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private void refreshMobilTable(DefaultTableModel mobilTableModel) {
        mobilTableModel.setRowCount(0); // Clear the table model
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Fetch the latest data from the database
        List<Mobil> daftarMobil = Mobil.getAllMobil();

        for (Mobil m : daftarMobil) {
            mobilTableModel.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    formatRupiah.format(m.getHargaSewa()),
                    m.isTersedia() ? "Available" : "Unavailable"
            });
        }
    }

    private void refreshHistoryTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear the table model
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Fetch the latest data from the database
        List<Transaksi> daftarTransaksi = Transaksi.getAllTransaksi();

        for (Transaksi t : daftarTransaksi) {
            tableModel.addRow(new Object[] {
                    t.getIdTransaksi(),
                    t.getTanggal(),
                    t.getPelanggan().getNama(),
                    t.getMobil().getModel(),
                    t.getMobil().getMerk(),
                    t.getDurasiSewa(),
                    formatRupiah.format(t.getTotalHarga()),
                    formatRupiah.format(t.getDenda())
            });
        }
    }

    private JPanel editLoginPegawai() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = Utility.styleLabel("Username Pegawai:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = Utility.styleTextField(true);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = Utility.styleLabel("Password Pegawai:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new Utility.RoundedPasswordField(20);
        passwordField.setBackground(new Color(220, 240, 255));

        JPanel passwordPanel = Utility.createPasswordTogglePanel(passwordField);
        gbc.gridx = 1;
        panel.add(passwordPanel, gbc);

        ImageIcon simpanicon = Utility.createUniformIcon("assets/save.png", 20, 20);

        JButton saveButton = Utility.styleButton("Simpan", new Color(255, 87, 51));
        saveButton.setIcon(simpanicon);
        saveButton.setIconTextGap(5);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simpan informasi login ke database
            String result = updateLoginPegawai(username, password);
            if (result.equals("success")) {
                JOptionPane.showMessageDialog(null, "Data login pegawai berhasil diubah!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengubah data login: " + result,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private String updateLoginPegawai(String username, String password) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "UPDATE tb_akun SET username = ?, password = ? WHERE role = 'Employee'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "success";
            } else {
                result = "Username tidak ditemukan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan: " + e.getMessage();
        }
        return result;
    }

    private JPanel dataKeuangan() {

        Font poppinsFont;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Regular.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (Exception e) {
            poppinsFont = new Font("Arial", Font.PLAIN, 16); // Fallback font
        }
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel profitLabel = Utility.styleLabel("Pelanggan & Profit All Time");
        
        // Create top panel (blue)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and configure innerTopPanel with your size
        JPanel innerTopPanel = new JPanel(new GridBagLayout());
        innerTopPanel.setBackground(Color.CYAN);
        innerTopPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerTopPanel.setPreferredSize(new Dimension(1350, 300)); // Your original size

        JPanel jumlahPelanggan = new JPanel(new GridBagLayout());
        jumlahPelanggan.setBackground(Color.RED);
        JPanel totalTransaksi = new JPanel(new GridBagLayout());
        totalTransaksi.setBackground(Color.GREEN);
        JPanel labaKotor = new JPanel(new GridBagLayout());
        labaKotor.setBackground(Color.GREEN);
        JPanel labaBersih = new JPanel(new GridBagLayout());
        labaBersih.setBackground(Color.ORANGE);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0); // Add gap between panels

        // Add left panel
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        innerTopPanel.add(jumlahPelanggan, gbc);

        // Add middle panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        innerTopPanel.add(labaKotor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        innerTopPanel.add(totalTransaksi, gbc);

        // Add right panel - remove right inset for last panel
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        innerTopPanel.add(labaBersih, gbc);

        // Center the innerTopPanel within topPanel
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.insets = new Insets(0, 0, 20, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.WEST;
        topPanel.add(profitLabel, innerGbc);
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        innerGbc.insets = new Insets(0, 0, 0, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(innerTopPanel, innerGbc);

        // Create bottom panel (yellow)
            // Create bottom panel with GridBagLayout
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 92, 30, 92));
        
        // Create three panels
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints dalemgbc = new GridBagConstraints();
        dalemgbc.fill = GridBagConstraints.BOTH;
        dalemgbc.weightx = 1;
        

        JLabel mobilPopuler = Utility.styleLabel("Mobil Terlaris");
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        leftPanel.add(mobilPopuler, dalemgbc);

        JPanel mobilPanel = new JPanel();
        mobilPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        mobilPanel.setBackground(Color.YELLOW);
        dalemgbc.insets = new Insets(0, 20, 0, 20); 
        leftPanel.add(mobilPanel, dalemgbc);

        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBackground(Color.LIGHT_GRAY);

        JLabel totalDenda = Utility.styleLabel("Total Denda");
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        middlePanel.add(totalDenda, dalemgbc);

        JPanel dendaPanel = new JPanel();
        dendaPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        dendaPanel.setBackground(Color.YELLOW);
        dalemgbc.insets = new Insets(0, 20, 0, 20); 
        middlePanel.add(dendaPanel, dalemgbc);
        //JLabel denda = Utility.styleLabel("Total Maintenance");
        //dalemgbc.gridy = 1;
        //dalemgbc.insets = new Insets(0, 20, 0, 0); // Remove right gap for last panel
        //middlePanel.add(denda, dalemgbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel totalMaintenance = Utility.styleLabel("Total Maintenance");
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        rightPanel.add(totalMaintenance, dalemgbc);

        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        maintenancePanel.setBackground(Color.YELLOW);
        dalemgbc.insets = new Insets(0, 20, 0, 20); 
        rightPanel.add(maintenancePanel, dalemgbc);
        //JLabel maintenance = Utility.styleLabel("Mobil Terlaris");
        //dalemgbc.gridy = 1;
        //dalemgbc.insets = new Insets(0, 20, 0, 0); // Remove right gap for last panel
        //rightPanel.add(maintenance, dalemgbc);

        // Create constraints for the three panels
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.fill = GridBagConstraints.BOTH;
        panelGbc.weighty = 1.0;
        panelGbc.insets = new Insets(0, 0, 0, 20); // Add gap between panels

        // Add left panel
        panelGbc.gridx = 0;
        panelGbc.weightx = 1.0;
        bottomPanel.add(leftPanel, panelGbc);

        // Add middle panel
        panelGbc.gridx = 1;
        panelGbc.weightx = 1.0;
        bottomPanel.add(middlePanel, panelGbc);

        // Add right panel - remove right inset for last panel
        panelGbc.gridx = 2;
        panelGbc.weightx = 1.0;
        panelGbc.insets = new Insets(0, 0, 0, 0); // Remove right gap for last panel
        bottomPanel.add(rightPanel, panelGbc);

        // Add panels to main panel with 50-50 split
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(topPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        panel.add(bottomPanel, gbc);

        return panel;

  /*
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Create labels
        JLabel lblTotalTransaksi = Utility.styleLabel("Total Transaksi");
        lblTotalTransaksi.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        JLabel lblTotalPendapatan = Utility.styleLabel("Total Pendapatan");
        lblTotalPendapatan.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));
        JLabel lblTotalDenda = Utility.styleLabel("Total Denda");
        lblTotalDenda.setFont(poppinsFont.deriveFont(Font.BOLD, 20f));

        // Create text fields with values
        JTextField txtTotalTransaksi = Utility.styleTextField(false);
        txtTotalTransaksi.setText(String.valueOf(Transaksi.getAllTransaksi().size()));
        JTextField txtTotalPendapatan = Utility.styleTextField(false);
        txtTotalPendapatan.setText(
                NumberFormat.getCurrencyInstance(new Locale("id", "ID"))
                        .format(Transaksi.getAllTransaksi().stream()
                                .mapToDouble(Transaksi::getTotalHarga).sum()));
        JTextField txtTotalDenda = Utility.styleTextField(false);
        txtTotalDenda.setText(NumberFormat.getCurrencyInstance(new Locale("id", "ID"))
                .format(Transaksi.getAllTransaksi().stream()
                        .mapToDouble(Transaksi::getDenda).sum()));

        Dimension fieldSize = new Dimension(300, 40);

        for (JTextField field : new JTextField[] { txtTotalTransaksi, txtTotalPendapatan, txtTotalDenda }) {
            field.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            field.setPreferredSize(fieldSize);
            field.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // Add components to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(lblTotalTransaksi, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtTotalTransaksi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panel.add(lblTotalPendapatan, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtTotalPendapatan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panel.add(lblTotalDenda, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtTotalDenda, gbc);

        // Create wrapper panel for centering
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(panel);

        return wrapperPanel;*/
    }

    private void switchPanel(String panelName, JButton selectedButton) {
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
            selectedButton.setIcon(Utility.createUniformIcon("assets/keuanganw.png", 20, 20)); // No 'w' version,
                                                                                               // fallback
        }
    }
}