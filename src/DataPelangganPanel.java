import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataPelangganPanel extends JPanel {
    public static JPanel create(JPanel contentPanel) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create form panel (left side)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 10));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 10, 6, 10);

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
        gbc.ipadx = 0;
        gbc.ipady = 10;

        JTextField idPelangganField = Utility.styleTextField(false);
        formPanel.add(idPelangganField, gbc);

        gbc.gridy++;
        JTextField namaField = Utility.styleTextField(true);
        formPanel.add(namaField, gbc);

        gbc.gridy++;
        JTextField noHPField = Utility.styleTextField(true);
        formPanel.add(noHPField, gbc);

        gbc.gridy++;
        JTextField noKTPField = Utility.styleTextField(true);
        formPanel.add(noKTPField, gbc);

        gbc.gridy++;
        JTextField alamatField = Utility.styleTextField(true);
        formPanel.add(alamatField, gbc);

        gbc.gridy++;
        JComboBox<String> genderComboBox = Utility.styleComboBox(new String[] { "L", "P" });
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
        buttonPanel.setBackground(Color.WHITE);

        formPanel.add(buttonPanel, gbc);

        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        tablePanel.setBackground(Color.WHITE);

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
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new Utility.PlaceholderTextField("Search Pelanggan...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchField.getText().trim().toLowerCase();
                pelangganTableModel.setRowCount(0);
                for (Pelanggan p : Pelanggan.getAllPelanggan()) {
                    String all = (p.getIdPelanggan() + " " +
                            p.getNama() + " " +
                            p.getNoHp() + " " +
                            p.getNoKtp() + " " +
                            p.getAlamat() + " " +
                            p.getGender()).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search pelanggan...") || all.contains(keyword)) {
                        pelangganTableModel.addRow(new Object[] {
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

        // Add action listener to the tambah button
        tambahButton.addActionListener(event -> {
            String id = idPelangganField.getText();
            String nama = namaField.getText();
            String noHP = noHPField.getText();
            String noKTP = noKTPField.getText();
            String alamat = alamatField.getText();
            String gender = genderComboBox.getSelectedItem().toString();

            PegawaiUtility.validasiDataPelanggan(nama, noHP, noKTP, alamat);            

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

            PegawaiUtility.refreshTambahTransaksiPanel(contentPanel);

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

            PegawaiUtility.validasiDataPelanggan(nama, noHP, noKTP, alamat);         

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

            PegawaiUtility.refreshTambahTransaksiPanel(contentPanel);

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

            PegawaiUtility.refreshTambahTransaksiPanel(contentPanel);

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
        wrapperPanel.setBackground(Color.white);
        wrapperPanel.add(formPanel);
        panel.add(wrapperPanel, BorderLayout.WEST);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }
}