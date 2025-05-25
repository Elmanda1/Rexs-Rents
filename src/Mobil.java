import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Mobil {
    private String idMobil;
    private String model;
    private String merk;
    private double hargaSewa;
    private boolean status;
    private String foto;
    private int jumlahHariPeminjaman;

    private static final Locale indo = new Locale("id", "ID");
    private static final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(indo);

    // Constructor untuk data dari database (7 parameter)
    public Mobil(String idMobil, String model, String merk, double hargaSewa, boolean status, String foto,
            int jumlahHariPeminjaman) {
        this.idMobil = idMobil;
        this.model = model;
        this.merk = merk;
        this.hargaSewa = hargaSewa;
        this.status = status;
        this.foto = foto;
        this.jumlahHariPeminjaman = jumlahHariPeminjaman;
    }

    // Constructor untuk tambah/update mobil baru (6 parameter)
    public Mobil(String idMobil, String model, String merk, double hargaSewa, boolean status, String foto) {
        this(idMobil, model, merk, hargaSewa, status, foto, 0); // default jumlahHariPeminjaman = 0
    }

    public String getIdMobil() {
        return idMobil;
    }

    public String getModel() {
        return model;
    }

    public String getMerk() {
        return merk;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public boolean isTersedia() {
        return status;
    }

    public String getFoto() {
        return foto;
    }

    public int getJumlahHariPeminjaman() {
        return jumlahHariPeminjaman;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return idMobil + ";" + model + ";" + merk + ";" + hargaSewa + ";" + status;
    }

    // Fetch all Mobil records from the database
    public static List<Mobil> getAllMobil() {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status, foto, jumlah_hari_peminjaman FROM tb_mobil ORDER BY id_mobil ASC";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idMobil = rs.getString("id_mobil").trim();
                    String model = rs.getString("model").trim();
                    String merk = rs.getString("merk").trim();
                    double hargaSewa = rs.getDouble("hargasewa");
                    boolean status = rs.getBoolean("status");
                    String foto = rs.getString("foto") != null ? rs.getString("foto").trim() : "";
                    int jumlahHariPeminjaman = rs.getInt("jumlah_hari_peminjaman");

                    daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status, foto, jumlahHariPeminjaman));
                }
            }
        } catch (SQLException e) {
            // ignored
        }
        return daftarMobil;
    }

    // Fetch all Mobil records from the database
    public static List<Mobil> getMobilTersedia() {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status, foto FROM tb_mobil WHERE status = TRUE ORDER BY id_mobil ASC";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idMobil = rs.getString("id_mobil").trim();
                    String model = rs.getString("model").trim();
                    String merk = rs.getString("merk").trim();
                    double hargaSewa = rs.getDouble("hargasewa");
                    boolean status = rs.getBoolean("status");
                    String foto = rs.getString("foto") != null ? rs.getString("foto").trim() : "";

                    daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status, foto, 0));
                }
            }
        } catch (SQLException e) {
            // ignored
        }
        return daftarMobil;
    }

    public static List<Mobil> getMobilTidakTersedia() {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status, foto FROM tb_mobil WHERE status = false ORDER BY id_mobil ASC";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idMobil = rs.getString("id_mobil").trim();
                    String model = rs.getString("model").trim();
                    String merk = rs.getString("merk").trim();
                    double hargaSewa = rs.getDouble("hargasewa");
                    boolean status = rs.getBoolean("status");
                    String foto = rs.getString("foto") != null ? rs.getString("foto").trim() : "";

                    daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status, foto, 0));
                }
            }
        } catch (SQLException e) {
            // ignored
        }
        return daftarMobil;
    }

    // Add a new Mobil record to the database
    public static String addToDatabase(String id, String model, String merk, double hargaSewa, boolean isAvailable,
            String foto) {
        double biayaMaintenance = hargaSewa * 0.10; // 10% dari harga sewa
        try (Connection con = Utility.connectDB()) {
            String query = "INSERT INTO tb_mobil (id_mobil, model, merk, hargasewa, status, foto, jumlah_hari_peminjaman, total_biaya_maintenance, biaya_maintenance) VALUES (?, ?, ?, ?, ?, ?, 0, 0, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, model);
            ps.setString(3, merk);
            ps.setDouble(4, hargaSewa);
            ps.setBoolean(5, isAvailable);
            ps.setString(6, foto);
            ps.setDouble(7, biayaMaintenance);
            int rows = ps.executeUpdate();
            return (rows > 0) ? "success" : "failed";
        } catch (SQLException e) {
            // ignored
        }
        return "error";
    }

    // Update an existing Mobil record in the database
    public static String updateInDatabase(Mobil mobil) {
        try (Connection con = Utility.connectDB()) {
            // Hitung ulang biaya maintenance jika harga sewa berubah
            double biayaMaintenanceBaru = mobil.getHargaSewa() * 0.10;

            String query = "UPDATE tb_mobil SET model = ?, merk = ?, hargasewa = ?, status = ?, biaya_maintenance = ? WHERE id_mobil = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, mobil.getModel());
            ps.setString(2, mobil.getMerk());
            ps.setDouble(3, mobil.getHargaSewa());
            ps.setBoolean(4, mobil.isTersedia());
            ps.setDouble(5, biayaMaintenanceBaru);
            ps.setString(6, mobil.getIdMobil());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update berhasil untuk mobil ID: " + mobil.getIdMobil());
            } else {
                System.out.println("Update gagal untuk mobil ID: " + mobil.getIdMobil());
            }
        } catch (SQLException e) {
            // ignored
        }
        return "";
    }

    // Delete a Mobil record from the database
    public static String deleteFromDatabase(String idMobil) {
        try (Connection con = Utility.connectDB()) {
            // First, delete dependent records in tb_transaksi
            String deleteTransaksiQuery = "DELETE FROM tb_transaksi WHERE id_mobil = ?";
            try (PreparedStatement psTransaksi = con.prepareStatement(deleteTransaksiQuery)) {
                psTransaksi.setString(1, idMobil);
                psTransaksi.executeUpdate();
            }

            // Then, delete the Mobil record
            String deleteMobilQuery = "DELETE FROM tb_mobil WHERE id_mobil = ?";
            try (PreparedStatement psMobil = con.prepareStatement(deleteMobilQuery)) {
                psMobil.setString(1, idMobil);
                int rowsAffected = psMobil.executeUpdate();
                if (rowsAffected > 0) {
                    return "Data Mobil berhasil dihapus.";
                } else {
                    return "Data Mobil gagal dihapus.";
                }
            }
        } catch (SQLException e) {
            // ignored
        }
        return "";
    }

    // Search Mobil records by model
    public static List<Mobil> search(String keyword) {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status FROM tb_mobil " +
                    "WHERE id_mobil LIKE ? OR model LIKE ? OR merk LIKE ? " +
                    "ORDER BY model ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                double hargaSewa = rs.getDouble("hargasewa");
                boolean status = rs.getBoolean("status");
                String foto = rs.getString("foto") != null ? rs.getString("foto").trim() : "";

                daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status, foto, 0));
            }
        } catch (SQLException e) {
            // ignored
        }
        return daftarMobil;
    }

    // Generate the next ID for a new Mobil
    public static String generateNextId() {
        int maxId = 0;
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT MAX(CAST(SUBSTRING(id_mobil, 2) AS UNSIGNED)) AS max_id FROM tb_mobil";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            // ignored
        }
        return "M" + String.format("%02d", maxId + 1);
    }

    // public static double getTotalMaintenance() {
    //     double total = 0;
    //     try (Connection con = Utility.connectDB();
    //             PreparedStatement ps = con.prepareStatement("SELECT SUM(total_biaya_maintenance) FROM tb_mobil");
    //             ResultSet rs = ps.executeQuery()) {
    //         if (rs.next()) {
    //             total = rs.getDouble(1);
    //         }
    //     } catch (SQLException e) {
    //         // ignored
    //     }
    //     return total;
    // }

    // public static void updateMaintenanceIfNeeded(String idMobil, int hariSewa) {
    //     try (Connection con = Utility.connectDB()) {
    //         // 1. Tambah jumlah hari peminjaman
    //         String updateHari = "UPDATE tb_mobil SET jumlah_hari_peminjaman = jumlah_hari_peminjaman + ? WHERE id_mobil = ?";
    //         try (PreparedStatement ps = con.prepareStatement(updateHari)) {
    //             ps.setInt(1, hariSewa);
    //             ps.setString(2, idMobil);
    //             ps.executeUpdate();
    //         }

    //         // 2. Ambil jumlah hari dan biaya maintenance per mobil SETELAH update
    //         String select = "SELECT jumlah_hari_peminjaman, biaya_maintenance FROM tb_mobil WHERE id_mobil = ?";
    //         try (PreparedStatement ps = con.prepareStatement(select)) {
    //             ps.setString(1, idMobil);
    //             ResultSet rs = ps.executeQuery();
    //             if (rs.next()) {
    //                 int jumlahHari = rs.getInt("jumlah_hari_peminjaman");
    //                 double biayaPerMaintenance = rs.getDouble("biaya_maintenance");
    //                 int kelipatan = jumlahHari / 30;
    //                 if (kelipatan > 0) {
    //                     double tambahan = kelipatan * biayaPerMaintenance;
    //                     // 3. Update total_biaya_maintenance dan kurangi jumlah_hari_peminjaman
    //                     String updateMaintenance = "UPDATE tb_mobil SET total_biaya_maintenance = total_biaya_maintenance + ?, jumlah_hari_peminjaman = jumlah_hari_peminjaman - ? WHERE id_mobil = ?";
    //                     try (PreparedStatement ps2 = con.prepareStatement(updateMaintenance)) {
    //                         ps2.setDouble(1, tambahan);
    //                         ps2.setInt(2, kelipatan * 30);
    //                         ps2.setString(3, idMobil);
    //                         ps2.executeUpdate();
    //                     }
    //                 }
    //             }
    //         }
    //     } catch (SQLException e) {
    //         // ignored
    //     }
    // }

    public static List<String> getTopMobilByPenyewaan(int jumlah) {
        List<String> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT CONCAT(m.merk, ' ', m.model) AS nama_mobil, COUNT(t.id_transaksi) AS jumlah_penyewaan " +
                           "FROM tb_mobil m " +
                           "JOIN tb_transaksi t ON m.id_mobil = t.id_mobil " +
                           "GROUP BY nama_mobil " +
                           "ORDER BY jumlah_penyewaan DESC LIMIT ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, jumlah);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String merkModel = rs.getString("nama_mobil").trim();
                        int jumlahPenyewaan = rs.getInt("jumlah_penyewaan");
                        daftarMobil.add(merkModel + "," + jumlahPenyewaan);
                    }
                }
            }
        } catch (SQLException e) {
            // ignored
        }
        return daftarMobil;
    }

    public static String getTotalMaintenance() {
        double total = 0;
        String sql = "SELECT SUM(IFNULL(t.jumlah_servis, 0) * m.biaya_maintenance) AS total_maintenance " +
                     "FROM tb_mobil m " +
                     "LEFT JOIN (" +
                     "    SELECT id_mobil, FLOOR(SUM(durasi) / 30) AS jumlah_servis " +
                     "    FROM tb_transaksi " +
                     "    GROUP BY id_mobil" +
                     ") t ON m.id_mobil = t.id_mobil";
        try (Connection con = Utility.connectDB();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("total_maintenance");
            }
        } catch (SQLException e) {
            // ignored
        }
        return formatRupiah.format(total);
    }
}