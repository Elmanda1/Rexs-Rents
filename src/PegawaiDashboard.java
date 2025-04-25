import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PegawaiDashboard extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JPanel tambahTransaksiPanel;
    private JPanel editPelangganPanel;
    private JPanel kembalikanMobilPanel;
    private JButton tambahTransaksiButton;
    private JButton editDataPelangganButton;
    private JButton kembalikanMobilButton;
    private JTable mobilTable;
    private DefaultTableModel tableModel;
    private JLabel userLabel;
    private JButton signOutButton;
    
    private JTextField pelangganField;
    private JTextField idMobilField;
    private JTextField modelMobilField;
    private JTextField durasiSewaField;
    private JTextField totalHargaField;
    private JButton confirmButton;
    
    private ArrayList<Mobil> mobilList;
    
    public PegawaiDashboard() {
        setTitle("Pegawai Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        tambahTransaksi();
        loadCarData();
        
        setVisible(true);
    }
    
    private JPanel createEditPelangganPanel() {
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
        
        JTextField idPelangganField = new JTextField(10);
        idPelangganField.setText("Pilih pada tabel");
        idPelangganField.setEditable(false);
        idPelangganField.setBackground(new Color(220, 230, 250));
        idPelangganField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
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
        JTextField alamatField = new JTextField(30);
        alamatField.setBackground(new Color(220, 230, 250));
        alamatField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(alamatField, gbc);
        
        gbc.gridy++;
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"L", "P"});
        genderComboBox.setBackground(new Color(220, 230, 250));
        genderComboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        formPanel.add(genderComboBox, gbc);
        
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);
        JButton simpanButton = new JButton("Simpan");
        simpanButton.setPreferredSize(new Dimension(100, 35));
        simpanButton.setBackground(new Color(231, 76, 60));
        simpanButton.setForeground(Color.WHITE);
        simpanButton.setBorderPainted(false);
        simpanButton.setFocusPainted(false);
        formPanel.add(simpanButton, gbc);
        
        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        
        String[] columnNames = {"ID", "Nama", "No HP", "No KTP", "Alamat", "Gender"};
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
        
        // Load customer data from CSV
        loadPelangganData(pelangganTableModel);
        
        // Add selection listener to the table
        pelangganTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = pelangganTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = pelangganTableModel.getValueAt(selectedRow, 0).toString();
                    String nama = pelangganTableModel.getValueAt(selectedRow, 1).toString();
                    String noHP = pelangganTableModel.getValueAt(selectedRow, 2).toString();
                    String noKTP = pelangganTableModel.getValueAt(selectedRow, 3).toString();
                    String alamat = pelangganTableModel.getValueAt(selectedRow, 4).toString();
                    String gender = pelangganTableModel.getValueAt(selectedRow, 5).toString();
                    
                    idPelangganField.setText(id);
                    namaField.setText(nama);
                    noHPField.setText(noHP);
                    noKTPField.setText(noKTP);
                    alamatField.setText(alamat);
                    genderComboBox.setSelectedItem(gender);
                }
            }
        });
        
        // Add action listener to the simpan button
        simpanButton.addActionListener(e -> {
            int selectedRow = pelangganTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Pilih pelanggan dari tabel terlebih dahulu", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String id = idPelangganField.getText();
            String nama = namaField.getText();
            String noHP = noHPField.getText();
            String noKTP = noKTPField.getText();
            String alamat = alamatField.getText();
            String gender = genderComboBox.getSelectedItem().toString();
            
            // Validation
            if (nama.trim().isEmpty() || noHP.trim().isEmpty() || noKTP.trim().isEmpty() || alamat.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update table model
            pelangganTableModel.setValueAt(nama, selectedRow, 1);
            pelangganTableModel.setValueAt(noHP, selectedRow, 2);
            pelangganTableModel.setValueAt(noKTP, selectedRow, 3);
            pelangganTableModel.setValueAt(alamat, selectedRow, 4);
            pelangganTableModel.setValueAt(gender, selectedRow, 5);
            
            // Save changes to CSV file
            savePelangganData(pelangganTableModel);
            
            JOptionPane.showMessageDialog(null, "Data pelanggan berhasil diupdate", 
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
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
            java.util.List<String> lines = new java.util.ArrayList<>();
            
            // Add header
            lines.add("ID;Nama;NoHP;NoKTP;Alamat;Gender");
            
            // Add data rows
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

    private void tambahTransaksi() {
        mainPanel = new JPanel(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Pegawai Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 83, 215));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        
        userLabel = new JLabel("Duwii");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        signOutButton = new JButton("Logout");
        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(false);
        signOutButton.setBorderPainted(false);
        
        userPanel.add(userLabel);
        userPanel.add(signOutButton);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userPanel, BorderLayout.EAST);
        
        // Menu Panel
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        tambahTransaksiButton = new JButton("Tambah Transaksi");
        tambahTransaksiButton.setPreferredSize(new Dimension(150, 40));
        tambahTransaksiButton.setMargin(new Insets(8, 15, 8, 15));
        tambahTransaksiButton.setFocusPainted(false);
        
        editDataPelangganButton = new JButton("Edit Data Pelanggan");
        editDataPelangganButton.setPreferredSize(new Dimension(180, 40));
        editDataPelangganButton.setMargin(new Insets(8, 15, 8, 15));
        editDataPelangganButton.setFocusPainted(false);
        
        kembalikanMobilButton = new JButton("Kembalikan Mobil");
        kembalikanMobilButton.setPreferredSize(new Dimension(150, 40));
        kembalikanMobilButton.setMargin(new Insets(8, 15, 8, 15));
        kembalikanMobilButton.setFocusPainted(false);
        
        // Set default selected button
        tambahTransaksiButton.setBackground(new Color(25, 83, 215));
        tambahTransaksiButton.setForeground(Color.WHITE);
        tambahTransaksiButton.setBorderPainted(false);
        
        editDataPelangganButton.setBackground(Color.WHITE);
        editDataPelangganButton.setBorderPainted(false);
        
        kembalikanMobilButton.setBackground(Color.WHITE);
        kembalikanMobilButton.setBorderPainted(false);
        
        menuPanel.add(tambahTransaksiButton);
        menuPanel.add(editDataPelangganButton);
        menuPanel.add(kembalikanMobilButton);
        
        // Content Panel
        contentPanel = new JPanel(new CardLayout());
        
        // Tambah Transaksi Panel
        tambahTransaksiPanel = new JPanel(new BorderLayout());
        
        // Left form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 15);
        
        JLabel pelangganLabel = new JLabel("Pelanggan");
        formPanel.add(pelangganLabel, gbc);
        
        gbc.gridy++;
        JLabel idMobilLabel = new JLabel("ID Mobil");
        formPanel.add(idMobilLabel, gbc);
        
        gbc.gridy++;
        JLabel modelMobilLabel = new JLabel("Model Mobil");
        formPanel.add(modelMobilLabel, gbc);
        
        gbc.gridy++;
        JLabel durasiSewaLabel = new JLabel("Durasi Sewa (Hari)");
        formPanel.add(durasiSewaLabel, gbc);
        
        gbc.gridy++;
        JLabel totalHargaLabel = new JLabel("Total Harga");
        formPanel.add(totalHargaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.ipadx = 50;
        gbc.ipady = 5;
        
        // Using JComboBox for pelanggan field to match the dropdown in the image
        pelangganField = new JTextField(20);
        pelangganField.setBackground(new Color(220, 230, 250));
        pelangganField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(pelangganField, gbc);
        
        gbc.gridy++;
        idMobilField = new JTextField(10);
        idMobilField.setEditable(false);
        idMobilField.setText("Pilih pada tabel");
        idMobilField.setBackground(new Color(180, 180, 180));
        idMobilField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(idMobilField, gbc);
        
        gbc.gridy++;
        modelMobilField = new JTextField(15);
        modelMobilField.setEditable(false);
        modelMobilField.setText("Pilih pada tabel");
        modelMobilField.setBackground(new Color(180, 180, 180));
        modelMobilField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(modelMobilField, gbc);
        
        gbc.gridy++;
        durasiSewaField = new JTextField(5);
        durasiSewaField.setBackground(new Color(220, 230, 250));
        durasiSewaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(durasiSewaField, gbc);
        
        gbc.gridy++;
        totalHargaField = new JTextField(15);
        totalHargaField.setEditable(false);
        totalHargaField.setText("Masukkan durasi sewa");
        totalHargaField.setBackground(new Color(180, 180, 180));
        totalHargaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(totalHargaField, gbc);
        
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 10, 15);
        confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(100, 35));
        confirmButton.setBackground(new Color(231, 76, 60));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBorderPainted(false);
        confirmButton.setFocusPainted(false);
        formPanel.add(confirmButton, gbc);
        
        // Right table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        
        String[] columnNames = {"No", "ID", "Model", "Merk", "Harga", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        mobilTable = new JTable(tableModel);
        mobilTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mobilTable.getTableHeader().setBackground(new Color(30, 90, 220));
        mobilTable.getTableHeader().setForeground(Color.WHITE);
        mobilTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        mobilTable.setBackground(new Color(220, 230, 250));
        mobilTable.setRowHeight(30);
        mobilTable.setGridColor(new Color(200, 200, 200));
        
        JScrollPane scrollPane = new JScrollPane(mobilTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        tambahTransaksiPanel.add(formPanel, BorderLayout.WEST);
        tambahTransaksiPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Edit Pelanggan Panel (placeholder)
        editPelangganPanel = createEditPelangganPanel();
        
        // Kembalikan Mobil Panel (placeholder)
        kembalikanMobilPanel = new JPanel(new BorderLayout());
        kembalikanMobilPanel.add(new JLabel("Kembalikan Mobil Panel", SwingConstants.CENTER), BorderLayout.CENTER);
        
        contentPanel.add(tambahTransaksiPanel, "tambahTransaksi");
        contentPanel.add(editPelangganPanel, "editPelanggan");
        contentPanel.add(kembalikanMobilPanel, "kembalikanMobil");
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(menuPanel, BorderLayout.PAGE_START);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add hover effects to menu buttons
        addButtonHoverEffect(tambahTransaksiButton);
        addButtonHoverEffect(editDataPelangganButton);
        addButtonHoverEffect(kembalikanMobilButton);
        
        // Set action listeners
        tambahTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "tambahTransaksi");
                
                tambahTransaksiButton.setBackground(new Color(25, 83, 215));
                tambahTransaksiButton.setForeground(Color.WHITE);
                editDataPelangganButton.setBackground(Color.WHITE);
                editDataPelangganButton.setForeground(Color.BLACK);
                kembalikanMobilButton.setBackground(Color.WHITE);
                kembalikanMobilButton.setForeground(Color.BLACK);
            }
        });
        
        editDataPelangganButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "editPelanggan");
                
                tambahTransaksiButton.setBackground(Color.WHITE);
                tambahTransaksiButton.setForeground(Color.BLACK);
                editDataPelangganButton.setBackground(new Color(25, 83, 215));
                editDataPelangganButton.setForeground(Color.WHITE);
                kembalikanMobilButton.setBackground(Color.WHITE);
                kembalikanMobilButton.setForeground(Color.BLACK);
            }
        });
        
        kembalikanMobilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) contentPanel.getLayout();
                cl.show(contentPanel, "kembalikanMobil");
                
                tambahTransaksiButton.setBackground(Color.WHITE);
                tambahTransaksiButton.setForeground(Color.BLACK);
                editDataPelangganButton.setBackground(Color.WHITE);
                editDataPelangganButton.setForeground(Color.BLACK);
                kembalikanMobilButton.setBackground(new Color(25, 83, 215));
                kembalikanMobilButton.setForeground(Color.WHITE);
            }
        });
        
        // Table selection listener - populate ID and Model fields
        mobilTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mobilTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = tableModel.getValueAt(selectedRow, 1).toString();
                    String model = tableModel.getValueAt(selectedRow, 2).toString();
                    
                    idMobilField.setText(id);
                    modelMobilField.setText(model);
                    updateTotalHarga();
                }
            }
        });
        
        // Duration field listener - recalculate total price
        durasiSewaField.addActionListener(e -> updateTotalHarga());
        
        // Confirm button action
        confirmButton.addActionListener(e -> {
            // Validation
            if (pelangganField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama pelanggan tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (idMobilField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Mobil tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (durasiSewaField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Durasi sewa tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                int durasi = Integer.parseInt(durasiSewaField.getText().trim());
                if (durasi <= 0) {
                    JOptionPane.showMessageDialog(this, "Durasi sewa harus lebih dari 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Durasi sewa harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form fields after submission
            pelangganField.setText("");
            idMobilField.setText("");
            modelMobilField.setText("");
            durasiSewaField.setText("");
            totalHargaField.setText("");
        });
        
        getContentPane().add(mainPanel);
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
    
    private void updateTotalHarga() {
        try {
            if (!idMobilField.getText().trim().isEmpty() && !durasiSewaField.getText().trim().isEmpty()) {
                String id = idMobilField.getText().trim();
                
                // Find the selected mobil from the list
                Mobil selectedMobil = null;
                for (Mobil mobil : mobilList) {
                    if (mobil.getIdMobil().equals(id)) {
                        selectedMobil = mobil;
                        break;
                    }
                }
                
                if (selectedMobil != null) {
                    int durasi = Integer.parseInt(durasiSewaField.getText().trim());
                    if (durasi > 0) {
                        double hargaPerHari = selectedMobil.getHargaSewa();
                        double totalHarga = hargaPerHari * durasi;
                        
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                        String formattedHarga = currencyFormat.format(totalHarga).replace("Rp", "Rp ");
                        
                        totalHargaField.setText(formattedHarga);
                    } else {
                        totalHargaField.setText("");
                    }
                } else {
                    totalHargaField.setText("");
                }
            } else {
                totalHargaField.setText("");
            }
        } catch (NumberFormatException ex) {
            totalHargaField.setText("");
        }
    }
    
    private void loadCarData() {
        mobilList = Mobil.readFromCSV();
        int rowNum = 1;
        
        for (Mobil mobil : mobilList) {
            if (mobil.isTersedia()) {
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                String hargaFormatted = formatRupiah.format(mobil.getHargaSewa()).replace("Rp", "Rp ").replace(",00", "");
                
                Object[] rowData = {
                    rowNum++, 
                    mobil.getIdMobil(), 
                    mobil.getModel(), 
                    mobil.getMerk(), 
                    hargaFormatted, 
                    "Tersedia"
                };
                
                tableModel.addRow(rowData);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new PegawaiDashboard();
        });
    }
}