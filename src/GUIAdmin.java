import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GUIAdmin extends JFrame {
    private JLabel lblAdminDashboard = new JLabel("Admin Dashboard", SwingConstants.LEFT);
    private JButton btnSignOut = new JButton("Sign out");
    private JPanel pnlSignOut = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JTable tbTransaksi;
    private DefaultTableModel tbModelTransaksi;
    private JPanel pnlContent = new JPanel(new BorderLayout());
    private JPanel pnlTop = new JPanel(new BorderLayout());
    private ArrayList<Mobil> daftarMobil = Mobil.readFromCSV();
    private ArrayList<Transaksi> daftarTransaksi = Transaksi.readFromCSV();

    public GUIAdmin() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Top Panel
        pnlTop.setPreferredSize(new Dimension(1000, 50));
        pnlTop.setBackground(Color.WHITE);

        lblAdminDashboard.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblAdminDashboard.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        btnSignOut.setPreferredSize(new Dimension(100, 30));
        btnSignOut.addActionListener(e -> System.exit(0)); // Logout action

        pnlSignOut.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlSignOut.setOpaque(false);
        pnlSignOut.add(btnSignOut);

        pnlTop.add(lblAdminDashboard, BorderLayout.WEST);
        pnlTop.add(pnlSignOut, BorderLayout.EAST);

        // Menu Bar
        JMenu menuHistory = new JMenu("Histori Transaksi");
        JMenu menuDataMobil = new JMenu("Data Mobil");
        JMenu menuEditLoginPegawai = new JMenu("Edit Login Pegawai");

        JMenuItem menuItemHistory = new JMenuItem("Histori Transaksi");
        JMenuItem menuItemDataMobil = new JMenuItem("Data Mobil");
        JMenuItem menuItemEditLoginPegawai = new JMenuItem("Edit Login Pegawai");

        menuHistory.add(menuItemHistory);
        menuDataMobil.add(menuItemDataMobil);
        menuEditLoginPegawai.add(menuItemEditLoginPegawai);

        menuBar.add(menuHistory);
        menuBar.add(menuDataMobil);
        menuBar.add(menuEditLoginPegawai);

        // Add Action Listeners for Menu Items
        menuItemHistory.addActionListener(e -> showHistory());
        menuItemDataMobil.addActionListener(e -> showDataMobil());
        menuItemEditLoginPegawai.addActionListener(e -> showEditLoginPegawai());

        // Add Panels to Frame
        pnlContent.add(menuBar, BorderLayout.NORTH);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pnlTop, BorderLayout.NORTH);
        getContentPane().add(pnlContent, BorderLayout.CENTER);
    }

    private void showHistory() {
        // Main Panel
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        // Table Panel
        JPanel pnlTableTransaksi = new JPanel(new BorderLayout());
        String[] kolom = {"No", "ID Transaksi", "Pelanggan", "Model Mobil", "Durasi (Hari)", "Total Harga"};
        tbModelTransaksi = new DefaultTableModel(kolom, 0);
        tbTransaksi = new JTable(tbModelTransaksi);
        tbTransaksi.setRowHeight(25);
        tbTransaksi.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(tbTransaksi);
        pnlTableTransaksi.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border around the table
        pnlTableTransaksi.add(scrollPane, BorderLayout.CENTER);

        // Populate the table with transaction data
        tbModelTransaksi.setRowCount(0); // Clear table
        int no = 1;
        double totalHarga = 0; // Variable to store the total harga
        for (Transaksi t : daftarTransaksi) {
            tbModelTransaksi.addRow(new Object[]{
                    no++, t.getIdTransaksi(), t.getPelanggan().getNama(),
                    t.getMobil().getModel(), t.getDurasiSewa(), t.getTotalHarga()
            });
            totalHarga += t.getTotalHarga(); // Add the totalHarga of each transaction
        }

        // Label Panel
        JPanel pnlLabels = new JPanel();
        pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS)); // Vertical alignment for labels

        JLabel lblTotalTransaksi = new JLabel("Total Transaksi: " + daftarTransaksi.size());
        JLabel lblTotalHarga = new JLabel("Total Pendapatan: Rp " + totalHarga);

        // Align labels to the right
        lblTotalTransaksi.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblTotalHarga.setAlignmentX(Component.RIGHT_ALIGNMENT);

        pnlLabels.add(lblTotalTransaksi);
        pnlLabels.add(lblTotalHarga);

        // Wrap the labels in a FlowLayout panel to push them to the right
        JPanel pnlLabelsWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlLabelsWrapper.add(pnlLabels);

        // Add Table and Labels to Main Panel
        pnlMain.add(pnlTableTransaksi, BorderLayout.CENTER);
        pnlMain.add(pnlLabelsWrapper, BorderLayout.SOUTH);

        // Update Content Panel
        pnlContent.removeAll();
        pnlContent.add(menuBar, BorderLayout.NORTH); // Re-add menuBar
        pnlContent.add(pnlMain, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    private void showDataMobil() {
        // Main Panel
        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        // Table Panel (Right Side)
        JPanel pnlTableMobil = new JPanel(new BorderLayout());
        String[] kolomMobil = {"No", "ID Mobil", "Model", "Merk", "Harga Sewa", "Status"};
        DefaultTableModel tbModelMobil = new DefaultTableModel(kolomMobil, 0);
        JTable tbMobil = new JTable(tbModelMobil);
        tbMobil.setRowHeight(25);
        tbMobil.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPaneMobil = new JScrollPane(tbMobil);
        pnlTableMobil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Add border around the table
        pnlTableMobil.add(scrollPaneMobil, BorderLayout.CENTER);

        // Populate the table with Mobil data
        tbModelMobil.setRowCount(0); // Clear table
        AtomicInteger no = new AtomicInteger(1); // Use AtomicInteger for thread-safe increment
        for (Mobil m : daftarMobil) {
            tbModelMobil.addRow(new Object[]{
                    no.getAndIncrement(), m.getIdMobil(), m.getModel(), m.getMerk(),
                    m.getHargaSewa(), m.isTersedia() ? "Available" : "Unavailable"
            });
        }

        // Label and Input Panel (Left Side)
        JPanel pnlLabels = new JPanel(new GridBagLayout());
        pnlLabels.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; // Align components to the top

        JLabel lblIDMobil = new JLabel("ID Mobil:");
        JTextField txtIDMobil = new JTextField(15);
        JLabel lblModelMobil = new JLabel("Model Mobil:");
        JTextField txtModelMobil = new JTextField(15);
        JLabel lblMerkMobil = new JLabel("Merk Mobil:");
        JTextField txtMerkMobil = new JTextField(15);
        JLabel lblHargaSewa = new JLabel("Harga Sewa:");
        JTextField txtHargaSewa = new JTextField(15);
        JLabel lblStatusMobil = new JLabel("Status Mobil:");
        JComboBox<String> statusMobil = new JComboBox<>(new String[]{"Available", "Unavailable"});
        JButton btnSimpanMobil = new JButton("Simpan");

        // Add components to the label panel
        gbc.gridx = 0; gbc.gridy = 0;
        pnlLabels.add(lblIDMobil, gbc);
        gbc.gridx = 1;
        pnlLabels.add(txtIDMobil, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlLabels.add(lblModelMobil, gbc);
        gbc.gridx = 1;
        pnlLabels.add(txtModelMobil, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlLabels.add(lblMerkMobil, gbc);
        gbc.gridx = 1;
        pnlLabels.add(txtMerkMobil, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        pnlLabels.add(lblHargaSewa, gbc);
        gbc.gridx = 1;
        pnlLabels.add(txtHargaSewa, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        pnlLabels.add(lblStatusMobil, gbc);
        gbc.gridx = 1;
        pnlLabels.add(statusMobil, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        pnlLabels.add(btnSimpanMobil, gbc);

        // Add Action Listener for Save Button
        btnSimpanMobil.addActionListener(e -> {
            String idMobil = txtIDMobil.getText();
            String modelMobil = txtModelMobil.getText();
            String merkMobil = txtMerkMobil.getText();
            String hargaSewa = txtHargaSewa.getText();
            String status = (String) statusMobil.getSelectedItem();

            // Add new Mobil to the list and save to CSV
            try {
                Mobil mobilBaru = new Mobil(idMobil, modelMobil, merkMobil, Double.parseDouble(hargaSewa), status.equals("Available"));
                daftarMobil.add(mobilBaru);
                Mobil.writeToCSV(daftarMobil); // Save updated list to CSV

                JOptionPane.showMessageDialog(GUIAdmin.this, "Data Mobil berhasil disimpan.",
                        "Simpan Data Mobil", JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table
                tbModelMobil.addRow(new Object[]{
                        no.getAndIncrement(), mobilBaru.getIdMobil(), mobilBaru.getModel(), mobilBaru.getMerk(),
                        mobilBaru.getHargaSewa(), mobilBaru.isTersedia() ? "Available" : "Unavailable"
                });
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GUIAdmin.this, "Harga Sewa harus berupa angka.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Table and Labels to Main Panel
        pnlMain.add(pnlLabels, BorderLayout.WEST); // Add labels to the left
        pnlMain.add(pnlTableMobil, BorderLayout.CENTER); // Add table to the right

        // Update Content Panel
        pnlContent.removeAll();
        pnlContent.add(menuBar, BorderLayout.NORTH); // Re-add menuBar
        pnlContent.add(pnlMain, BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    private void showEditLoginPegawai() {
        // Main Panel
        JPanel pnlMain = new JPanel(new GridBagLayout());
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblUsernamePegawai = new JLabel("Username Pegawai:");
        JTextField txtUsernamePegawai = new JTextField(15);
        JLabel lblPasswordPegawai = new JLabel("Password Pegawai:");
        JTextField txtPasswordPegawai = new JTextField(15);
        JButton btnSimpanPegawai = new JButton("Simpan");

        // Add components to the panel
        gbc.gridx = 0; gbc.gridy = 0;
        pnlMain.add(lblUsernamePegawai, gbc);
        gbc.gridx = 1;
        pnlMain.add(txtUsernamePegawai, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlMain.add(lblPasswordPegawai, gbc);
        gbc.gridx = 1;
        pnlMain.add(txtPasswordPegawai, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        pnlMain.add(btnSimpanPegawai, gbc);

        // Create an instance of Admin
        Pegawai pegawai = new Pegawai("pegawai", "password");
        Admin admin = new Admin("admin", "admin123", pegawai);

        // Add Action Listener for Save Button
        btnSimpanPegawai.addActionListener(e -> {
            String username = txtUsernamePegawai.getText();
            String password = txtPasswordPegawai.getText();

            // Validate input
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

            pegawai.setUsername(username);
            pegawai.setPassword(password);

            JOptionPane.showMessageDialog(GUIAdmin.this, "Data login pegawai berhasil diubah.",
                    "Simpan Data Pegawai", JOptionPane.INFORMATION_MESSAGE);
        });

        // Update Content Panel
        pnlContent.removeAll();
        pnlContent.add(menuBar, BorderLayout.NORTH); // Re-add menuBar
        pnlContent.add(pnlMain, BorderLayout.CENTER); // Add main panel to the center
        pnlContent.revalidate();
        pnlContent.repaint();
    }
}