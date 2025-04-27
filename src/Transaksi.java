import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Transaksi {
    private static int jumlahTransaksi = 0;
    private String idTransaksi;
    private String tanggal;
    private Pelanggan pelanggan;
    private Mobil mobil;
    private int durasiSewa;
    private double totalHarga;
    private double keuntungan;
    private double denda; // New attribute for penalty

    public Transaksi(String tanggal, Pelanggan pelanggan, Mobil mobil, int durasiSewa) {
        // Validasi input
        if (tanggal == null || tanggal.isEmpty()) {
            throw new IllegalArgumentException("Tanggal tidak boleh kosong.");
        }
        if (pelanggan == null) {
            throw new IllegalArgumentException("Pelanggan tidak boleh null.");
        }
        if (mobil == null) {
            throw new IllegalArgumentException("Mobil tidak boleh null.");
        }
        if (durasiSewa <= 0) {
            throw new IllegalArgumentException("Durasi sewa harus lebih besar dari 0.");
        }

        // Perbarui jumlahTransaksi berdasarkan data di file CSV
        jumlahTransaksi = getJumlahTransaksiDariCSV() + 1;

        // Set atribut
        this.idTransaksi = "TRX" + String.format("%04d", jumlahTransaksi);
        this.tanggal = tanggal;
        this.pelanggan = pelanggan;
        this.mobil = mobil;
        this.durasiSewa = durasiSewa;

        // Hitung total harga
        this.totalHarga = mobil.getHargaSewa() * durasiSewa + denda; // Initialize denda to 0

        // Keuntungan awal diatur ke 0
        this.keuntungan = 0;
        this.denda = 0; // Initialize denda to 0
    }

    private static int getJumlahTransaksiDariCSV() {
        int jumlah = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("transaksi.csv"))) {
            br.readLine(); // Skip header
            while (br.readLine() != null) {
                jumlah++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jumlah;
    }

    public void tambahKeuntungan(int denda) {
        this.keuntungan += denda; // Tambahkan denda ke keuntungan
    }

    public void proses() {
        this.totalHarga = mobil.getHargaSewa() * durasiSewa + denda;
        this.keuntungan += this.totalHarga; // Tambahkan total harga sewa ke keuntungan
    }

    public double getKeuntungan() {
        return keuntungan;
    }

    public String getTotalHargaFormatted() {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(this.totalHarga);
    }

    public static void writeToCSV(ArrayList<Transaksi> daftarTransaksi) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transaksi.csv"))) {
            // Update the header to include Denda
            bw.write("IDTransaksi;Tanggal;NamaPelanggan;ModelMobil;MerkMobil;Durasi;HargaSewa;Denda");
            bw.newLine();

            for (Transaksi transaksi : daftarTransaksi) {
                String line = String.format(
                        "%s;%s;%s;%s;%s;%d;%.2f;%.2f",
                        transaksi.getIdTransaksi(),
                        transaksi.getTanggal(),
                        transaksi.getPelanggan().getNama(),
                        transaksi.getMobil().getModel(),
                        transaksi.getMobil().getMerk(),
                        transaksi.getDurasiSewa(),
                        transaksi.getTotalHarga(),
                        transaksi.getDenda()); // Include denda in the output
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaksi> readFromCSV(String filePath) {
        ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
        List<Mobil> daftarMobil = Mobil.readFromCSV("daftarmobil.csv"); // Ensure daftarMobil is populated

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length < 8) { // Update to check for 8 fields
                    System.out.println("Malformed line: " + line);
                    continue;
                }

                String idTransaksi = data[0];
                String tanggal = data[1];
                String namaPelanggan = data[2];
                String modelMobil = data[3];
                String merkMobil = data[4];
                int durasi = Integer.parseInt(data[5]);
                double hargaSewa = Double.parseDouble(data[6]);
                double denda = Double.parseDouble(data[7]); // Read denda

                // Find the corresponding Mobil object
                Mobil mobil = daftarMobil.stream()
                        .filter(m -> m.getModel().equals(modelMobil) && m.getMerk().equals(merkMobil))
                        .findFirst()
                        .orElse(null);

                if (mobil == null) {
                    System.out.println("Mobil not found: " + modelMobil + " " + merkMobil);
                    continue;
                }

                // Create a simple Pelanggan object
                Pelanggan pelanggan = new Pelanggan(namaPelanggan, "", "", "", "");

                // Create a Transaksi object
                Transaksi transaksi = new Transaksi(tanggal, pelanggan, mobil, durasi);
                transaksi.idTransaksi = idTransaksi; // Set the ID directly from the CSV
                transaksi.totalHarga = hargaSewa; // Set the total price directly
                transaksi.denda = denda; // Set the denda directly
                daftarTransaksi.add(transaksi);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return daftarTransaksi;
    }

    // Getter untuk idTransaksi
    public String getIdTransaksi() {
        return idTransaksi;
    }

    // Getter untuk durasiSewa
    public int getDurasiSewa() {
        return durasiSewa;
    }

    // Getter untuk mobil
    public Mobil getMobil() {
        return mobil;
    }

    public String getTanggal() {
        return tanggal;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public String getModelMobil() {
        return mobil.getModel();
    }

    public String getMerkMobil() {
        return mobil.getMerk();
    }

    public int getDurasi() {
        return durasiSewa;
    }

    public static String getHeader() {
        return String.format(
                "| %-10s | %-20s | %-5s | %-15s | %-10s | %-15s | %-5s | %-16s |",
                "ID Trans", "Tanggal", "ID Pel", "Pelanggan", "ID Mobil", "Mobil", "Durasi", "Total Harga");
    }

    // Add a setter for denda
    public void setDenda(double denda) {
        this.denda = denda;
    }

    // Add a getter for denda
    public double getDenda() {
        return denda;
    }
}