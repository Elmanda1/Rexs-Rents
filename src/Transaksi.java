import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Transaksi {
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    private static ArrayList<Mobil> daftarMobil = Mobil.readFromCSV();

    private static int jumlahTransaksi = 0;
    private String idTransaksi;
    private String tanggal;
    private Pegawai pegawai;
    private Pelanggan pelanggan;
    private Mobil mobil;
    private int durasiSewa;
    private double totalHarga;

    public Transaksi(String tanggal, Pegawai pegawai, Pelanggan pelanggan, Mobil mobil,
            int durasiSewa) {
        // Menambahkan jumlahTransaksi dan membuat ID otomatis
        jumlahTransaksi++;
        this.idTransaksi = "TRX" + String.format("%04d", jumlahTransaksi);
        this.tanggal = tanggal;
        this.pegawai = pegawai;
        this.pelanggan = pelanggan;
        this.mobil = mobil;
        this.durasiSewa = durasiSewa;
    }

    public void proses() {
        this.totalHarga = mobil.getHargaSewa() * durasiSewa;
    }

    public String getRingkasan() {
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    String totalHargaFormatted = formatRupiah.format(totalHarga).replace(",00", ""); // Hapus desimal
    return idTransaksi + " | " + tanggal + " | " + pelanggan.getInfoTransaksi() + mobil.getInfoTransaksi()
            + durasiSewa + " hari | " + totalHargaFormatted;
}

    public static void writeToCSV(ArrayList<Transaksi> daftarTransaksi) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transaksi.csv"))) {
            // Write header
            bw.write("ID;Tanggal;Pegawai;Pelanggan;Mobil;Durasi;TotalHarga");
            bw.newLine();

            // Write each transaction
            for (Transaksi transaksi : daftarTransaksi) {
                String line = String.format(
                        "%s;%s;%s;%s;%s;%d;%.2f",
                        transaksi.getIdTransaksi(),
                        transaksi.getTanggal(),
                        transaksi.getPegawai() != null ? transaksi.getPegawai().getUsername() : "N/A",
                        transaksi.getPelanggan().getInfoTransaksi(),
                        transaksi.getMobil().getInfoTransaksi(),
                        transaksi.getDurasiSewa(),
                        transaksi.getTotalHarga());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Transaksi> readFromCSV() {
        ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("transaksi.csv"))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length < 7) { // Validate that the line has at least 7 fields
                    System.out.println("Malformed line: " + line);
                    continue;
                }

                try {
                    String idTransaksi = data[0];
                    String tanggal = data[1];
                    String pegawaiName = data[2]; // Pegawai name (not used here)
                    String pelangganInfo = data[3];
                    String mobilInfo = data[4];
                    int durasiSewa = Integer.parseInt(data[5]);
                    double totalHarga = Double.parseDouble(data[6]);

                    // Parse pelanggan and mobil info (adjust based on your format)
                    String[] pelangganData = pelangganInfo.split(" | ");
                    if (pelangganData.length < 2) {
                        System.out.println("Malformed pelanggan info: " + pelangganInfo);
                        continue;
                    }
                    String pelangganNama = pelangganData[1];
                    Pelanggan pelanggan = new Pelanggan(pelangganNama, "", "", "", ""); // Simplified

                    String[] mobilData = mobilInfo.split(" - ");
                    if (mobilData.length < 1) {
                        System.out.println("Malformed mobil info: " + mobilInfo);
                        continue;
                    }
                    String mobilId = mobilData[0];
                    Mobil mobil = daftarMobil.stream()
                            .filter(m -> m.getIdMobil().equals(mobilId))
                            .findFirst()
                            .orElse(null);

                    if (mobil == null) {
                        System.out.println("Mobil not found for ID: " + mobilId);
                        continue;
                    }

                    // Create a new Transaksi object
                    Transaksi transaksi = new Transaksi(tanggal, null, pelanggan, mobil, durasiSewa);
                    transaksi.proses(); // Calculate totalHarga
                    daftarTransaksi.add(transaksi);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric fields in line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
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

    public Pegawai getPegawai() {
        return pegawai;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public static String getHeader() {
        return String.format(
            "| %-10s | %-20s | %-5s | %-15s | %-10s | %-15s | %-5s | %-16s |",
            "ID Trans", "Tanggal", "ID Pel", "Pelanggan", "ID Mobil", "Mobil", "Durasi", "Total Harga");
    }

   public static void tampilkanRiwayat(ArrayList<Transaksi> daftarTransaksi) {
    if (daftarTransaksi.isEmpty()) {
        System.out.println("Tidak ada riwayat transaksi.");
        return;
    }

    // Tampilkan header tabel
    System.out.println(
        "===========================================================================================================================");
    System.out.println(
        "||                                                  Riwayat Transaksi                                                    ||");
    System.out.println(
        "===========================================================================================================================");
    System.out.println(getHeader());
    System.out.println(
        "---------------------------------------------------------------------------------------------------------------------------");

    // Tampilkan setiap transaksi
    for (Transaksi transaksi : daftarTransaksi) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String totalHargaFormatted = formatRupiah.format(transaksi.getTotalHarga()).replace(",00", "").replace("Rp","Rp "); // Hapus desimal
        System.out.printf("| %-10s | %-20s | %-6s | %-15s | %-10s | %-15s | %-6d | %-16s |\n",
            transaksi.getIdTransaksi(), transaksi.getTanggal(), transaksi.getPelanggan().getIdPelanggan(),
            transaksi.getPelanggan().getNama(), transaksi.getMobil().getIdMobil(), transaksi.getMobil().getModel(),
            transaksi.getDurasiSewa(), totalHargaFormatted);
    }

    // Tampilkan footer tabel
    System.out.println(
        "===========================================================================================================================");
}

    public static void main(String[] args) {
        ArrayList<Transaksi> daftarTransaksi = Transaksi.readFromCSV();
        Transaksi.tampilkanRiwayat(daftarTransaksi);
    }
}