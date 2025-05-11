import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pelanggan {
    private String idPelanggan;
    private String nama;
    private String noHp;
    private String noKtp;
    private String alamat;
    private String gender;

    // Default constructor
    public Pelanggan() {
        this.idPelanggan = "";
        this.nama = "";
        this.noHp = "";
        this.noKtp = "";
        this.alamat = "";
        this.gender = "";
    }

    // Constructor with parameters
    public Pelanggan(String idPelanggan, String nama, String noHp, String noKtp, String alamat, String gender) {
        this.idPelanggan = idPelanggan != null ? idPelanggan : "";
        this.nama = nama != null ? nama : "";
        this.noHp = noHp != null ? noHp : "";
        this.noKtp = noKtp != null ? noKtp : "";
        this.alamat = alamat != null ? alamat : "";
        this.gender = gender != null ? gender : "";
    }

    // Constructor with five parameters (no ID)
    public Pelanggan(String nama, String noHp, String noKtp, String alamat, String gender) {
        this.idPelanggan = ""; // Default value for ID
        this.nama = nama != null ? nama : "";
        this.noHp = noHp != null ? noHp : "";
        this.noKtp = noKtp != null ? noKtp : "";
        this.alamat = alamat != null ? alamat : "";
        this.gender = gender != null ? gender : "";
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getGender() {
        return gender;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Fetch all Pelanggan records from the database
    public static List<Pelanggan> getAllPelanggan() {
        List<Pelanggan> daftarPelanggan = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT * FROM tb_pelanggan ORDER BY id_pelanggan ASC";
            try (PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String idPelanggan = rs.getString("id_pelanggan").trim();
                    String nama = rs.getString("nama").trim();
                    String noHp = rs.getString("noHP").trim();
                    String noKtp = rs.getString("noKTP").trim();
                    String alamat = rs.getString("alamat").trim();
                    String gender = rs.getString("gender").trim();

                    daftarPelanggan.add(new Pelanggan(idPelanggan, nama, noHp, noKtp, alamat, gender));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarPelanggan;
    }

    // Add a new Pelanggan record to the database
    public static String addToDatabase(Pelanggan pelanggan) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "INSERT INTO tb_pelanggan (id_pelanggan, nama, noHP, noKTP, alamat, gender) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                // Ensure the ID is generated if not provided
                String idPelanggan = pelanggan.getIdPelanggan().isEmpty() ? generateNextId() : pelanggan.getIdPelanggan();
                ps.setString(1, idPelanggan);
                ps.setString(2, pelanggan.getNama());
                ps.setString(3, pelanggan.getNoHp());
                ps.setString(4, pelanggan.getNoKtp());
                ps.setString(5, pelanggan.getAlamat());
                ps.setString(6, pelanggan.getGender());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    result = "Pelanggan berhasil ditambahkan.";
                } else {
                    result = "Pelanggan gagal ditambahkan.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Update an existing Pelanggan record in the database
    public static String updateInDatabase(Pelanggan pelanggan) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "UPDATE tb_pelanggan SET nama = ?, noHP = ?, noKTP = ?, alamat = ?, gender = ? WHERE id_pelanggan = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, pelanggan.getNama());
            ps.setString(2, pelanggan.getNoHp());
            ps.setString(3, pelanggan.getNoKtp());
            ps.setString(4, pelanggan.getAlamat());
            ps.setString(5, pelanggan.getGender());
            ps.setString(6, pelanggan.getIdPelanggan());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Pelanggan berhasil diupdate.";
            } else {
                result = "Pelanggan gagal diupdate.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Delete a Pelanggan record from the database
    public static String deleteFromDatabase(String idPelanggan) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "DELETE FROM tb_pelanggan WHERE id_pelanggan = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, idPelanggan);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "Pelanggan berhasil dihapus.";
            } else {
                result = "Pelanggan gagal dihapus.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Generate the next ID for a new Pelanggan
    public static String generateNextId() {
        int maxId = 0;
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT MAX(CAST(SUBSTRING(id_pelanggan, 2) AS UNSIGNED)) AS max_id FROM tb_pelanggan";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "P" + String.format("%03d", maxId + 1);
    }

    // Search Pelanggan records by name
    public static List<Pelanggan> searchByName(String nameKeyword) {
        List<Pelanggan> daftarPelanggan = new ArrayList<>();
        try (Connection con = Utility.connectDB()) {
            String query = "SELECT * FROM tb_pelanggan WHERE nama LIKE ? ORDER BY nama ASC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nameKeyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idPelanggan = rs.getString("id_pelanggan").trim();
                String nama = rs.getString("nama").trim();
                String noHp = rs.getString("noHP").trim();
                String noKtp = rs.getString("noKTP").trim();
                String alamat = rs.getString("alamat").trim();
                String gender = rs.getString("gender").trim();

                daftarPelanggan.add(new Pelanggan(idPelanggan, nama, noHp, noKtp, alamat, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarPelanggan;
    }
}