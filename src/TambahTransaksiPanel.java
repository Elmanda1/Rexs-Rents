import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TambahTransaksiPanel extends javax.swing.JPanel {
    public static JPanel create(JPanel contentPanel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 10, 6, 10);

        // Form fields
        JLabel pelangganLabel = Utility.styleLabel("Nama Pelanggan");
        JLabel idMobilLabel = Utility.styleLabel("ID Mobil");
        JLabel modelLabel = Utility.styleLabel("Model");
        JLabel merkLabel = Utility.styleLabel("Merk");
        JLabel durasiLabel = Utility.styleLabel("Durasi Sewa");
        JLabel hargaSewaLabel = Utility.styleLabel("Harga Sewa");

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
        gbc.ipadx = 246;
        gbc.ipady = 10;

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
        JComboBox<Integer> durasiComboBox = Utility.styleComboBox(durasiOptions);
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
        gbc.gridx = 1; // <-- hanya di kolom kanan, segaris dengan textfield
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(8, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;

        JPanel fotoPanel = new JPanel(new BorderLayout());
        // Atur hanya tinggi, lebar biarkan mengikuti parent
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
                                int width = 500;
                                int height = 200;
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
                PegawaiUtility.refreshTambahTransaksiPanel(contentPanel);
                PegawaiUtility.refreshKembalikanMobilPanel(contentPanel);

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

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
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

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateTable();
            }

            @Override
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
}