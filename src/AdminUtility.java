import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;

public class AdminUtility {
    public static void refreshMobilTable(DefaultTableModel mobilTableModel) {
        mobilTableModel.setRowCount(0); // Clear the table model
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Fetch the latest data from the database
        List<Mobil> daftarMobil = Mobil.getAllMobil();

        for (Mobil m : daftarMobil) {
            mobilTableModel.addRow(new Object[] {
                    m.getIdMobil(), m.getModel(), m.getMerk(),
                    formatRupiah.format(m.getHargaSewa()),
                    m.isTersedia() ? "Available" : "Unavailable"
            });
        }
    }

    public static void refreshHistoryTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Clear the table model
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        // Fetch the latest data from the database
        List<Transaksi> daftarTransaksi = Transaksi.getAllTransaksi();

        for (Transaksi t : daftarTransaksi) {
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
