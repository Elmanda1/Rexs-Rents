import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PegawaiUtility {
    public static String generateNextIdPelanggan() {
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

    public static void refreshTambahTransaksiPanel(JPanel contentPanel) {
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
        List<Mobil> daftarMobil = Mobil.getMobilTersedia(); // Fetch available mobil from the database
        for (Mobil mobil : daftarMobil) {
            tableModel.addRow(new Object[] {
                    mobil.getIdMobil(), mobil.getModel(), mobil.getMerk(), formatRupiah.format(mobil.getHargaSewa())
            });
        }
    }

    public static void refreshKembalikanMobilPanel(JPanel contentPanel) {
        // Locate the table model for the kembalikanMobil panel
        JPanel kembalikanMobilPanel = (JPanel) contentPanel.getComponent(2); // Assuming it's the third panel
        JScrollPane scrollPane = (JScrollPane) ((JPanel) kembalikanMobilPanel.getComponent(1)).getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        // Clear the table
        tableModel.setRowCount(0);

        // Populate the table with unavailable mobil
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        List<Mobil> daftarMobil = Mobil.getMobilTidakTersedia(); // Fetch unavailable mobil from the database
        for (Mobil mobil : daftarMobil) {
            tableModel.addRow(new Object[] {
                    mobil.getIdMobil(), mobil.getModel(), mobil.getMerk(), formatRupiah.format(mobil.getHargaSewa())
            });
        }
    }

    public static void refreshPelangganTable(DefaultTableModel pelangganTableModel) {
        pelangganTableModel.setRowCount(0);
        for (Pelanggan p : Pelanggan.getAllPelanggan()) {
            pelangganTableModel.addRow(new Object[] {
                    p.getIdPelanggan(), p.getNama(), p.getNoHp(),
                    p.getNoKtp(), p.getAlamat(), p.getGender()
            });
        }
    }

    public static void validasiDataPelanggan(String nama, String noHP, String noKTP, String alamat) {
        if (nama.trim().isEmpty() || noHP.trim().isEmpty() || noKTP.trim().isEmpty() || alamat.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nama.matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(null, "Nama hanya boleh mengandung huruf.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!noHP.matches("^\\d{10,13}$")) {
            JOptionPane.showMessageDialog(null, "No HP harus berupa angka dengan panjang 10-13 digit.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!noKTP.matches("^\\d{16}$")) {
            JOptionPane.showMessageDialog(null, "No KTP harus berupa angka dengan panjang 16 digit.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
