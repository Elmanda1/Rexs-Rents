import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Transaksi {
    private String idTransaksi;
    private String tanggal;
    private Pelanggan pelanggan;
    private Mobil mobil;
    private int durasiSewa;
    private double totalHarga;
    private double denda;
    private static NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public Transaksi(String idTransaksi, String tanggal, Pelanggan pelanggan, Mobil mobil, int durasiSewa,
            double denda) {
        this.idTransaksi = idTransaksi;
        this.tanggal = tanggal;
        this.pelanggan = pelanggan;
        this.mobil = mobil;
        this.durasiSewa = durasiSewa;
        this.denda = denda;
        this.totalHarga = mobil.getHargaSewa() * durasiSewa + denda;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Mobil getMobil() {
        return mobil;
    }

    public int getDurasiSewa() {
        return durasiSewa;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
        this.totalHarga = mobil.getHargaSewa() * durasiSewa + denda;
    }

    // Add a new Transaksi record to the database
    public static String addToDatabase(Transaksi transaksi) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "INSERT INTO tb_transaksi (id_transaksi, tanggal, id_pelanggan, id_mobil, durasi, denda, total_harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, transaksi.getIdTransaksi());
            ps.setString(2, transaksi.getTanggal());
            ps.setString(3, transaksi.getPelanggan().getIdPelanggan());
            ps.setString(4, transaksi.getMobil().getIdMobil());
            ps.setInt(5, transaksi.getDurasiSewa());
            ps.setDouble(6, transaksi.getDenda());
            ps.setDouble(7, transaksi.getTotalHarga());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Transaksi berhasil disimpan.";
            } else {
                result = "Transaksi gagal disimpan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Fetch all Transaksi records from the database
    public static List<Transaksi> getAllTransaksi() {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT t.id_transaksi, t.tanggal, p.nama AS nama_pelanggan, m.id_mobil, m.model, m.merk, t.durasi, t.denda, t.total_harga "
                    +
                    "FROM tb_transaksi t " +
                    "JOIN tb_pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                    "JOIN tb_mobil m ON t.id_mobil = m.id_mobil " +
                    "ORDER BY t.id_transaksi ASC";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idTransaksi = rs.getString("id_transaksi").trim();
                    String tanggal = rs.getString("tanggal").trim();
                    String namaPelanggan = rs.getString("nama_pelanggan").trim();
                    String idMobil = rs.getString("id_mobil").trim();
                    String model = rs.getString("model").trim();
                    String merk = rs.getString("merk").trim();
                    int durasi = rs.getInt("durasi");
                    double denda = rs.getDouble("denda");
                    double hargaSewa = rs.getDouble("total_harga") - denda;

                    Pelanggan pelanggan = new Pelanggan(namaPelanggan, "", "", "", "");
                    Mobil mobil = new Mobil(idMobil, model, merk, hargaSewa, false, "");

                    Transaksi transaksi = new Transaksi(idTransaksi, tanggal, pelanggan, mobil, durasi, denda);
                    daftarTransaksi.add(transaksi);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTransaksi;
    }

    // Update the denda for a specific Transaksi in the database
    public static String updateDendaInDatabase(double denda) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            // Query untuk mengupdate denda pada transaksi dengan ID terbesar
            String query = "UPDATE tb_transaksi SET denda = ?, total_harga = total_harga + ? " +
                    "WHERE id_transaksi = (SELECT MAX(id_transaksi) FROM tb_transaksi)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, denda);
            ps.setDouble(2, denda);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Denda berhasil diperbarui untuk transaksi dengan ID terbesar.";
            } else {
                result = "Denda gagal diperbarui. Tidak ada transaksi yang ditemukan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan: " + e.getMessage();
        }
        return result;
    }

    // Generate the next ID for a new Transaksi
    public static String generateNextId() {
        int maxId = 0;
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT MAX(CAST(SUBSTRING(id_transaksi, 4) AS UNSIGNED)) AS max_id FROM tb_transaksi";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "TRX" + String.format("%04d", maxId + 1);
    }

    // Search Transaksi records by keyword
    public static List<Transaksi> searchByKeyword(String keyword) {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT t.id_transaksi, t.tanggal, p.nama AS nama_pelanggan, m.id_mobil, m.model, m.merk, t.durasi, t.denda, t.total_harga "
                    +
                    "FROM tb_transaksi t " +
                    "JOIN tb_pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                    "JOIN tb_mobil m ON t.id_mobil = m.id_mobil " +
                    "WHERE t.id_transaksi LIKE ? OR t.tanggal LIKE ? OR p.nama LIKE ? OR m.model LIKE ? OR m.merk LIKE ? "
                    +
                    "ORDER BY t.tanggal ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idTransaksi = rs.getString("id_transaksi").trim();
                String tanggal = rs.getString("tanggal").trim();
                String namaPelanggan = rs.getString("nama_pelanggan").trim();
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                int durasi = rs.getInt("durasi");
                double denda = rs.getDouble("denda");
                double hargaSewa = rs.getDouble("total_harga") - denda;

                Pelanggan pelanggan = new Pelanggan(namaPelanggan, "", "", "", "");
                Mobil mobil = new Mobil(idMobil, model, merk, hargaSewa, false, "");

                Transaksi transaksi = new Transaksi(idTransaksi, tanggal, pelanggan, mobil, durasi, denda);
                daftarTransaksi.add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTransaksi;
    }

    public static int countTransaksi() {
        int count = 0;
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT COUNT(*) AS total FROM tb_transaksi";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static String calculateBruto() {
        double bruto = 0;
        try (Connection con = Utility.connectDB()) {
            String harga = "SELECT SUM(total_harga) AS total FROM tb_transaksi";
            String denda = "SELECT SUM(denda) AS total FROM tb_transaksi";
            PreparedStatement ps = con.prepareStatement(harga);
            PreparedStatement ps2 = con.prepareStatement(denda);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            if (rs.next()) {
                bruto = rs.getDouble("total");
            }

            if (rs2.next()) {
                bruto += rs2.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return formatRupiah.format(bruto);
    }

    public static String calculateNetto() {
        // Remove .toDouble() and parse the formatted string values correctly
        String brutoStr = calculateBruto();
        String maintenanceStr = Mobil.getTotalMaintenance();
        double bruto = parseRupiahToDouble(brutoStr);
        double maintenance = parseRupiahToDouble(maintenanceStr);
        double netto = bruto - maintenance;
        
        return formatRupiah.format(netto);
    }

    // Helper to parse formatted rupiah string to double
    private static double parseRupiahToDouble(String rupiah) {
        try {
            // Remove all non-digit, non-comma, non-dot characters
            String cleaned = rupiah.replaceAll("[^\\d,.]", "");
            // Remove all periods (thousand separators)
            cleaned = cleaned.replaceAll("\\.", "");
            // Replace comma (decimal separator) with dot
            cleaned = cleaned.replace(",", ".");
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String calculateDenda() {
        double denda = 0;
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT SUM(denda) AS total FROM tb_transaksi";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                denda = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(denda);
    }

    public static String mobilTerlaris() {
        String mobilTerlaris = "";
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT m.model, m.merk, SUM(t.total_harga) AS total_harga_acc " +
                    "FROM tb_transaksi t " +
                    "JOIN tb_mobil m ON t.id_mobil = m.id_mobil " +
                    "GROUP BY m.model, m.merk " +
                    "ORDER BY total_harga_acc DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                mobilTerlaris = rs.getString("model") + " (" + rs.getString("merk") + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mobilTerlaris;
    }
}