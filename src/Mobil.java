import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    String hargaFormatted = formatRupiah.format(hargaSewa).replace("Rp","Rp ").replace(",00", ""); // Hapus desimal

    return String.format(
            "| %-10s | %-15s | %-12s | %-18s | %-12s |",
            idMobil, model, merk, hargaFormatted, (status ? "Available" : "Unavailable"));
}

public String getInfoTransaksi() {
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    String hargaFormatted = formatRupiah.format(hargaSewa).replace("Rp","Rp ").replace(",00", ""); // Hapus desimal
    return String.format(
            "| %-10s | %-15s | %-12s | %-18s |",
            idMobil, model, merk, hargaFormatted);
}

    @Override
    public String toString() {
        return idMobil + ";" + model + ";" + merk + ";" + hargaSewa + ";" + status;
    }

    public static ArrayList<Mobil> readFromCSV() {
    ArrayList<Mobil> daftarMobil = new ArrayList<>();
    int nomorMobil = 1; // Inisialisasi nomor mobil

    try (BufferedReader br = new BufferedReader(new FileReader("daftarMobil.csv"))) {
        String line;
        br.readLine(); // Skip header
        while ((line = br.readLine()) != null) {
            String[] data = line.split(";");

            if (data.length < 5)
                continue;

            String id = data[0];
            String model = data[1];
            String merk = data[2];
            double hargaSewa = Double.parseDouble(data[3]);
            boolean status = Boolean.parseBoolean(data[4]);

            Mobil mobil = new Mobil(id, model, merk, hargaSewa, status); // Tetapkan nomor mobil
            daftarMobil.add(mobil);
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    return daftarMobil;
}

    public static void writeToCSV(ArrayList<Mobil> daftarMobil) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("daftarMobil.csv"))) {
            // Write header
            bw.write("ID;Model;Merk;HargaSewa;Status");
            bw.newLine();

            // Write each mobil
            for (Mobil m : daftarMobil) {
                bw.write(m.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
