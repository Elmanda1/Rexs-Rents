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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.CategoryLabelPositions;

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
        JPanel dataMobilPanel = dataMobil();
        JPanel editLoginPanel = editLoginPegawai();
        JPanel dataKeuanganPanel = dataKeuangan();
        JPanel statistikPanel = statistik(); // Placeholder for Statistik panel

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

    private JPanel dataMobil() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(14, 20, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 10, 6, 10);

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
        JComboBox<String> statusComboBox = Utility.styleComboBox(new String[] { "Available", "Unavailable" });
        formPanel.add(statusComboBox, gbc);

        // ====== TAMBAHKAN PANEL FOTO DI SINI ======
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;

        JPanel fotoPanel = new JPanel(new BorderLayout());
        fotoPanel.setPreferredSize(new Dimension(0, 173));
        fotoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 173));
        fotoPanel.setMinimumSize(new Dimension(0, 173));
        fotoPanel.setBackground(new Color(240, 240, 240));
        fotoPanel.setBorder(BorderFactory.createLineBorder(new Color(217, 231, 244), 2, true));

        JLabel fotoLabel = new JLabel();
        fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fotoLabel.setVerticalAlignment(SwingConstants.CENTER);
        fotoLabel.setPreferredSize(new Dimension(0, 150));
        fotoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel captionLabel = new JLabel("Pilih Mobil", SwingConstants.CENTER);
        captionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        captionLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(180, 180, 180)));

        fotoPanel.add(fotoLabel, BorderLayout.CENTER);
        fotoPanel.add(captionLabel, BorderLayout.SOUTH);

        formPanel.add(fotoPanel, gbc);

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

        // --- FOTO MOBIL PANEL (SAMA DENGAN GUIPegawai) ---
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;

        fotoPanel.add(fotoLabel, BorderLayout.CENTER);
        fotoPanel.add(captionLabel, BorderLayout.SOUTH);

        formPanel.add(fotoPanel, gbc);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        tablePanel.setPreferredSize(new Dimension(500, 600)); // Standardized size

        String[] columnNames = { "ID", "Model", "Merk", "Harga Sewa", "Status", "Jumlah Hari Peminjaman" };
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
                    m.isTersedia() ? "Available" : "Unavailable",
                    m.getJumlahHariPeminjaman()
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

            String result = Mobil.addToDatabase(id, model, merk, harga, isAvailable, "");
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

                    // --- FOTO & CAPTION ---
                    String id = mobilTableModel.getValueAt(selectedRow, 0).toString();
                    Mobil selectedMobil = Mobil.getAllMobil().stream()
                            .filter(m -> m.getIdMobil().equals(id))
                            .findFirst()
                            .orElse(null);

                    if (selectedMobil != null) {
                        String fotoFile = selectedMobil.getFoto(); // kolom foto di database
                        if (fotoFile != null && !fotoFile.isEmpty()) {
                            String path = "assets/mobil/" + fotoFile;
                            File imgFile = new File(path);
                            ImageIcon icon = new ImageIcon(path);
                            Image img = icon.getImage();
                            if (img.getWidth(null) != -1) {
                                int width = 510;
                                int height = 200;
                                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                                fotoLabel.setIcon(new ImageIcon(scaledImg));
                            } else {
                                fotoLabel.setIcon(null);
                            }
                        } else {
                            fotoLabel.setIcon(null);
                        }
                        captionLabel.setText(selectedMobil.getMerk() + " " + selectedMobil.getModel());
                    } else {
                        fotoLabel.setIcon(null);
                        captionLabel.setText("Pilih Mobil");
                    }
                } else {
                    fotoLabel.setIcon(null);
                    captionLabel.setText("Pilih Mobil");
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
                                m.isTersedia() ? "Available" : "Unavailable",
                                m.getJumlahHariPeminjaman()
                        });
                    }
                } else {
                    for (Mobil m : Mobil.search(keyword)) {
                        mobilTableModel.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(),
                                formatRupiah.format(m.getHargaSewa()),
                                m.isTersedia() ? "Available" : "Unavailable",
                                m.getJumlahHariPeminjaman()
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
                    m.isTersedia() ? "Available" : "Unavailable",
                    m.getJumlahHariPeminjaman()
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
        gbc.insets = new Insets(6, 10, 6, 10);
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
        profitLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));

        // Create top panel (blue)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and configure innerTopPanel with your size
        JPanel innerTopPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        innerTopPanel.setBackground(new Color(220, 230, 250));
        innerTopPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerTopPanel.setPreferredSize(new Dimension(1300, 300)); // Your original size

        JPanel jumlahPelanggan = new JPanel(new GridBagLayout());
        jumlahPelanggan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        jumlahPelanggan.setBackground(new Color(220, 230, 250));
        JPanel totalTransaksi = new JPanel(new GridBagLayout());
        totalTransaksi.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        totalTransaksi.setBackground(new Color(220, 230, 250));
        JPanel labaKotor = new JPanel(new GridBagLayout());
        labaKotor.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.LIGHT_GRAY));
        labaKotor.setBackground(new Color(220, 230, 250));
        JPanel labaBersih = new JPanel(new GridBagLayout());
        labaBersih.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
        labaBersih.setBackground(new Color(220, 230, 250));

        GridBagConstraints gbcBanyakBangetAnjing = new GridBagConstraints();

        JLabel jumlahPelangganLabel = Utility.styleLabel("Total Pelanggan");
        jumlahPelangganLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        jumlahPelangganLabel.setForeground(new Color(35, 47, 89));
        gbcBanyakBangetAnjing.gridx = 0;
        gbcBanyakBangetAnjing.insets = new Insets(55, 0, 65, 70); // Add gap between panels
        jumlahPelanggan.add(jumlahPelangganLabel, gbcBanyakBangetAnjing);

        JLabel angkaPelangganLabel = Utility.styleLabel(Pelanggan.countPelanggan() + "");
        angkaPelangganLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaPelangganLabel.setForeground(new Color(51, 100, 255));
        gbcBanyakBangetAnjing.gridx = 1;
        gbcBanyakBangetAnjing.insets = new Insets(55, 60, 65, 0); // Add gap between panels
        jumlahPelanggan.add(angkaPelangganLabel, gbcBanyakBangetAnjing);

        JLabel jumlahTransaksiLabel = Utility.styleLabel("Total Transaksi");
        jumlahTransaksiLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        jumlahTransaksiLabel.setForeground(new Color(35, 47, 89));
        gbcBanyakBangetAnjing.gridx = 0;
        gbcBanyakBangetAnjing.insets = new Insets(55, 0, 65, 70); // Add gap between panels
        totalTransaksi.add(jumlahTransaksiLabel, gbcBanyakBangetAnjing);

        JLabel angkaTransaksiLabel = Utility.styleLabel(Transaksi.countTransaksi() + "");
        angkaTransaksiLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaTransaksiLabel.setForeground(new Color(51, 100, 255));
        gbcBanyakBangetAnjing.gridx = 1;
        gbcBanyakBangetAnjing.insets = new Insets(55, 60, 65, 0); // Add gap between panels
        totalTransaksi.add(angkaTransaksiLabel, gbcBanyakBangetAnjing);

        JLabel labaKotorLabel = Utility.styleLabel("Laba Kotor");
        labaKotorLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        labaKotorLabel.setForeground(new Color(35, 47, 89));
        gbcBanyakBangetAnjing.gridy = 0;
        gbcBanyakBangetAnjing.insets = new Insets(40, 0, 0, 0); // Add gap between panels
        labaKotor.add(labaKotorLabel, gbcBanyakBangetAnjing);

        JLabel angkaKotorLabel = Utility.styleLabel(Transaksi.calculateBruto());
        angkaKotorLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaKotorLabel.setForeground(new Color(51, 100, 255));
        gbcBanyakBangetAnjing.gridy = 1;
        gbcBanyakBangetAnjing.insets = new Insets(20, 0, 40, 0); // Add gap between panels
        labaKotor.add(angkaKotorLabel, gbcBanyakBangetAnjing);

        JLabel labaBersihLabel = Utility.styleLabel("Laba Bersih");
        labaBersihLabel.setForeground(new Color(35, 47, 89));
        labaBersihLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        gbcBanyakBangetAnjing.gridy = 0;
        gbcBanyakBangetAnjing.insets = new Insets(40, 0, 0, 0); // Add gap between panels
        labaBersih.add(labaBersihLabel, gbcBanyakBangetAnjing);

        JLabel angkaBersihLabel = Utility.styleLabel(Transaksi.calculateBruto());
        angkaBersihLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaBersihLabel.setForeground(new Color(51, 100, 255));
        gbcBanyakBangetAnjing.gridy = 1;
        gbcBanyakBangetAnjing.insets = new Insets(20, 0, 40, 0); // Add gap between panels
        labaBersih.add(angkaBersihLabel, gbcBanyakBangetAnjing);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

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

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        innerTopPanel.add(labaBersih, gbc);

        // Center the innerTopPanel within topPanel
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.insets = new Insets(0, 10, 20, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.WEST;
        topPanel.add(profitLabel, innerGbc);
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        innerGbc.insets = new Insets(0, 0, 0, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(innerTopPanel, innerGbc);

        // Create bottom panel
        // Create bottom panel with GridBagLayout
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 92, 30, 92));

        // Create three panels
        JPanel leftPanel = new JPanel(new GridBagLayout());

        GridBagConstraints dalemgbc = new GridBagConstraints();
        dalemgbc.fill = GridBagConstraints.BOTH;
        dalemgbc.weightx = 1;

        JLabel mobilPopuler = Utility.styleLabel("Mobil Terlaris");
        mobilPopuler.setFont(new Font("poppinsFont", Font.BOLD, 24));
        mobilPopuler.setForeground(new Color(35, 47, 89));

        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        leftPanel.add(mobilPopuler, dalemgbc);

        JPanel mobilPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        mobilPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        mobilPanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        leftPanel.add(mobilPanel, dalemgbc);

        GridBagConstraints gbclagi = new GridBagConstraints();
        gbclagi.fill = GridBagConstraints.BOTH;

        JPanel namaMobilPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        namaMobilPanel.setPreferredSize(new Dimension(160, 120)); // Your original size
        gbclagi.gridy = 0;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        mobilPanel.add(namaMobilPanel, gbclagi);

        JLabel merkMobilLabel = Utility.styleLabel("Lamborghini");
        merkMobilLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        merkMobilLabel.setForeground(new Color(51, 40, 255));
        gbclagi.gridy = 0;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.WEST;
        namaMobilPanel.add(merkMobilLabel, gbclagi);

        JLabel modelMobiLabel = Utility.styleLabel("stargazer");
        modelMobiLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        modelMobiLabel.setForeground(new Color(51, 40, 255));
        gbclagi.gridy = 1;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.WEST;
        namaMobilPanel.add(modelMobiLabel, gbclagi);
        // panell ini jing
        JPanel sewPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        sewPanel.setPreferredSize(new Dimension(130, 120)); // slightly bigger width
        sewPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        gbclagi.gridy = 0;
        gbclagi.gridx = 1;
        gbclagi.weightx = 1;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.EAST;
        mobilPanel.add(sewPanel, gbclagi);

        GridBagConstraints gbctrus = new GridBagConstraints();

        JLabel penyewaanLabel = Utility.styleLabel("Penyewaan");
        penyewaanLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        penyewaanLabel.setForeground(new Color(35, 47, 89));
        gbctrus.gridy = 0;
        gbctrus.gridx = 0;
        gbctrus.weightx = 1.0;
        gbctrus.weighty = 0;
        gbctrus.insets = new Insets(10, 0, 0, 0);
        gbctrus.anchor = GridBagConstraints.CENTER;
        sewPanel.add(penyewaanLabel, gbctrus);

        JLabel totalsewaLabel = Utility.styleLabel("Rp.10000000000");
        totalsewaLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        totalsewaLabel.setForeground(new Color(51, 100, 255));
        gbctrus.gridy = 1;
        gbctrus.gridx = 0;
        gbctrus.weightx = 1.0;
        gbctrus.weighty = 0.5;
        gbctrus.insets = new Insets(0, 0, 0, 0);
        gbctrus.anchor = GridBagConstraints.CENTER;
        sewPanel.add(totalsewaLabel, gbctrus);

        JPanel middlePanel = new JPanel(new GridBagLayout());

        JLabel totalDendaLabel = Utility.styleLabel("Total Denda");
        totalDendaLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        totalDendaLabel.setForeground(new Color(35, 47, 89));
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        middlePanel.add(totalDendaLabel, dalemgbc);

        JPanel dendaPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        dendaPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        dendaPanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        middlePanel.add(dendaPanel, dalemgbc);

        JLabel totalDendaAngka = Utility.styleLabel(Transaksi.calculateDenda());
        totalDendaAngka.setFont(new Font("poppinsFont", Font.BOLD, 40));
        totalDendaAngka.setForeground(new Color(51, 100, 255));
        GridBagConstraints dendaGbc = new GridBagConstraints();
        dendaGbc.gridy = 0;
        dendaGbc.gridx = 0;
        dendaGbc.weightx = 1.0;
        dendaGbc.weighty = 1.0;
        dendaGbc.anchor = GridBagConstraints.CENTER;
        dendaPanel.add(totalDendaAngka, dendaGbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());

        JLabel totalMaintenanceLabel = Utility.styleLabel("Total Maintenance");
        totalMaintenanceLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        totalMaintenanceLabel.setForeground(new Color(35, 47, 89));
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        rightPanel.add(totalMaintenanceLabel, dalemgbc);

        JPanel maintenancePanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        maintenancePanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        maintenancePanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        rightPanel.add(maintenancePanel, dalemgbc);

        double totalMaintenance = Mobil.getTotalMaintenance();
        String formattedMaintenance = NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(totalMaintenance);
        JLabel maintenanceangkaLabel = Utility.styleLabel(formattedMaintenance);
        maintenanceangkaLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        maintenanceangkaLabel.setForeground(new Color(51, 100, 255));
        dendaGbc.gridy = 0;
        dendaGbc.gridx = 0;
        dendaGbc.weightx = 1.0;
        dendaGbc.weighty = 1.0;
        dendaGbc.anchor = GridBagConstraints.CENTER;
        maintenancePanel.add(maintenanceangkaLabel, dendaGbc);

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

        // Add right panel
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
    }

    private JPanel statistik() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- LEFT PANEL ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(255, 204, 0));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(150, "", "Lamborghini stargazer");
        dataset.addValue(120, "", "BMW X3");
        dataset.addValue(100, "", "Honda Civic");
        dataset.addValue(80, "", "Toyota Fortuner");
        dataset.addValue(60, "", "Hyundai IONIQ");
        dataset.addValue(40, "", "Toyota Alphard");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistik",
                "Model Mobil",
                "Jumlah Penyewaan",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);

        // Set chart title color (navy)
        barChart.getTitle().setPaint(new Color(35, 47, 89));

        // Set bar color (orange)
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 140, 0));

        // Set axis label color (blue)
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelPaint(new Color(51, 100, 255)); // "Model Mobil" label
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        domainAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        domainAxis.setTickLabelPaint(new Color(35, 47, 89)); // navy for tick labels

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelPaint(new Color(51, 100, 255)); // "Jumlah Penyewaan" label
        rangeAxis.setTickLabelFont(new Font("poppinsFont", Font.PLAIN, 12));
        rangeAxis.setTickLabelPaint(new Color(35, 47, 89)); // navy for tick labels

        // DO NOT force chartPanel width
        ChartPanel chartPanel = new ChartPanel(barChart);
        leftPanel.add(chartPanel, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        mainPanel.add(leftPanel, gbc);

        // --- RIGHT PANEL ---
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE); // optional, set your own color

        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.weightx = 1.0;
        rightGbc.fill = GridBagConstraints.BOTH;

        JPanel topRightPanel = new JPanel(new GridBagLayout());
        topRightPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        rightGbc.weighty = 0.5;
        rightGbc.gridy = 0;
        rightPanel.add(topRightPanel, rightGbc);

        JPanel bottomRightPanel = new JPanel(new GridBagLayout());
        rightGbc.gridy = 1;
        rightPanel.add(bottomRightPanel, rightGbc);

        // Add right panel with 50% weight
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        mainPanel.add(rightPanel, gbc);

        JLabel topTitle = new JLabel("Mobil Dengan Penyewaan Terbanyak");
        topTitle.setFont(new Font("poppinsFont", Font.BOLD, 22));
        topTitle.setForeground(new Color(35, 47, 89));
        GridBagConstraints topTitleGbc = new GridBagConstraints();
        topTitleGbc.gridx = 0;
        topTitleGbc.gridy = 0;
        topTitleGbc.gridwidth = 1;
        topTitleGbc.insets = new Insets(0, 10, 0, 10);
        topTitleGbc.anchor = GridBagConstraints.WEST;
        topRightPanel.add(topTitle, topTitleGbc);

        // Statistik rows for topRightPanel, start from rowGbc.gridy = 1
        GridBagConstraints rowGbc = new GridBagConstraints();
        rowGbc.gridx = 0;
        rowGbc.fill = GridBagConstraints.HORIZONTAL;
        rowGbc.weightx = 1.0;
        rowGbc.insets = new Insets(10, 10, 10, 10);

        rowGbc.gridy = 1;
        topRightPanel.add(createStatistikRow(1, "Lamborghini", "stargazer", 150), rowGbc);
        rowGbc.gridy = 2;
        topRightPanel.add(createStatistikRow(2, "BMW", "X3", 120), rowGbc);
        rowGbc.gridy = 3;
        topRightPanel.add(createStatistikRow(3, "Honda", "Civic", 100), rowGbc);
        rowGbc.gridy = 4;
        topRightPanel.add(createStatistikRow(4, "Toyota", "Fortuner", 80), rowGbc);

        // --- Add this before adding statistik rows to bottomRightPanel ---
        JLabel bottomTitle = new JLabel("Pelanggan Dengan Penyewaan Terbanyak");
        bottomTitle.setFont(new Font("poppinsFont", Font.BOLD, 22));
        bottomTitle.setForeground(new Color(35, 47, 89));
        GridBagConstraints bottomTitleGbc = new GridBagConstraints();
        bottomTitleGbc.gridx = 0;
        bottomTitleGbc.gridy = 0;
        bottomTitleGbc.gridwidth = 1;
        bottomTitleGbc.insets = new Insets(0, 10, 0, 10);
        bottomTitleGbc.anchor = GridBagConstraints.WEST;
        bottomRightPanel.add(bottomTitle, bottomTitleGbc);

        // Statistik rows for bottomRightPanel, start from bottomRowGbc.gridy = 1
        GridBagConstraints bottomRowGbc = new GridBagConstraints();
        bottomRowGbc.gridx = 0;
        bottomRowGbc.fill = GridBagConstraints.HORIZONTAL;
        bottomRowGbc.weightx = 1.0;
        bottomRowGbc.insets = new Insets(10, 10, 10, 10);

        bottomRowGbc.gridy = 1;
        bottomRightPanel.add(createStatistikRow(1, "John", "Doe", 30), bottomRowGbc);
        bottomRowGbc.gridy = 2;
        bottomRightPanel.add(createStatistikRow(2, "Jane", "Smith", 25), bottomRowGbc);
        bottomRowGbc.gridy = 3;
        bottomRightPanel.add(createStatistikRow(3, "Bob", "Wilson", 20), bottomRowGbc);
        bottomRowGbc.gridy = 4;
        bottomRightPanel.add(createStatistikRow(4, "Alice", "Brown", 18), bottomRowGbc);

        return mainPanel;
    }

    private JPanel createStatistikRow(int number, String merk, String model, int jumlahSewa) {
        JPanel rowPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rowPanel.setPreferredSize(new Dimension(0, 60)); // Height only, width will stretch

        GridBagConstraints gbc = new GridBagConstraints();

        // Number label
        JLabel numberLabel = new JLabel(number + ".");
        numberLabel.setFont(new Font("poppinsFont", Font.BOLD, 22));
        numberLabel.setForeground(new Color(51, 100, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 15);
        rowPanel.add(numberLabel, gbc);

        // Merk & model label
        JLabel carLabel = new JLabel(merk + " " + model);
        carLabel.setFont(new Font("poppinsFont", Font.BOLD, 20));
        carLabel.setForeground(new Color(51, 40, 255));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 15);
        gbc.anchor = GridBagConstraints.WEST;
        rowPanel.add(carLabel, gbc);

        // Penyewaan label
        JLabel sewaLabel = new JLabel("Penyewaan: " + jumlahSewa + " kali");
        sewaLabel.setFont(new Font("poppinsFont", Font.PLAIN, 18));
        sewaLabel.setForeground(new Color(35, 47, 89));
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        rowPanel.add(sewaLabel, gbc);

        return rowPanel;
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