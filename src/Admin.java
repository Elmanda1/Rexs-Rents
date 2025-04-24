import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Akun {
    private ArrayList<Mobil> daftarMobil = Mobil.readFromCSV();
    private ArrayList<Transaksi> daftarTransaksi = Pegawai.getDaftarTransaksi();
    private Pegawai pegawai;

    public Admin(String username, String password, Pegawai pegawai) {
        super(username, password);
        this.pegawai = pegawai; // inisialisasi objek pegawai
    }

    public void tampilkanMenu() {
        Scanner obj = new Scanner(System.in);
        int pilihan = 0;
        do {
            clearScreen();
            System.out.println("====================================");
            System.out.println("||        Admin Rex's Rent        ||");
            System.out.println("====================================");
            System.out.println("|| 1. Lihat Riwayat Transaksi     ||");
            System.out.println("|| 2. Lihat Evaluasi Bisnis       ||");
            System.out.println("|| 3. Edit Data Mobil             ||");
            System.out.println("|| 4. Tambah Data Mobil           ||");
            System.out.println("|| 5. Edit Data Login Pegawai     ||");
            System.out.println("|| 6. Logout                      ||");
            System.out.println("====================================");
            System.out.print("Pilih Menu: ");

            try {
                pilihan = obj.nextInt();
                obj.nextLine(); // Bersihkan buffer setelah membaca angka
            } catch (Exception e) {
                System.out.println("Input tidak valid. Masukkan angka sesuai menu.");
                obj.nextLine(); // Bersihkan buffer jika terjadi kesalahan
                tekanEnter();
            }

            switch (pilihan) {
                case 1:
                    clearScreen();
                    lihatRiwayat();
                    tekanEnter();
                    break;
                case 2:
                    clearScreen();
                    evaluasi();
                    tekanEnter();
                    break;
                case 3:
                    clearScreen();
                    editDataMobil(obj);
                    tekanEnter();
                    break;
                case 4:
                    clearScreen();
                    tambahMobil(obj);
                    tekanEnter();
                    break;
                case 5:
                    clearScreen();
                    ubahDataLoginPegawai(obj);
                    tekanEnter();
                    break;
                case 6:
                    Mobil.writeToCSV(daftarMobil);
                    Transaksi.writeToCSV(daftarTransaksi);
                    System.out.println("Keluar dari menu admin.");
                    break;
            }
        } while (pilihan != 6);
    }

    public void lihatRiwayat() {
        if (daftarTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }

        // Panggil tampilkanRiwayat
        Transaksi.tampilkanRiwayat(daftarTransaksi);
    }

    public void evaluasi() {
        if (daftarTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi untuk dievaluasi.");
            return;
        }

        // Tampilkan header untuk histori penyewaan
        System.out.println(
                "=======================================================================================================");
        System.out.println(
                "||                                         Histori Penyewaan                                         ||");
        System.out.println(
                "=======================================================================================================");
        System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-10s | %-15s |\n",
                "ID Trans", "Tanggal", "Pelanggan", "Mobil", "Durasi", "Total Harga");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");

        double total = 0;

        // Loop untuk menampilkan setiap transaksi
        for (Transaksi transaksi : daftarTransaksi) {
            String pelanggan = transaksi.getPelanggan().getNama();
            String mobil = transaksi.getMobil().getModel();
            int durasi = transaksi.getDurasiSewa();
            double totalHarga = transaksi.getTotalHarga();

            // Tampilkan detail transaksi
            System.out.printf("| %-10s | %-20s | %-15s | %-15s | %-10d | Rp%-13.2f |\n",
                    transaksi.getIdTransaksi(), transaksi.getTanggal(), pelanggan, mobil, durasi, totalHarga);

            // Tambahkan ke total penghasilan
            total += totalHarga;
        }

        System.out.println(
                "-------------------------------------------------------------------------------------------------------");

        // Tampilkan total penghasilan
        System.out.printf("Total Penghasilan: Rp %.2f\n", total);
    }

    public void editDataMobil(Scanner obj) {
        System.out.println("===========================================");
        System.out.println("||      Edit Data Mobil Rex's Rent       ||");
        System.out.println("===========================================");
        if (daftarMobil.isEmpty()) {
            System.out.println("Tidak ada data mobil untuk diedit.");
            return;
        }
        System.out.println(Mobil.getHeader());
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i = 0; i < daftarMobil.size(); i++) {
            System.out.printf("%-2d. %s\n", (i + 1), daftarMobil.get(i).getInfo());
        }
        System.out.println("---------------------------------------------------------------------------------------");

        int index = -1;
        try {
            System.out.print("Pilih mobil: ");
            index = obj.nextInt() - 1;
            obj.nextLine(); // Membersihkan buffer

            if (index < 0 || index >= daftarMobil.size()) {
                System.out.println("Pilihan tidak valid. Pilih angka antara 1 hingga " + daftarMobil.size() + ".");
                return;
            }

            System.out.print("Model baru: ");
            String modelBaru = obj.nextLine();
            if (modelBaru.trim().isEmpty()) {
                System.out.println("Model tidak boleh kosong.");
                return;
            }
            
            daftarMobil.get(index).setModel(modelBaru);

            System.out.print("Merk baru: ");
            String merkBaru = obj.nextLine();
            if (!merkBaru.matches("[a-zA-Z ]+") || merkBaru.trim().isEmpty()) {
                System.out.println("Merk tidak valid. Hanya boleh huruf dan tidak boleh kosong.");
                return;
            }
            daftarMobil.get(index).setMerk(merkBaru);

            System.out.print("Harga baru: ");
            try {
                double hargaBaru = obj.nextDouble();
                obj.nextLine(); // Membersihkan buffer
                if (hargaBaru <= 0) {
                    System.out.println("Harga tidak valid. Harus berupa angka positif.");
                    return;
                }
                daftarMobil.get(index).setHargaSewa(hargaBaru);
            } catch (Exception e) {
                System.out.println("Input harga tidak valid. Harus berupa angka.");
                obj.nextLine(); // Membersihkan buffer
                return;
            }
            System.out.println("Data mobil berhasil diubah.");
        } catch (Exception e) {
            System.out.println("Input tidak valid. Masukkan angka yang sesuai.");
            obj.nextLine(); // Membersihkan buffer untuk menghindari error berikutnya
        }
    }

    public void tambahMobil(Scanner obj) {
    try {
        String model;
        System.out.println("===========================================");
        System.out.println("||     Tambah Data Mobil Rex's Rent      ||");
        System.out.println("===========================================");
        do {
            System.out.print("Masukkan model mobil: ");
            model = obj.nextLine();
            if (model.trim().isEmpty() || !model.matches("[a-zA-Z ]+")) {
                System.out.println("Model tidak valid. Hanya boleh huruf dan tidak boleh kosong.");
            } else if (model.length() > 50) {
                System.out.println("Model terlalu panjang. Maksimal 50 karakter.");
            }
        } while (model.trim().isEmpty() || !model.matches("[a-zA-Z ]+") || model.length() > 50);

        String merk;
        do {
            System.out.print("Masukkan merk mobil: ");
            merk = obj.nextLine();
            if (merk.trim().isEmpty() || !merk.matches("[a-zA-Z ]+")) {
                System.out.println("Merk tidak valid. Hanya boleh huruf dan tidak boleh kosong.");
            } else if (merk.length() > 50) {
                System.out.println("Merk terlalu panjang. Maksimal 50 karakter.");
            }
        } while (merk.trim().isEmpty() || !merk.matches("[a-zA-Z ]+") || merk.length() > 50);

        double hargaSewa;
        do {
            System.out.print("Masukkan harga sewa mobil: ");
            try {
                hargaSewa = obj.nextDouble();
                obj.nextLine(); // Membersihkan buffer
                if (hargaSewa <= 0) {
                    System.out.println("Harga tidak valid. Harus berupa angka positif.");
                }
            } catch (Exception e) {
                System.out.println("Input harga tidak valid. Harus berupa angka.");
                obj.nextLine(); // Membersihkan buffer
                hargaSewa = -1; // Set nilai invalid untuk mengulang
            }
        } while (hargaSewa <= 0);

        // Buat ID mobil baru secara otomatis
        String idMobil = "M" + String.format("%02d", daftarMobil.size() + 1);

        // Tetapkan nomor mobil berdasarkan ukuran daftar
        int nomorMobil = daftarMobil.size() + 1;

        // Buat objek mobil baru
        Mobil mobilBaru = new Mobil(idMobil, model, merk, hargaSewa, true);

        // Tambahkan mobil baru ke daftar mobil
        daftarMobil.add(mobilBaru);

        System.out.println("Mobil berhasil ditambahkan dengan ID: " + idMobil);
    } catch (Exception e) {
        System.out.println("Terjadi kesalahan. Pastikan semua data dimasukkan dengan benar.");
        obj.nextLine(); // Membersihkan buffer untuk menghindari error berikutnya
    }
}

    public void ubahDataLoginPegawai(Scanner obj) {
        System.out.println("===========================================");
        System.out.println("||   Ubah Data Login Pegawai Rex's Rent  ||");
        System.out.println("===========================================");
        String usernameBaru;
        do {
            System.out.print("Masukkan username baru untuk pegawai: ");
            usernameBaru = obj.nextLine();
            if (usernameBaru.trim().isEmpty()) {
                System.out.println("Username tidak valid. Username tidak boleh kosong.");
            }
        } while (usernameBaru.trim().isEmpty());

        String passwordBaru;
        do {
            System.out.print("Masukkan password baru untuk pegawai (minimal 5 karakter): ");
            passwordBaru = obj.nextLine();
            if (passwordBaru.trim().isEmpty() || passwordBaru.length() < 5) {
                System.out.println("Password tidak valid. Harus minimal 5 karakter dan tidak boleh kosong.");
            }
        } while (passwordBaru.trim().isEmpty() || passwordBaru.length() < 5);

        // Ubah data login pegawai yang ada
        pegawai.setUsername(usernameBaru);
        pegawai.setPassword(passwordBaru);

        System.out.println("Data login pegawai berhasil diubah.");
    }

    // Clear screen
    public static void clearScreen() {
    try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (IOException | InterruptedException e) {
        System.out.println("Gagal membersihkan layar: " + e.getMessage());
    }
}

    // Tekan enter untuk melanjutkan
    public static void tekanEnter() {
        System.out.print("\nTekan enter untuk melanjutkan...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menunggu input.");
        }
    }
}