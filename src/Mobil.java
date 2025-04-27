import java.io.*;
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

    public static String getHeader() {
        return String.format(
                "| %-10s | %-15s | %-12s | %-18s | %-12s |",
                "ID Mobil", "Model", "Merk", "Harga Sewa", "Status");
    }

    public String getInfo() {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaFormatted = formatRupiah.format(hargaSewa).replace("Rp", "Rp ").replace(",00", ""); // Hapus desimal

        return String.format(
                "| %-10s | %-15s | %-12s | %-18s | %-12s |",
                idMobil, model, merk, hargaFormatted, (status ? "Available" : "Unavailable"));
    }

    public String getInfoTransaksi() {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaFormatted = formatRupiah.format(hargaSewa).replace("Rp", "Rp ").replace(",00", ""); // Hapus desimal
        return String.format(
                "| %-10s | %-15s | %-12s | %-18s |",
                idMobil, model, merk, hargaFormatted);
    }

    @Override
    public String toString() {
        return idMobil + ";" + model + ";" + merk + ";" + hargaSewa + ";" + status;
    }

    public static List<Mobil> readFromCSV(String filePath) {
        List<Mobil> daftarMobil = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Lewati header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String idMobil = parts[0];
                    String model = parts[1];
                    String merk = parts[2];
                    double hargaSewa = Double.parseDouble(parts[3]);
                    boolean status = Boolean.parseBoolean(parts[4]);
                    daftarMobil.add(new Mobil(idMobil, model, merk, hargaSewa, status));
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file " + filePath + ": " + e.getMessage());
        }
        return daftarMobil;
    }

    public static void writeToCSV(ArrayList<Mobil> daftarMobil) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("daftarmobil.csv"))) { // Perbaiki path
            bw.write("ID;Model;Merk;HargaSewa;Status");
            bw.newLine();

            for (Mobil m : daftarMobil) {
                bw.write(String.join(";", m.getIdMobil(), m.getModel(), m.getMerk(),
                        String.valueOf(m.getHargaSewa()), String.valueOf(m.isTersedia())));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
