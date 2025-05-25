import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class KembalikanMobilPanel extends JPanel {
    public static JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        formPanel.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 10, 6, 7);

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
        gbc.ipadx = 280; // Smaller padding
        gbc.ipady = 10;

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
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 5, 75, 15);

        ImageIcon kembalikanIcon = Utility.createUniformIcon("assets/save.png", 15, 15);

        JButton kembalikanButton = Utility.styleButton("Kembalikan", new Color(0, 153, 76)); // Green
        kembalikanButton.setIcon(kembalikanIcon);
        kembalikanButton.setIconTextGap(5); // Add some space between icon and text

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(kembalikanButton);
        buttonPanel.setBackground(Color.white);

        formPanel.add(buttonPanel, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        tablePanel.setBackground(Color.white);

        String[] columnNames = { "ID", "Model", "Merk", "Harga Sewa" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
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
                    String id = tableModel.getValueAt(selectedRow, 0).toString(); // ID Mobil
                    String model = tableModel.getValueAt(selectedRow, 1).toString(); // Model
                    String merk = tableModel.getValueAt(selectedRow, 2).toString(); // Merk
                    String hargaSewa = tableModel.getValueAt(selectedRow, 3).toString(); // Harga Sewa

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
                                int width = 550;
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
        searchPanelKembalikan.setBackground(Color.white);
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

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        wrapperPanel.setBackground(Color.white);
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }
}