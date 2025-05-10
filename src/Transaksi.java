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

    public Transaksi(String idTransaksi, String tanggal, Pelanggan pelanggan, Mobil mobil, int durasiSewa, double denda) {
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
            String query = "SELECT t.id_transaksi, t.tanggal, p.nama AS nama_pelanggan, m.id_mobil, m.model, m.merk, t.durasi, t.denda, t.total_harga " +
                           "FROM tb_transaksi t " +
                           "JOIN tb_pelanggan p ON t.id_pelanggan = p.id_pelanggan " +
                           "JOIN tb_mobil m ON t.id_mobil = m.id_mobil " +
                           "ORDER BY t.id_transaksi ASC";
            PreparedStatement ps = con.prepareStatement(query);
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
                Mobil mobil = new Mobil(idMobil, model, merk, hargaSewa, false);

                Transaksi transaksi = new Transaksi(idTransaksi, tanggal, pelanggan, mobil, durasi, denda);
                daftarTransaksi.add(transaksi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarTransaksi;
    }

    // Update the denda for a specific Transaksi in the database
    public static String updateDendaInDatabase(String idTransaksi, double denda) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "UPDATE tb_transaksi SET denda = ?, total_harga = total_harga + ? WHERE id_transaksi = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, denda);
            ps.setDouble(2, denda);
            ps.setString(3, idTransaksi);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Denda berhasil diperbarui.";
            } else {
                result = "Denda gagal diperbarui.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}