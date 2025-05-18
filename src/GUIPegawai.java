import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUIPegawai extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton tambahTransaksiButton;
    private JButton dataPelangganButton;
    private JButton kembalikanMobilButton;
    private JButton signOutButton;
    private List<Mobil> daftarMobil = Mobil.getAllMobil(); // Fetch mobil data from the database
    private JTextField idPelangganField;
    private JTable pelangganTable;
    private JLabel clockLabel;

    public GUIPegawai() {
        setTitle("Rex's Rents - Employee Dashboard");
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
        clockLabel = Utility.createClockLabel();
        headerPanel.add(clockLabel, BorderLayout.CENTER);

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
        signOutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
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

        ImageIcon tambahTransaksiIcon = Utility.createUniformIcon("assets/tambahtransaksi.png", 20, 20);
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

        ImageIcon kembalikanMobilIcon = Utility.createUniformIcon("assets/return.png", 24, 20);
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
        JPanel tambahTransaksi = tambahTransaksi();
        JPanel dataPelanggan = dataPelanggan();
        JPanel kembalikanMobil = kembalikanMobil();

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

    private JPanel tambahTransaksi() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 15);

        // Form fields
        JLabel pelangganLabel = new JLabel("Nama Pelanggan");
        JLabel idMobilLabel = new JLabel("ID Mobil");
        JLabel modelLabel = new JLabel("Model");
        JLabel merkLabel = new JLabel("Merk");
        JLabel durasiLabel = new JLabel("Durasi Sewa");
        JLabel hargaSewaLabel = new JLabel("Harga Sewa");

        formPanel.add(pelangganLabel, gbc);

        gbc.gridy++;
        formPanel.add(idMobilLabel, gbc);

        gbc.gridy++;
        formPanel.add(modelLabel, gbc);

        gbc.gridy++;
        formPanel.add(merkLabel, gbc);

        gbc.gridy++;
        formPanel.add(durasiLabel, gbc);

        gbc.gridy++;
        formPanel.add(hargaSewaLabel, gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;

        JTextField pelangganField = Utility.styleTextField(false);
        formPanel.add(pelangganField, gbc);

        gbc.gridy++;
        JTextField idMobilField = Utility.styleTextField(false);
        formPanel.add(idMobilField, gbc);

        gbc.gridy++;
        JTextField modelField = Utility.styleTextField(false);
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        JTextField merkField = Utility.styleTextField(false);
        formPanel.add(merkField, gbc);

        gbc.gridy++;
        // Replace durasiField JTextField with JComboBox for positive integers (1-30)
        Integer[] durasiOptions = new Integer[30];
        for (int i = 0; i < 30; i++)
            durasiOptions[i] = i + 1;
        JComboBox<Integer> durasiComboBox = new JComboBox<>(durasiOptions);
        durasiComboBox.setSelectedIndex(0);
        durasiComboBox.setBackground(new Color(220, 230, 250));
        durasiComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        durasiComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        durasiComboBox.setPreferredSize(new Dimension(200, 30));
        formPanel.add(durasiComboBox, gbc);

        gbc.gridy++;
        JTextField hargaSewaField = Utility.styleTextField(false);
        formPanel.add(hargaSewaField, gbc);

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 5, 5, 15);

        ImageIcon tambahIcon = Utility.createUniformIcon("assets/add.png", 15, 15);

        JButton tambahButton = Utility.styleButton("Tambah", new Color(255, 102, 0)); // Orange for "Tambah"
        tambahButton.setIcon(tambahIcon);
        tambahButton.setIconTextGap(5); // Add some space between icon and text

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(tambahButton);

        formPanel.add(buttonPanel, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNamesAtas = { "ID", "Model", "Merk", "Harga Sewa" };
        DefaultTableModel tableModelAtas = new DefaultTableModel(columnNamesAtas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // --- FOTO MOBIL PANEL ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4, 0, 0, 0);

        JPanel fotoPanel = new JPanel(new BorderLayout());
        fotoPanel.setPreferredSize(new Dimension(180, 155));
        fotoPanel.setBackground(new Color(240, 240, 240));
        fotoPanel.setBorder(BorderFactory.createLineBorder(new Color(217, 231, 244), 2, true));

        JLabel fotoLabel = new JLabel();
        fotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fotoLabel.setVerticalAlignment(SwingConstants.CENTER);
        fotoLabel.setPreferredSize(new Dimension(60, 150));
        fotoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel captionLabel = new JLabel("Pilih Mobil", SwingConstants.CENTER);
        captionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        captionLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(180, 180, 180)));

        fotoPanel.add(fotoLabel, BorderLayout.CENTER);
        fotoPanel.add(captionLabel, BorderLayout.SOUTH);

        formPanel.add(fotoPanel, gbc);

        JTable tableAtas = Utility.styleTable(tableModelAtas);
        JScrollPane scrollPaneAtas = new JScrollPane(tableAtas);

        // Populate the table atas with Mobil data
        tableModelAtas.setRowCount(0); // Clear table
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Mobil m : Mobil.getMobilTersedia()) {
            tableModelAtas.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    formatRupiah.format(m.getHargaSewa()) // Format harga sewa to Rupiah
            });
        }

        // Tabel bawah (data pelanggan)
        String[] columnNamesBawah = { "ID", "Nama", "No HP", "No KTP", "Alamat", "Gender" };
        DefaultTableModel tableModelBawah = new DefaultTableModel(columnNamesBawah, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tableBawah = Utility.styleTable(tableModelBawah);
        JScrollPane scrollPaneBawah = new JScrollPane(tableBawah);

        // Populate the table bawah with pelanggan data
        tableModelBawah.setRowCount(0); // Clear table
        for (Pelanggan p : Pelanggan.getAllPelanggan()) {
            tableModelBawah.addRow(new Object[] {
                    p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                    p.getNoKtp(), p.getAlamat(), p.getGender()
            });
        }

        // Add selection listener to the table atas
        tableAtas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableAtas.getSelectedRow();
                if (selectedRow != -1) {
                    String id = tableModelAtas.getValueAt(selectedRow, 0).toString(); // ID Mobil
                    String model = tableModelAtas.getValueAt(selectedRow, 1).toString(); // Model
                    String merk = tableModelAtas.getValueAt(selectedRow, 2).toString(); // Merk
                    String hargaSewa = tableModelAtas.getValueAt(selectedRow, 3).toString(); // Harga Sewa

                    // Remove "Rp" and thousand separators before parsing
                    hargaSewa = hargaSewa.replace("Rp", "").replace(".", "").replace(",", ".").trim();

                    idMobilField.setText(id); // Set ID Mobil to field
                    modelField.setText(model); // Set Model to field
                    merkField.setText(merk); // Set Merk to field
                    hargaSewaField.setText(formatRupiah.format(Double.parseDouble(hargaSewa))); // Format to Rupiah

                    // --- KODE FOTO & CAPTION MULAI DI SINI ---
                    Mobil selectedMobil = Mobil.getAllMobil().stream()
                            .filter(mobil -> mobil.getIdMobil().equals(id))
                            .findFirst()
                            .orElse(null);

                    if (selectedMobil != null) {
                        String fotoFile = selectedMobil.getFoto();
                        System.out.println("Foto file: " + fotoFile); // Debug
                        if (fotoFile != null && !fotoFile.isEmpty()) {
                            String path = "assets/mobil/" + fotoFile;
                            System.out.println("Path gambar: " + path); // Debug
                            File imgFile = new File(path);
                            if (!imgFile.exists()) {
                                System.out.println("File gambar tidak ditemukan: " + path);
                            }
                            ImageIcon icon = new ImageIcon(path);
                            Image img = icon.getImage();
                            if (img.getWidth(null) == -1) {
                                System.out.println("Gambar gagal di-load: " + path);
                                fotoLabel.setIcon(null);
                            } else {
                                int width = 160;
                                int height = 90;
                                // Scaling langsung tanpa BufferedImage
                                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                                fotoLabel.setIcon(new ImageIcon(scaledImg));
                            }
                        } else {
                            fotoLabel.setIcon(null);
                        }
                        captionLabel.setText(selectedMobil.getMerk() + " " + selectedMobil.getModel());
                    } else {
                        fotoLabel.setIcon(null);
                        captionLabel.setText("Pilih Mobil");
                    }
                }
            }
        });

        // Add selection listener to the table bawah
        tableBawah.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableBawah.getSelectedRow();
                if (selectedRow != -1) {
                    String namaPelanggan = tableModelBawah.getValueAt(selectedRow, 1).toString();
                    pelangganField.setText(namaPelanggan);
                }
            }
        });

        // Add action listener to the "Tambah" button
        tambahButton.addActionListener(event -> {
            String idMobil = idMobilField.getText();
            String pelangganName = pelangganField.getText();
            int durasiInt = (Integer) durasiComboBox.getSelectedItem();
            String hargaSewa = hargaSewaField.getText();

            // Validate input
            if (idMobil.isEmpty() || pelangganName.isEmpty() || hargaSewa.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Find mobil by ID
                Mobil selectedMobil = Mobil.getAllMobil().stream()
                        .filter(mobil -> mobil.getIdMobil().equals(idMobil))
                        .findFirst()
                        .orElse(null);

                if (selectedMobil == null) {
                    JOptionPane.showMessageDialog(null, "Mobil tidak ditemukan!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update mobil status to unavailable
                selectedMobil.setStatus(false);
                Mobil.updateInDatabase(selectedMobil); // Update mobil status in the database

                // Find pelanggan by name
                Pelanggan pelanggan = Pelanggan.getAllPelanggan().stream()
                        .filter(p -> p.getNama().equalsIgnoreCase(pelangganName))
                        .findFirst()
                        .orElse(null);

                if (pelanggan == null) {
                    JOptionPane.showMessageDialog(null, "Pelanggan tidak ditemukan!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create a new transaksi
                Transaksi transaksiBaru = new Transaksi(
                        Transaksi.generateNextId(),
                        java.time.LocalDate.now().toString(),
                        pelanggan,
                        selectedMobil,
                        durasiInt,
                        0.00 // No denda for new transactions
                );

                String result = Transaksi.addToDatabase(transaksiBaru); // Save transaksi to the database
                System.out.println(result);

                // Refresh tables
                refreshTambahTransaksiPanel();
                refreshKembalikanMobilPanel();

                JOptionPane.showMessageDialog(null, "Transaksi berhasil ditambahkan!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Clear form fields
                pelangganField.setText("");
                idMobilField.setText("");
                modelField.setText("");
                merkField.setText("");
                hargaSewaField.setText("");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Durasi dan Harga Sewa harus berupa angka!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });

        // Add search functionality for Mobil table (live search)
        JPanel searchPanelMobil = new JPanel(new BorderLayout());
        searchPanelMobil.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JTextField searchFieldMobil = new Utility.PlaceholderTextField("Search Mobil...");
        searchPanelMobil.add(searchFieldMobil, BorderLayout.CENTER);
        tablePanel.add(searchPanelMobil, BorderLayout.NORTH);
        searchFieldMobil.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchFieldMobil.getText().trim().toLowerCase();
                tableModelAtas.setRowCount(0);
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                // Ambil data terbaru dari database setiap kali search
                for (Mobil m : Mobil.getMobilTersedia()) {
                    String hargaSewaStr = formatRupiah.format(m.getHargaSewa());
                    // Untuk pencarian, gabungkan semua kolom dan ubah ke lower case
                    String all = (m.getIdMobil() + " " +
                            m.getModel() + " " +
                            m.getMerk() + " " +
                            hargaSewaStr).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search mobil...") || all.contains(keyword)) {
                        // Untuk display, tampilkan hargaSewaStr asli (tanpa .toLowerCase())
                        tableModelAtas.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(), hargaSewaStr
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

        // Add search functionality for Pelanggan table (live search)
        JPanel searchPanelPelanggan = new JPanel(new BorderLayout());
        searchPanelPelanggan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchFieldPelanggan = new Utility.PlaceholderTextField("Search Pelanggan...");
        searchPanelPelanggan.add(searchFieldPelanggan, BorderLayout.CENTER);
        searchFieldPelanggan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchFieldPelanggan.getText().trim().toLowerCase();
                tableModelBawah.setRowCount(0);
                for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                    String all = (p.getIdPelanggan() + " " +
                            p.getNama() + " " +
                            p.getNoHp() + " " +
                            p.getNoKtp() + " " +
                            p.getAlamat() + " " +
                            p.getGender()).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search pelanggan...") || all.contains(keyword)) {
                        tableModelBawah.addRow(new Object[] {
                                p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                                p.getNoKtp(), p.getAlamat(), p.getGender()
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

        // Add the searchPanelPelanggan above the pelanggan table
        JPanel pelangganPanel = new JPanel(new BorderLayout());
        pelangganPanel.add(searchPanelPelanggan, BorderLayout.NORTH);
        pelangganPanel.add(scrollPaneBawah, BorderLayout.CENTER);

        // Use JSplitPane to divide the table area
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneAtas, pelangganPanel);
        splitPane.setResizeWeight(0.5); // Initial size ratio 50:50
        splitPane.setDividerSize(5); // Divider size

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel dataPelanggan() {
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
        JLabel idPelangganLabel = Utility.styleLabel("ID Pelanggan");
        JLabel namaLabel = Utility.styleLabel("Nama");
        JLabel noHPLabel = Utility.styleLabel("No HP");
        JLabel noKTPLabel = Utility.styleLabel("No KTP");
        JLabel alamatLabel = Utility.styleLabel("Alamat");
        JLabel genderLabel = Utility.styleLabel("Gender");

        formPanel.add(idPelangganLabel, gbc);

        gbc.gridy++;
        formPanel.add(namaLabel, gbc);

        gbc.gridy++;
        formPanel.add(noHPLabel, gbc);

        gbc.gridy++;
        formPanel.add(noKTPLabel, gbc);

        gbc.gridy++;
        formPanel.add(alamatLabel, gbc);

        gbc.gridy++;
        formPanel.add(genderLabel, gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;

        JTextField idPelangganField = Utility.styleTextField(false);
        formPanel.add(idPelangganField, gbc);

        gbc.gridy++;
        JTextField namaField = new JTextField(20);
        formPanel.add(namaField, gbc);

        gbc.gridy++;
        JTextField noHPField = new JTextField(15);
        formPanel.add(noHPField, gbc);

        gbc.gridy++;
        JTextField noKTPField = new JTextField(16);
        formPanel.add(noKTPField, gbc);

        gbc.gridy++;
        JTextField alamatField = new JTextField(16);
        formPanel.add(alamatField, gbc);

        gbc.gridy++;
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "L", "P" });
        formPanel.add(genderComboBox, gbc);

        // Buttons
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

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = { "ID", "Nama", "No HP", "No KTP", "Alamat", "Gender" };
        DefaultTableModel pelangganTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable pelangganTable = Utility.styleTable(pelangganTableModel);
        JScrollPane scrollPane = new JScrollPane(pelangganTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with data from the database
        pelangganTableModel.setRowCount(0);
        for (Pelanggan p : Pelanggan.getAllPelanggan()) {
            pelangganTableModel.addRow(new Object[] {
                    p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                    p.getNoKtp(), p.getAlamat(), p.getGender()
            });
        }

        // Generate the next ID Pelanggan
        idPelangganField.setText(Pelanggan.generateNextId());

        // Add search functionality for Data Pelanggan (live search)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new Utility.PlaceholderTextField("Search Pelanggan...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchField.getText().trim();
                pelangganTableModel.setRowCount(0);
                if (keyword.isEmpty() || keyword.equals("Search Pelanggan...")) {
                    for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                        pelangganTableModel.addRow(new Object[] {
                                p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                                p.getNoKtp(), p.getAlamat(), p.getGender()
                        });
                    }
                } else {
                    for (Pelanggan p : Pelanggan.searchByName(keyword)) {
                        pelangganTableModel.addRow(new Object[] {
                                p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                                p.getNoKtp(), p.getAlamat(), p.getGender()
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

        // Add action listener to the tambah button
        tambahButton.addActionListener(event -> {
            String id = idPelangganField.getText();
            String nama = namaField.getText();
            String noHP = noHPField.getText();
            String noKTP = noKTPField.getText();
            String alamat = alamatField.getText();
            String gender = genderComboBox.getSelectedItem().toString();

            // Validate input
            if (nama.trim().isEmpty() || noHP.trim().isEmpty() || noKTP.trim().isEmpty() || alamat.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if ID already exists
            for (Pelanggan pelanggan : Pelanggan.getAllPelanggan()) {
                if (pelanggan.getIdPelanggan().equals(id)) {
                    JOptionPane.showMessageDialog(null, "ID Pelanggan sudah ada. Silakan gunakan ID baru.",
                            "Error", JOptionPane.ERROR_MESSAGE);

                    // Reset form
                    idPelangganField.setText(Pelanggan.generateNextId());
                    namaField.setText("");
                    noHPField.setText("");
                    noKTPField.setText("");
                    alamatField.setText("");
                    genderComboBox.setSelectedIndex(0);
                    return;
                }
            }

            // Add new pelanggan to the database
            Pelanggan pelangganBaru = new Pelanggan(nama, noHP, noKTP, alamat, gender);
            String result = Pelanggan.addToDatabase(pelangganBaru);
            System.out.println(result);

            // Refresh table
            pelangganTableModel.setRowCount(0);
            for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                pelangganTableModel.addRow(new Object[] {
                        p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                        p.getNoKtp(), p.getAlamat(), p.getGender()
                });
            }

            JOptionPane.showMessageDialog(null, "Pelanggan berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            idPelangganField.setText(Pelanggan.generateNextId());
            namaField.setText("");
            noHPField.setText("");
            noKTPField.setText("");
            alamatField.setText("");
            genderComboBox.setSelectedIndex(0);
        });

        // Add action listener to the simpan button
        simpanButton.addActionListener(e -> {
            int selectedRow = pelangganTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih pelanggan dari tabel terlebih dahulu.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = idPelangganField.getText();
            String nama = namaField.getText();
            String noHP = noHPField.getText();
            String noKTP = noKTPField.getText();
            String alamat = alamatField.getText();
            String gender = genderComboBox.getSelectedItem().toString();

            // Update pelanggan in the database
            Pelanggan pelanggan = new Pelanggan(id, nama, noHP, noKTP, alamat, gender);
            String result = Pelanggan.updateInDatabase(pelanggan);
            System.out.println(result);

            // Refresh table
            pelangganTableModel.setRowCount(0);
            for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                pelangganTableModel.addRow(new Object[] {
                        p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                        p.getNoKtp(), p.getAlamat(), p.getGender()
                });
            }

            JOptionPane.showMessageDialog(null, "Pelanggan berhasil diperbarui!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            idPelangganField.setText(Pelanggan.generateNextId());
            namaField.setText("");
            noHPField.setText("");
            noKTPField.setText("");
            alamatField.setText("");
            genderComboBox.setSelectedIndex(0);
        });

        // Add action listener to the delete button
        deleteButton.addActionListener(e -> {
            int selectedRow = pelangganTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih pelanggan yang ingin dihapus dari tabel terlebih dahulu.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = pelangganTableModel.getValueAt(selectedRow, 0).toString();

            // Delete pelanggan from the database
            String result = Pelanggan.deleteFromDatabase(id);
            System.out.println(result);

            // Refresh table
            pelangganTableModel.setRowCount(0);
            for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                pelangganTableModel.addRow(new Object[] {
                        p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                        p.getNoKtp(), p.getAlamat(), p.getGender()
                });
            }

            JOptionPane.showMessageDialog(null, "Pelanggan berhasil dihapus!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            idPelangganField.setText(Pelanggan.generateNextId());
            namaField.setText("");
            noHPField.setText("");
            noKTPField.setText("");
            alamatField.setText("");
            genderComboBox.setSelectedIndex(0);
        });

        pelangganTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = pelangganTable.getSelectedRow();
                if (selectedRow != -1) {
                    idPelangganField.setText(pelangganTableModel.getValueAt(selectedRow, 0).toString());
                    namaField.setText(pelangganTableModel.getValueAt(selectedRow, 1).toString());
                    noHPField.setText(pelangganTableModel.getValueAt(selectedRow, 2).toString());
                    noKTPField.setText(pelangganTableModel.getValueAt(selectedRow, 3).toString());
                    alamatField.setText(pelangganTableModel.getValueAt(selectedRow, 4).toString());
                    genderComboBox.setSelectedItem(pelangganTableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 4));
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel kembalikanMobil() {
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
        formPanel.add(Utility.styleLabel("ID Mobil"), gbc);
        gbc.gridy++;
        formPanel.add(Utility.styleLabel("Model"), gbc);
        gbc.gridy++;
        formPanel.add(Utility.styleLabel("Merk"), gbc);
        gbc.gridy++;
        formPanel.add(Utility.styleLabel("Harga Sewa"), gbc);
        gbc.gridy++;
        formPanel.add(Utility.styleLabel("Telat Hari"), gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField idMobilField = Utility.styleTextField(false);
        formPanel.add(idMobilField, gbc);

        gbc.gridy++;
        JTextField modelField = Utility.styleTextField(false);
        formPanel.add(modelField, gbc);

        gbc.gridy++;
        JTextField merkField = Utility.styleTextField(false);
        formPanel.add(merkField, gbc);

        gbc.gridy++;
        JTextField hargaSewaField = Utility.styleTextField(false);
        formPanel.add(hargaSewaField, gbc);

        gbc.gridy++;
        JTextField telatHariField = Utility.styleTextField(true);
        formPanel.add(telatHariField, gbc);

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        ImageIcon kembalikanIcon = Utility.createUniformIcon("assets/save.png", 15, 15);

        JButton kembalikanButton = Utility.styleButton("Kembalikan", new Color(0, 153, 76)); // Green
        kembalikanButton.setIcon(kembalikanIcon);
        kembalikanButton.setIconTextGap(5); // Add some space between icon and text

        formPanel.add(kembalikanButton, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = { "ID", "Model", "Merk", "Harga Sewa" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = Utility.styleTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Populate the table with unavailable mobil
        tableModel.setRowCount(0);
        for (Mobil mobil : Mobil.getMobilTidakTersedia()) {
            tableModel.addRow(new Object[] {
                    mobil.getIdMobil(), mobil.getModel(), mobil.getMerk(), formatRupiah.format(mobil.getHargaSewa())
            });
        }

        // Add selection listener to the table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    idMobilField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    modelField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    merkField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    hargaSewaField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        // Add action listener to the "Kembalikan" button
        kembalikanButton.addActionListener(e -> {
            String idMobil = idMobilField.getText();
            String telatHari = telatHariField.getText();

            if (idMobil.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih mobil yang ingin dikembalikan!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double denda = 0.00;

            // Calculate fine if "Telat Hari" is filled
            if (!telatHari.isEmpty()) {
                try {
                    int telat = Integer.parseInt(telatHari);
                    double hargaSewa = Double.parseDouble(
                            hargaSewaField.getText().replace("Rp", "").replace(".", "").replace(",", ".").trim());
                    denda = telat * hargaSewa * 1.5;

                    // Format fine in IDR
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                    String dendaFormatted = formatter.format(denda);

                    JOptionPane.showMessageDialog(null, "Telat mengembalikan mobil " + telat + " hari.\n"
                            + "Maka dikenakan denda sebesar " + dendaFormatted,
                            "Denda", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input Telat Hari harus berupa angka!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Update mobil status to available
            Mobil mobil = Mobil.getAllMobil().stream()
                    .filter(m -> m.getIdMobil().equals(idMobil))
                    .findFirst()
                    .orElse(null);

            if (mobil != null) {
                mobil.setStatus(true);
                Mobil.updateInDatabase(mobil); // Update mobil status in the database
            }

            // Update the corresponding Transaksi with the calculated Denda
            Transaksi transaksi = Transaksi.getAllTransaksi().stream()
                    .filter(t -> t.getMobil().getIdMobil().equals(idMobil))
                    .findFirst()
                    .orElse(null);

            if (transaksi != null) {
                transaksi.setDenda(denda);
                Transaksi.updateDendaInDatabase(denda); // Update denda in the database
            }

            // Refresh the table
            tableModel.setRowCount(0);
            for (Mobil m : Mobil.getMobilTidakTersedia()) {
                tableModel.addRow(new Object[] {
                        m.getIdMobil(), m.getModel(), m.getMerk(), formatRupiah.format(m.getHargaSewa())
                });
            }

            // Clear the fields
            idMobilField.setText("");
            modelField.setText("");
            merkField.setText("");
            hargaSewaField.setText("");
            telatHariField.setText("");

            JOptionPane.showMessageDialog(null, "Mobil berhasil dikembalikan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add search functionality for KembalikanMobil table (live search)
        JPanel searchPanelKembalikan = new JPanel(new BorderLayout());
        searchPanelKembalikan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchFieldKembalikan = new Utility.PlaceholderTextField("Search Mobil...");
        searchPanelKembalikan.add(searchFieldKembalikan, BorderLayout.CENTER);
        tablePanel.add(searchPanelKembalikan, BorderLayout.NORTH);
        searchFieldKembalikan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchFieldKembalikan.getText().trim().toLowerCase();
                tableModel.setRowCount(0);
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                for (Mobil m : Mobil.getMobilTidakTersedia()) {
                    String hargaSewaStr = formatRupiah.format(m.getHargaSewa()).toLowerCase();
                    String all = (m.getIdMobil() + " " +
                            m.getModel() + " " +
                            m.getMerk() + " " +
                            hargaSewaStr).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search mobil...") || all.contains(keyword)) {
                        tableModel.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(), hargaSewaStr
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

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private void switchPanel(String panelName, JButton selectedButton) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);

        // Reset button styles
        tambahTransaksiButton.setBackground(Color.WHITE);
        tambahTransaksiButton.setForeground(Color.BLACK);
        dataPelangganButton.setBackground(Color.WHITE);
        dataPelangganButton.setForeground(Color.BLACK);
        kembalikanMobilButton.setBackground(Color.WHITE);
        kembalikanMobilButton.setForeground(Color.BLACK);

        // Highlight selected button
        selectedButton.setBackground(new Color(25, 83, 215));
        selectedButton.setForeground(Color.WHITE);

        // Jika panel Customer Data dibuka, load data dan generate next ID pelanggan
        if (panelName.equals("dataPelanggan")) {
            if (pelangganTable != null) {
                String nextIdPelanggan = generateNextIdPelanggan();
                idPelangganField.setText(nextIdPelanggan);
            }
        }
    }

    private String generateNextIdPelanggan() {
        int maxId = 0;

        // Iterate through the list of Pelanggan to find the highest ID
        for (Pelanggan pelanggan : Pelanggan.getAllPelanggan()) {
            String id = pelanggan.getIdPelanggan();
            if (id.startsWith("P")) {
                try {
                    // Extract the numeric part of the ID after "P"
                    int numericPart = Integer.parseInt(id.substring(1));
                    maxId = Math.max(maxId, numericPart);
                } catch (NumberFormatException ignored) {
                    // Ignore invalid IDs
                }
            }
        }

        // Generate the next ID in the format "P" followed by a 3-digit number
        return String.format("P%03d", maxId + 1);
    }

    private void refreshTambahTransaksiPanel() {
        // Ambil panel "tambahTransaksi"
        JPanel tambahTransaksiPanel = (JPanel) contentPanel.getComponent(0); // Panel pertama di contentPanel

        // Ambil tablePanel dari tambahTransaksiPanel
        JPanel tablePanel = (JPanel) tambahTransaksiPanel.getComponent(1); // Komponen kedua di tambahTransaksiPanel
        // Ambil JSplitPane dari tablePanel
        JSplitPane splitPane = (JSplitPane) tablePanel.getComponent(1);
        JScrollPane scrollPane = (JScrollPane) splitPane.getTopComponent();
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        // Clear the table
        tableModel.setRowCount(0);

        // Populate the table with available mobil
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        daftarMobil = Mobil.getMobilTersedia(); // Fetch available mobil from the database
        for (Mobil mobil : daftarMobil) {
            tableModel.addRow(new Object[] {
                    mobil.getIdMobil(), mobil.getModel(), mobil.getMerk(), formatRupiah.format(mobil.getHargaSewa())
            });
        }
    }

    private void refreshKembalikanMobilPanel() {
        // Locate the table model for the kembalikanMobil panel
        JPanel kembalikanMobilPanel = (JPanel) contentPanel.getComponent(2); // Assuming it's the third panel
        JScrollPane scrollPane = (JScrollPane) ((JPanel) kembalikanMobilPanel.getComponent(1)).getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        // Clear the table
        tableModel.setRowCount(0);

        // Populate the table with unavailable mobil
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        daftarMobil = Mobil.getMobilTidakTersedia(); // Fetch unavailable mobil from the database
        for (Mobil mobil : daftarMobil) {
            tableModel.addRow(new Object[] {
                    mobil.getIdMobil(), mobil.getModel(), mobil.getMerk(), formatRupiah.format(mobil.getHargaSewa())
            });
        }
    }

    private void refreshPelangganTable(DefaultTableModel pelangganTableModel) {
        pelangganTableModel.setRowCount(0);
        for (Pelanggan p : Pelanggan.getAllPelanggan()) {
            pelangganTableModel.addRow(new Object[] {
                    p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                    p.getNoKtp(), p.getAlamat(), p.getGender()
            });
        }
    }
}