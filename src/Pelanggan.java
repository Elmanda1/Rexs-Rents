import java.io.*;
import java.util.ArrayList;

public class Pelanggan {
    private static ArrayList<Pelanggan> daftarPelanggan = new ArrayList<>();
    private String idPelanggan;
    private String nama;
    private String noHp;
    private String noKtp;
    private String alamat;
    private String gender;

    public Pelanggan(String nama, String noHp, String noKtp, String alamat, String gender) {
        this.idPelanggan = "P" + String.format("%03d", daftarPelanggan.size() + 1);
        this.nama = nama;
        this.noHp = noHp;
        this.noKtp = noKtp;
        this.alamat = alamat;
        this.gender = gender;
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
        return noHp;
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

    public static String getHeader() {
        return String.format(
                "| %-4s | %-25s | %-6s | %-13s | %-16s | %-34s |",
                "ID", "Nama", "Gender", "No HP", "No KTP", "Alamat");
    }

    public String getInfo() {
        return String.format(
                "| %-4s | %-25s | %-6s | %-13s | %-16s | %-34s |",
                idPelanggan, nama, gender, noHp, noKtp, alamat);
    }

    public String getInfoTransaksi() {
        return String.format(
                "| %-4s | %-25s | %-6s |",
                idPelanggan, nama, gender);
    }

    public static ArrayList<Pelanggan> readFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("daftarpelanggan.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length < 6)
                    continue;

                String id = data[0];
                String nama = data[1];
                String noHp = data[2];
                String noKtp = data[3];
                String alamat = data[4];
                String gender = data[5];

                Pelanggan pelanggan = new Pelanggan(nama, noHp, noKtp, alamat, gender);
                pelanggan.idPelanggan = id; // Set the ID manually
                daftarPelanggan.add(pelanggan);
            }
            System.out.println("Data pelanggan berhasil dibaca dari file.");
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }
        return daftarPelanggan;
    }

    public static void writeToCSV(ArrayList<Pelanggan> daftarPelanggan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("daftarpelanggan.csv"))) {
            // Write header
            bw.write("ID;Nama;NoHP;NoKTP;Alamat;Gender");
            bw.newLine();

            // Write each pelanggan
            for (Pelanggan pelanggan : daftarPelanggan) {
                String line = String.format(
                        "%s;%s;%s;%s;%s;%s",
                        pelanggan.getIdPelanggan(),
                        pelanggan.getNama(),
                        pelanggan.getNoHp(),
                        pelanggan.getNoKtp(),
                        pelanggan.getAlamat(),
                        pelanggan.getGender());
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Data pelanggan berhasil disimpan ke file.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan file: " + e.getMessage());
        }
    }
}