import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class DataMobilPanel extends JPanel {
    public static JPanel create() {
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

        String[] columnNames = { "ID", "Model", "Merk", "Harga Sewa", "Status"};
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

            String result = Mobil.addToDatabase(id, model, merk, harga, isAvailable, "");
            System.out.println(result);

            AdminUtility.refreshMobilTable(mobilTableModel);

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

            AdminUtility.refreshMobilTable(mobilTableModel);

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

            AdminUtility.refreshMobilTable(mobilTableModel);

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
                        });
                    }
                } else {
                    for (Mobil m : Mobil.search(keyword)) {
                        mobilTableModel.addRow(new Object[] {
                                m.getIdMobil(), m.getModel(), m.getMerk(),
                                formatRupiah.format(m.getHargaSewa()),
                                m.isTersedia() ? "Available" : "Unavailable",
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

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 7));
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

}
