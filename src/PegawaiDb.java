import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PegawaiDb extends JFrame {
    private JTextField pilihNomorField;
    private JTextField idTransaksiField;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Transaksi> transaksiList;

    public PegawaiDb() {
        setTitle("Dashboard Pegawai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        transaksiList = Transaksi.readFromCSV(); // Ganti dengan load dari file atau dummy

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1000, 50));
        topPanel.setBackground(Color.WHITE);

        JLabel dashboardLabel = new JLabel("Employee Dashboard", SwingConstants.LEFT);
        dashboardLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        dashboardLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JButton logoutButton = new JButton("Sign out");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);

        topPanel.add(dashboardLabel, BorderLayout.WEST);
        topPanel.add(logoutPanel, BorderLayout.EAST);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(1000, 40));

        JMenu tambahMenu = new JMenu("Tambah Transaksi");
        JMenu editMenu = new JMenu("Edit Data Pelanggan");
        JMenu kembaliMenu = new JMenu("Kembalikan Mobil");
        menuBar.add(tambahMenu);
        menuBar.add(editMenu);
        menuBar.add(kembaliMenu);

        JPanel kembalikanPanel = buildKembalikanMobilPanel();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(menuBar, BorderLayout.NORTH);
        contentPanel.add(kembalikanPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildKembalikanMobilPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        pilihNomorField = new JTextField();
        pilihNomorField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        idTransaksiField = new JTextField();
        idTransaksiField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton confirmButton = new JButton("Kembalikan");
        confirmButton.setPreferredSize(new Dimension(100, 30));

        leftPanel.add(new JLabel("Pilih Nomor:"));
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(pilihNomorField);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(new JLabel("ID Transaksi:"));
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(idTransaksiField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(confirmButton);

        String[] kolom = {"No", "ID Transaksi", "Pelanggan", "Model Mobil", "Durasi (Hari)"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        updateTableMobilDipinjam();

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void updateTableMobilDipinjam() {
        tableModel.setRowCount(0); // Bersihkan tabel
    
        ArrayList<Mobil> semuaMobil = Mobil.readFromCSV();
        int no = 1;
        for (Mobil mobil : semuaMobil) {
            if (!mobil.isTersedia()) { // false = sedang dipinjam
                tableModel.addRow(new Object[]{
                    no++, "-", "-", mobil.getModel(), "-"
                });
            }
        }
    }
    
    /*private void updateTable() {
        tableModel.setRowCount(0);
        int no = 1;
        for (Transaksi t : transaksiList) {
            tableModel.addRow(new Object[]{
                    no++, t.getIdTransaksi(), t.getPelanggan().getNama(),
                    t.getMobil().getModel(), t.getDurasiSewa()
            });
        }
    }*/
}
