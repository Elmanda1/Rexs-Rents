import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class HistoryTransaksiPanel extends JPanel{
    public static JPanel create() {
        List<Transaksi> transaksiList = Transaksi.getAllTransaksi();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {
                "ID Transaksi", "Tanggal", "Nama Pelanggan", "Model Mobil",
                "Merk Mobil", "Durasi (Hari)", "Harga Sewa", "Denda"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = Utility.styleTable(tableModel);
        table.setRowSelectionAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Transaksi t : transaksiList) {
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

        // Add search functionality for History Transaksi (live search)
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new Utility.PlaceholderTextField("Search Transaksi...");
        searchPanel.add(searchField, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateTable() {
                String keyword = searchField.getText().trim().toLowerCase();
                tableModel.setRowCount(0);
                for (Transaksi t : Transaksi.getAllTransaksi()) {
                    // Gabungkan semua kolom jadi satu string untuk pencarian
                    String all = (t.getIdTransaksi() + " " +
                            t.getTanggal() + " " +
                            t.getPelanggan().getNama() + " " +
                            t.getMobil().getModel() + " " +
                            t.getMobil().getMerk() + " " +
                            t.getDurasiSewa() + " " +
                            t.getTotalHarga() + " " +
                            t.getDenda()).toLowerCase();
                    if (keyword.isEmpty() || keyword.equals("search transaksi...") || all.contains(keyword)) {
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

        return panel;
    }
}
