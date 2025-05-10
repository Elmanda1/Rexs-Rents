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

    Locale indo = new Locale("id", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(indo);

    public Mobil(String idMobil, String model, String merk, double hargaSewa, boolean status) {
        this.idMobil = idMobil;
        this.model = model;
        this.merk = merk;
        this.hargaSewa = hargaSewa;
        this.status = status;
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
            String query = "SELECT * FROM tb_mobil ORDER BY id_mobil ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                double hargaSewa = rs.getDouble("hargasewa");
                boolean status = rs.getBoolean("status");

                daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarMobil;
    }

    // Fetch all Mobil records from the database
    public static List<Mobil> getMobilTersedia() {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status FROM tb_mobil WHERE status = TRUE ORDER BY id_mobil ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                double hargaSewa = rs.getDouble("hargasewa");
                boolean status = rs.getBoolean("status");

                daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarMobil;
    }

    public static List<Mobil> getMobilTidakTersedia() {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT id_mobil, model, merk, hargasewa, status FROM tb_mobil WHERE status = false ORDER BY id_mobil ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                double hargaSewa = rs.getDouble("hargasewa");
                boolean status = rs.getBoolean("status");

                daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarMobil;
    }

    // Add a new Mobil record to the database
    public static String addToDatabase(String idMobil, String model, String merk, double hargaSewa, boolean status) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "INSERT INTO tb_mobil (id_mobil, model, merk, harga_sewa, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, idMobil);
            ps.setString(2, model);
            ps.setString(3, merk);
            ps.setDouble(4, hargaSewa);
            ps.setBoolean(5, status);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Data Mobil berhasil disimpan.";
            } else {
                result = "Data Mobil gagal disimpan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Update an existing Mobil record in the database
    public static String updateInDatabase(Mobil mobil) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "UPDATE tb_mobil SET model = ?, merk = ?, harga_sewa = ?, status = ? WHERE id_mobil = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, mobil.getModel());
            ps.setString(2, mobil.getMerk());
            ps.setDouble(3, mobil.getHargaSewa());
            ps.setBoolean(4, mobil.isTersedia());
            ps.setString(5, mobil.getIdMobil());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Data Mobil berhasil diubah.";
            } else {
                result = "Data Mobil gagal diubah.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Delete a Mobil record from the database
    public static String deleteFromDatabase(String idMobil) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "DELETE FROM tb_mobil WHERE id_mobil = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, idMobil);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Data Mobil berhasil dihapus.";
            } else {
                result = "Data Mobil gagal dihapus.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Search Mobil records by model
    public static List<Mobil> searchByModel(String modelKeyword) {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT * FROM tb_mobil WHERE model LIKE ? ORDER BY model ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, modelKeyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idMobil = rs.getString("id_mobil").trim();
                String model = rs.getString("model").trim();
                String merk = rs.getString("merk").trim();
                double hargaSewa = rs.getDouble("harga_sewa");
                boolean status = rs.getBoolean("status");

                daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return "M" + String.format("%03d", maxId + 1);
    }
}