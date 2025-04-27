import java.awt.*;
import java.util.ArrayList;
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
    private ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>(Pelanggan.readFromCSV());
    private ArrayList<Mobil> daftarMobil = new ArrayList<>(Mobil.readFromCSV("daftarmobil.csv"));
    private ArrayList<Transaksi> daftarTransaksi = new ArrayList<>(Transaksi.readFromCSV("transaksi.csv"));
    private JTextField durasiField, hargaSewaField;
    private JTextField idPelangganField;
    private JTable pelangganTable;

    public GUIPegawai() {
        setTitle("Rex's Rents - Employee Dashboard");
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
        signOutButton = new JButton("Logout");
        signOutButton.setForeground(Color.RED);
        signOutButton.setBackground(Color.WHITE);
        signOutButton.setContentAreaFilled(true);
        signOutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        signOutButton.setFocusPainted(false);
        // Saat logout di GUIPegawai
        signOutButton.addActionListener(e -> {
            dispose(); // Tutup GUIPegawai
            LoginFrame loginFrame = new LoginFrame(null, null);
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

        tambahTransaksiButton = new JButton("New Transaction");
        tambahTransaksiButton.setPreferredSize(new Dimension(200, 40));
        tambahTransaksiButton.setMargin(new Insets(8, 15, 8, 15));
        tambahTransaksiButton.setFocusPainted(false);

        dataPelangganButton = new JButton("Customer Data");
        dataPelangganButton.setPreferredSize(new Dimension(150, 40));
        dataPelangganButton.setMargin(new Insets(8, 15, 8, 15));
        dataPelangganButton.setFocusPainted(false);

        kembalikanMobilButton = new JButton("Car Return");
        kembalikanMobilButton.setPreferredSize(new Dimension(250, 40));
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
        addButtonHoverEffect(tambahTransaksiButton);
        addButtonHoverEffect(dataPelangganButton);
        addButtonHoverEffect(kembalikanMobilButton);

        // Set action listeners
        tambahTransaksiButton.addActionListener(e -> switchPanel("NewTransaction", tambahTransaksiButton));
        dataPelangganButton.addActionListener(e -> switchPanel("dataPelanggan", dataPelangganButton));
        kembalikanMobilButton.addActionListener(e -> switchPanel("kembalikanMobil", kembalikanMobilButton));

        getContentPane().add(mainPanel);
    }

    private JPanel tambahTransaksi() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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
        JLabel durasiLabel = new JLabel("Durasi sewa");
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

        // PelangganField
        JTextField pelangganField = new JTextField(10);
        pelangganField.setEditable(false);
        pelangganField.setBackground(new Color(220, 230, 250));
        pelangganField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(pelangganField, gbc);

        gbc.gridy++;
        // ID Mobil Field (auto-generated)
        JTextField idMobilField = new JTextField(10);
        idMobilField.setEditable(false);
        idMobilField.setBackground(new Color(220, 230, 250));
        idMobilField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(idMobilField, gbc);
        // model
        gbc.gridy++;
        JTextField modelField = new JTextField(20);
        modelField.setEditable(false);
        modelField.setBackground(new Color(220, 230, 250));
        modelField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(modelField, gbc);

        // merk
        gbc.gridy++;
        JTextField merkField = new JTextField(15);
        merkField.setEditable(false);
        merkField.setBackground(new Color(220, 230, 250));
        merkField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(merkField, gbc);

        // durasi
        gbc.gridy++;
        durasiField = new JTextField(10);
        durasiField.setBackground(new Color(220, 230, 250));
        durasiField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(durasiField, gbc);

        // hargasewa
        gbc.gridy++;
        hargaSewaField = new JTextField(16);
        hargaSewaField.setEditable(false);
        hargaSewaField.setBackground(new Color(220, 230, 250));
        hargaSewaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(hargaSewaField, gbc);

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        JButton tambahButton = new JButton("Tambah");
        tambahButton.setPreferredSize(new Dimension(100, 35));
        tambahButton.setBackground(new Color(255, 102, 0)); // oren for "Tambah"
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setBorderPainted(false);
        tambahButton.setFocusPainted(false);

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

        JTable tableAtas = new JTable(tableModelAtas);
        tableAtas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAtas.getTableHeader().setBackground(new Color(30, 90, 220));
        tableAtas.getTableHeader().setForeground(Color.WHITE);
        tableAtas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableAtas.setBackground(new Color(220, 230, 250));
        tableAtas.setRowHeight(30);
        tableAtas.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPaneAtas = new JScrollPane(tableAtas);

        // Populate the table atas with Mobil data
        tableModelAtas.setRowCount(0); // Clear table
        for (Mobil m : daftarMobil) {
            tableModelAtas.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    m.getHargaSewa()
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

        JTable tableBawah = new JTable(tableModelBawah);
        tableBawah.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBawah.getTableHeader().setBackground(new Color(30, 90, 220));
        tableBawah.getTableHeader().setForeground(Color.WHITE);
        tableBawah.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableBawah.setBackground(new Color(220, 230, 250));
        tableBawah.setRowHeight(30);
        tableBawah.setGridColor(new Color(200, 200, 200));
        JScrollPane scrollPaneBawah = new JScrollPane(tableBawah);

        // Populate the table bawah with pelanggan data
        tableModelBawah.setRowCount(0); // Clear table
        for (Pelanggan p : daftarPelanggan) {
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

                    idMobilField.setText(id); // Set ID Mobil to the field
                    modelField.setText(model); // Set Model to the field
                    merkField.setText(merk); // Set Merk to the field
                    hargaSewaField.setText(hargaSewa); // Set Harga Sewa to the field
                }
            }
        });

        // Add selection listener to the table bawah
        tableBawah.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableBawah.getSelectedRow();
                if (selectedRow != -1) {
                    // Ambil nama pelanggan dari kolom ke-1 (index 1) tabel bawah
                    String namaPelanggan = tableModelBawah.getValueAt(selectedRow, 1).toString();

                    // Set nama pelanggan ke namaPelangganField
                    pelangganField.setText(namaPelanggan);
                }
            }
        });

        // listener hargasewafield
        hargaSewaField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }
        });

        // listener durasifield
        durasiField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateHargaSewa();
            }
        });

        tambahButton.addActionListener(event -> {
            String namaPelanggan = pelangganField.getText();
            String idMobil = idMobilField.getText();
            String durasi = durasiField.getText();
            String hargaSewa = hargaSewaField.getText();

            // Validasi input
            if (namaPelanggan.isEmpty() || idMobil.isEmpty() || durasi.isEmpty() || hargaSewa.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int durasiInt = Integer.parseInt(durasi);

                // Cari mobil berdasarkan ID
                Mobil mobil = daftarMobil.stream()
                        .filter(m -> m.getIdMobil().equals(idMobil))
                        .findFirst()
                        .orElse(null);

                if (mobil == null) {
                    JOptionPane.showMessageDialog(null, "Mobil tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pelanggan pelanggan = daftarPelanggan.stream()
                        .filter(p -> p.getNama().equals(namaPelanggan))
                        .findFirst()
                        .orElse(null);

                if (pelanggan == null) {
                    JOptionPane.showMessageDialog(null, "Pelanggan tidak ditemukan!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Buat transaksi baru
                Transaksi transaksi = new Transaksi(java.time.LocalDate.now().toString(), null, pelanggan, mobil,
                        durasiInt);
                daftarTransaksi.add(transaksi);

                // Tandai mobil sebagai disewa
                mobil.setStatus(false);

                // Simpan perubahan status mobil ke file CSV
                Mobil.writeToCSV(daftarMobil);

                // Simpan transaksi ke file CSV
                Transaksi.writeToCSV(daftarTransaksi);

                JOptionPane.showMessageDialog(null, "Transaksi berhasil ditambahkan!", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset form setelah transaksi berhasil ditambahkan
                pelangganField.setText("");
                idMobilField.setText("");
                modelField.setText("");
                merkField.setText("");
                durasiField.setText("");
                hargaSewaField.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Durasi dan Harga Sewa harus berupa angka!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Gunakan JSplitPane untuk membagi area tabel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneAtas,
                scrollPaneBawah);
        splitPane.setResizeWeight(0.5); // Proporsi ukuran awal 50:50
        splitPane.setDividerSize(5); // Ukuran garis pembagi

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    // menghitung hargasewa
    private void calculateHargaSewa() {
        if (durasiField == null || hargaSewaField == null) {
            return;
        }

        try {
            int durasi = Integer.parseInt(durasiField.getText().trim());
            int hargaPerHari = Integer.parseInt(hargaSewaField.getText().trim());
            int totalHarga = durasi * hargaPerHari;

            java.text.NumberFormat formatter = java.text.NumberFormat.getInstance();
            String totalFormatted = formatter.format(totalHarga);

        } catch (NumberFormatException e) {
        }
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
        JLabel idPelangganLabel = new JLabel("ID Pelanggan");
        JLabel namaLabel = new JLabel("Nama");
        JLabel noHPLabel = new JLabel("No HP");
        JLabel noKTPLabel = new JLabel("No KTP");
        JLabel alamatLabel = new JLabel("Alamat");
        JLabel genderLabel = new JLabel("Gender");

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

        // ID Pelanggan Field (auto-generated)
        idPelangganField = new JTextField(10); // Inisialisasi variabel instance
        idPelangganField.setEditable(false);
        idPelangganField.setBackground(new Color(220, 230, 250));
        idPelangganField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        gbc.gridx = 0;
        formPanel.add(idPelangganLabel, gbc); // Tambahkan label
        gbc.gridx = 1;
        formPanel.add(idPelangganField, gbc);

        gbc.gridy++;
        JTextField namaField = new JTextField(20);
        namaField.setBackground(new Color(220, 230, 250));
        namaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(namaField, gbc);

        gbc.gridy++;
        JTextField noHPField = new JTextField(15);
        noHPField.setBackground(new Color(220, 230, 250));
        noHPField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(noHPField, gbc);

        gbc.gridy++;
        JTextField noKTPField = new JTextField(16);
        noKTPField.setBackground(new Color(220, 230, 250));
        noKTPField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(noKTPField, gbc);

        gbc.gridy++;
        JTextField alamatField = new JTextField(16);
        alamatField.setBackground(new Color(220, 230, 250));
        alamatField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(alamatField, gbc);

        gbc.gridy++;
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] { "L", "P" });
        genderComboBox.setBackground(new Color(220, 230, 250));
        genderComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        formPanel.add(genderComboBox, gbc);

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        JButton tambahButton = new JButton("Tambah");
        tambahButton.setPreferredSize(new Dimension(100, 35));
        tambahButton.setBackground(new Color(0, 153, 76)); // Green for "Tambah"
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setBorderPainted(false);
        tambahButton.setFocusPainted(false);

        JButton simpanButton = new JButton("Simpan");
        simpanButton.setPreferredSize(new Dimension(100, 35));
        simpanButton.setBackground(Color.RED); // Red for "Simpan"
        simpanButton.setForeground(Color.WHITE);
        simpanButton.setBorderPainted(false);
        simpanButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 35));
        deleteButton.setBackground(new Color(255, 102, 0)); // Orange for "Delete"
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);

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

        JTable pelangganTable = new JTable(pelangganTableModel);
        pelangganTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pelangganTable.getTableHeader().setBackground(new Color(30, 90, 220));
        pelangganTable.getTableHeader().setForeground(Color.WHITE);
        pelangganTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        pelangganTable.setBackground(new Color(220, 230, 250));
        pelangganTable.setRowHeight(30);
        pelangganTable.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(pelangganTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Generate the next ID Pelanggan
        String nextIdPelanggan = generateNextIdPelanggan(pelangganTableModel);
        idPelangganField.setText(nextIdPelanggan);

        // Populate the table with Pelanggan data
        loadPelangganData(pelangganTableModel);

        // action listener memilih tabel
        pelangganTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = pelangganTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Ambil data dari baris yang dipilih
                    String id = pelangganTableModel.getValueAt(selectedRow, 0).toString();
                    String nama = pelangganTableModel.getValueAt(selectedRow, 1).toString();
                    String noHP = pelangganTableModel.getValueAt(selectedRow, 2).toString();
                    String noKTP = pelangganTableModel.getValueAt(selectedRow, 3).toString();
                    String alamat = pelangganTableModel.getValueAt(selectedRow, 4).toString();
                    String gender = pelangganTableModel.getValueAt(selectedRow, 5).toString();

                    // Masukkan data ke text field
                    idPelangganField.setText(generateNextIdPelanggan(pelangganTableModel));
                    namaField.setText(nama);
                    noHPField.setText(noHP);
                    noKTPField.setText(noKTP);
                    alamatField.setText(alamat);
                    genderComboBox.setSelectedItem(gender);
                }
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

            // Validasi input
            if (nama.trim().isEmpty() || noHP.trim().isEmpty() || noKTP.trim().isEmpty() || alamat.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Periksa apakah ID pelanggan sudah ada di tabel
            boolean idExists = false;
            for (int i = 0; i < pelangganTableModel.getRowCount(); i++) {
                if (pelangganTableModel.getValueAt(i, 0).toString().equals(id)) {
                    idExists = true;
                    break;
                }
            }

            if (idExists) {
                JOptionPane.showMessageDialog(null, "ID Pelanggan sudah ada. Silakan gunakan ID baru.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tambahkan pelanggan baru ke daftar dan tabel
            Pelanggan pelangganBaru = new Pelanggan(nama, noHP, noKTP, alamat, gender);
            daftarPelanggan.add(pelangganBaru); // Tambahkan ke daftar pelanggan

            pelangganTableModel.addRow(new Object[] {
                    pelangganBaru.getIdPelanggan(),
                    pelangganBaru.getNama(),
                    pelangganBaru.getNoHp(),
                    pelangganBaru.getNoKtp(),
                    pelangganBaru.getAlamat(),
                    pelangganBaru.getGender()
            });

            // Simpan data pelanggan ke file CSV
            savePelangganData(pelangganTableModel);

            JOptionPane.showMessageDialog(null, "Pelanggan berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Reset form setelah pelanggan berhasil ditambahkan
            idPelangganField.setText("");
            namaField.setText("");
            noHPField.setText("");
            noKTPField.setText("");
            alamatField.setText("");
            genderComboBox.setSelectedIndex(0);
            idPelangganField.setText(generateNextIdPelanggan(pelangganTableModel));
        });

        // Add action listener to "Simpan" button
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

            // Validasi input
            if (nama.trim().isEmpty() || noHP.trim().isEmpty() || noKTP.trim().isEmpty() || alamat.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Perbarui data di tabel
            pelangganTableModel.setValueAt(id, selectedRow, 0);
            pelangganTableModel.setValueAt(nama, selectedRow, 1);
            pelangganTableModel.setValueAt(noHP, selectedRow, 2);
            pelangganTableModel.setValueAt(noKTP, selectedRow, 3);
            pelangganTableModel.setValueAt(alamat, selectedRow, 4);
            pelangganTableModel.setValueAt(gender, selectedRow, 5);

            // Simpan perubahan ke file CSV
            savePelangganData(pelangganTableModel);

            JOptionPane.showMessageDialog(null, "Data pelanggan berhasil diperbarui.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Kosongkan form setelah simpan
            idPelangganField.setText(generateNextIdPelanggan(pelangganTableModel));
            namaField.setText("");
            noHPField.setText("");
            noKTPField.setText("");
            alamatField.setText("");
            genderComboBox.setSelectedIndex(0);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = pelangganTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih pelanggan yang ingin dihapus dari tabel terlebih dahulu.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus pelanggan ini?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                ((DefaultTableModel) pelangganTable.getModel()).removeRow(selectedRow);

                savePelangganData((DefaultTableModel) pelangganTable.getModel());

                JOptionPane.showMessageDialog(null, "Pelanggan berhasil dihapus.",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Reset form setelah hapus
                idPelangganField.setText(generateNextIdPelanggan((DefaultTableModel) pelangganTable.getModel()));
                namaField.setText("");
                noHPField.setText("");
                noKTPField.setText("");
                alamatField.setText("");
                genderComboBox.setSelectedIndex(0);
            }
        });

        panel.add(formPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    private void loadPelangganData(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data

        try {
            java.nio.file.Path path = java.nio.file.Paths.get("daftarpelanggan.csv");
            java.util.List<String> lines = java.nio.file.Files.readAllLines(path);

            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] data = line.split(";");

                if (data.length >= 6) {
                    model.addRow(data);
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading customer data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePelangganData(DefaultTableModel model) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("daftarpelanggan.csv");
            if (!java.nio.file.Files.exists(path)) {
                java.nio.file.Files.createFile(path);
            }

            java.util.List<String> lines = new java.util.ArrayList<>();
            lines.add("ID;Nama;NoHP;NoKTP;Alamat;Gender");

            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    sb.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) {
                        sb.append(";");
                    }
                }
                lines.add(sb.toString());
            }

            java.nio.file.Files.write(path, lines);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving customer data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
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

        // label
        JLabel idtransaksiLabel = new JLabel("Transaction ID");
        JLabel modelLabel = new JLabel("Model");
        JLabel merkLabel = new JLabel("Merk");
        JLabel dendaLabel = new JLabel("Fine");

        formPanel.add(idtransaksiLabel, gbc);

        gbc.gridy++;
        formPanel.add(modelLabel, gbc);

        gbc.gridy++;
        formPanel.add(merkLabel, gbc);

        gbc.gridy++;
        formPanel.add(dendaLabel, gbc);

        // Form input fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;

        // ID transaksi Field (auto-generated)
        JTextField idtransaksiField = new JTextField(10);
        idtransaksiField.setEditable(false);
        idtransaksiField.setBackground(new Color(220, 230, 250));
        idtransaksiField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(idtransaksiField, gbc);
        // model field
        gbc.gridy++;
        JTextField modelField = new JTextField(20);
        modelField.setEditable(false);
        modelField.setBackground(new Color(220, 230, 250));
        modelField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(modelField, gbc);
        // merk field
        gbc.gridy++;
        JTextField merkField = new JTextField(15);
        merkField.setBackground(new Color(220, 230, 250));
        merkField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(merkField, gbc);
        // denda field
        gbc.gridy++;
        JTextField dendaField = new JTextField(16);
        dendaField.setBackground(new Color(220, 230, 250));
        dendaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(dendaField, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

        String[] columnNames = { "ID", "Tanggal", "Nama Pelanggan", "Model", "Merk", "Durasi", "Harga Sewa" };
        DefaultTableModel kembalikanTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable kembalikanTable = new JTable(kembalikanTableModel);
        kembalikanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        kembalikanTable.getTableHeader().setBackground(new Color(30, 90, 220));
        kembalikanTable.getTableHeader().setForeground(Color.WHITE);
        kembalikanTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        kembalikanTable.setBackground(new Color(220, 230, 250));
        kembalikanTable.setRowHeight(30);
        kembalikanTable.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(kembalikanTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Populate the table atas with transaksi data
        kembalikanTableModel.setRowCount(0); // Clear table
        for (Transaksi t : daftarTransaksi) {
            kembalikanTableModel.addRow(new Object[] {
                    t.getIdTransaksi(), t.getModelMobil(), t.getMerkMobil(),
                    t.getDurasi(), t.getDurasiSewa()
            });
        }

        // Add selection listener to the tabel
        kembalikanTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = kembalikanTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = kembalikanTableModel.getValueAt(selectedRow, 0).toString(); // ID Mobil
                    String model = kembalikanTableModel.getValueAt(selectedRow, 1).toString(); // Model
                    String merk = kembalikanTableModel.getValueAt(selectedRow, 2).toString(); // Merk
                    String durasi = kembalikanTableModel.getValueAt(selectedRow, 3).toString(); // durasi
                    String hargasewa = kembalikanTableModel.getValueAt(selectedRow, 3).toString(); // Harga Sewa

                    idtransaksiField.setText(id); // Set ID Mobil to the field
                    modelField.setText(model); // Set Model to the field
                    merkField.setText(merk); // Set Merk to the field
                }
            }
        });

        // Buttons
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);

        JButton tambahButton = new JButton("Return");
        tambahButton.setPreferredSize(new Dimension(100, 35));
        tambahButton.setBackground(new Color(255, 102, 0)); // oren for "return"
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setBorderPainted(false);
        tambahButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(tambahButton);
        formPanel.add(buttonPanel, gbc);

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
                DefaultTableModel pelangganTableModel = (DefaultTableModel) pelangganTable.getModel();
                loadPelangganData(pelangganTableModel); // Pastikan data dimuat
                String nextIdPelanggan = generateNextIdPelanggan(pelangganTableModel);
                idPelangganField.setText(nextIdPelanggan);
            }
        }
    }

    private String generateNextIdPelanggan(DefaultTableModel tableModel) {
        int maxId = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String id = tableModel.getValueAt(i, 0).toString();
            if (id.startsWith("P")) {
                try {
                    // Ambil bagian angka setelah prefix "P"
                    int numericPart = Integer.parseInt(id.substring(1));
                    maxId = Math.max(maxId, numericPart);
                } catch (NumberFormatException ignored) {
                    // Abaikan jika ID tidak valid
                }
            }
        }
        // Pastikan ID baru memiliki format "P" diikuti angka dengan padding 3 digit
        return String.format("P%03d", maxId + 1);
    }
}
