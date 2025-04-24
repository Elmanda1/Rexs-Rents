import java.io.IOException; // Tambahkan import untuk LocalDate
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Pegawai extends Akun {
    private static final Scanner obj = new Scanner(System.in);
    private static ArrayList<Pelanggan> daftarPelanggan = Pelanggan.readFromCSV();

    private ArrayList<Mobil> daftarMobil = Mobil.readFromCSV(); // Akan diatur dari Admin/Main

    private static ArrayList<Transaksi> daftarTransaksi = Transaksi.readFromCSV();

    public Pegawai(String username, String password) {
        super(username, password);
    }

    // Hapus deklarasi ulang Scanner di tampilkanMenu()
    public void tampilkanMenu() {
        int pilihan = 0; // Inisialisasi dengan nilai default
        do {
            clearScreen();
            System.out.println("====================================");
            System.out.println("||       Pegawai Rex's Rent       ||");
            System.out.println("====================================");
            System.out.println("|| 1. Tambah Data Pelanggan       ||");
            System.out.println("|| 2. Edit Data Pelanggan         ||");
            System.out.println("|| 3. Lihat Data Pelanggan        ||");
            System.out.println("|| 4. Tambah Transaksi Pelanggan  ||");
            System.out.println("|| 5. Lihat List Mobil Tersedia   ||");
            System.out.println("|| 6. Kembalikan Mobil            ||");
            System.out.println("|| 7. Logout                      ||");
            System.out.println("====================================");
            System.out.print("Pilih Menu: ");
            try {
                pilihan = obj.nextInt();
                obj.nextLine(); // Membersihkan buffer

                switch (pilihan) {
                    case 1:
                        clearScreen();
                        tambahDataPelanggan();
                        tekanEnter();
                        break;
                    case 2:
                        clearScreen();
                        editDataPelanggan();
                        tekanEnter();
                        break;
                    case 3:
                        clearScreen();
                        liatDataPelanggan();
                        tekanEnter();
                        break;
                    case 4:
                        clearScreen();
                        tambahTransaksi();
                        tekanEnter();
                        break;
                    case 5:
                        clearScreen();
                        lihatListMobil();
                        tekanEnter();
                        break;
                    case 6:
                        clearScreen();
                        kembalikanMobil(obj);
                        tekanEnter();
                        break;
                    case 7:
                        Pelanggan.writeToCSV(daftarPelanggan);
                        Mobil.writeToCSV(daftarMobil);
                        Transaksi.writeToCSV(daftarTransaksi);
                        System.out.println("Keluar dari menu pegawai.\n");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        tekanEnter();
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid. Masukkan angka sesuai menu.");
                obj.nextLine(); // Membersihkan buffer
                tekanEnter();
            }
        } while (pilihan != 7);
    }

    public void tambahDataPelanggan() {
        System.out.println("===========================================");
        System.out.println("||   Tambah Data Pelanggan Rex's Rent    ||");
        System.out.println("===========================================");

        String nama;
        do {
            System.out.print("Nama Pelanggan: ");
            nama = obj.nextLine();
            if (!nama.matches("[a-zA-Z\\s]+")) { // Hanya huruf dan spasi
                System.out.println("Nama hanya boleh mengandung huruf dan spasi. Silakan coba lagi.");
            }
            else if (nama.trim().isEmpty()) { // Cek apakah nama kosong
                System.out.println("Nama tidak boleh kosong. Silakan masukkan nama yang valid.");
            }
        } while (!nama.matches("[a-zA-Z\\s]+") || nama.trim().isEmpty());
       

        String noHp;
        do {
            System.out.print("No HP: ");
            noHp = obj.nextLine();
            if (!noHp.matches("\\d{10,13}")) { // Hanya angka, panjang 10-13 digit
                System.out.println("Nomor HP harus berupa angka dengan panjang 10-13 digit. Silakan coba lagi.");
            }
        } while (!noHp.matches("\\d{10,13}"));

        String noKtp;
        do {
            System.out.print("NIK: ");
            noKtp = obj.nextLine();
            if (!noKtp.matches("\\d{16}")) { // Hanya angka, panjang 16 digit
                System.out.println("NIK harus berupa angka dengan panjang 16 digit. Silakan coba lagi.");
            }
        } while (!noKtp.matches("\\d{16}"));

        String alamat;
        do {
            System.out.print("Alamat: ");
            alamat = obj.nextLine();
            if (alamat.trim().isEmpty()) {
                System.out.println("Alamat tidak boleh kosong. Silakan masukkan alamat yang valid.");
            }
        } while (alamat.trim().isEmpty());

        String gender;
        do {
            System.out.print("Gender (L/P): ");
            gender = obj.nextLine().toUpperCase(); // Ubah input menjadi huruf besar
            if (!gender.equals("L") && !gender.equals("P")) {
                System.out.println("Gender harus berupa 'L' (Laki-laki) atau 'P' (Perempuan). Silakan coba lagi.");
            }
        } while (!gender.equals("L") && !gender.equals("P"));

        Pelanggan pelanggan = new Pelanggan(nama, noHp, noKtp, alamat, gender);
        daftarPelanggan.add(pelanggan);
        System.out.println("Data pelanggan berhasil ditambahkan dengan ID: " + pelanggan.getIdPelanggan());
    }

    public void liatDataPelanggan() {
        System.out.println("===========================================");
        System.out.println("||     List Data Pelanggan Rex's Rent    ||");
        System.out.println("===========================================");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        System.out.println(Pelanggan.getHeader());
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        for (Pelanggan pelanggan : daftarPelanggan) {
            System.out.println(pelanggan.getInfo());
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
    }

    public void editDataPelanggan() {
        System.out.println("===========================================");
        System.out.println("||     Edit Data Pelanggan Rex's Rent    ||");
        System.out.println("===========================================");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        System.out.println(Pelanggan.getHeader());
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        for (Pelanggan pelanggan : daftarPelanggan) {
            System.out.println(pelanggan.getInfo());
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        String id;   
        do{
            System.out.print("Masukkan ID Pelanggan yang ingin diubah: ");
            id = obj.nextLine();
            if (id.trim().isEmpty()) { // Cek apakah ID kosong
                System.out.println("ID tidak boleh kosong. Silakan masukkan ID yang valid.");
            }
        }while(id.trim().isEmpty());

        for (Pelanggan pelanggan : daftarPelanggan) {
            if (pelanggan.getIdPelanggan().equals(id)) {
                String nama;
                do {
                    System.out.print("Nama: ");
                    nama = obj.nextLine();
                    if (nama.trim().isEmpty()) { // Cek apakah nama kosong
                        System.out.println("Nama tidak boleh kosong. Silakan masukkan nama yang valid.");
                    } else if (!nama.matches("[a-zA-Z\\s]+")) { // Cek apakah nama hanya mengandung huruf dan spasi
                        System.out.println("Nama hanya boleh mengandung huruf dan spasi. Silakan coba lagi.");
                    }
                } while (nama.trim().isEmpty() || !nama.matches("[a-zA-Z\\s]+"));
                pelanggan.setNama(nama);

                String noHp;
                do {
                    System.out.print("No HP: ");
                    noHp = obj.nextLine();
                    if (!noHp.matches("\\d{10,13}")) { // Hanya angka, panjang 10-13 digit
                        System.out.println("Nomor HP harus berupa angka dengan panjang 10-13 digit. Silakan coba lagi.");
                    }
                } while (!noHp.matches("\\d{10,13}"));
                pelanggan.setNoHp(noHp);

                String noKtp;
                do {
                    System.out.print("NIK: ");
                    noKtp = obj.nextLine();
                    if (!noKtp.matches("\\d{16}")) { // Hanya angka, panjang 16 digit
                        System.out.println("NIK harus berupa angka dengan panjang 16 digit. Silakan coba lagi.");
                    }
                } while (!noKtp.matches("\\d{16}"));
                pelanggan.setNoKtp(noKtp);

                String alamat;
                do {
                    System.out.print("Alamat: ");
                    alamat = obj.nextLine();
                    if (alamat.trim().isEmpty()) {
                        System.out.println("Alamat tidak boleh kosong. Silakan masukkan alamat yang valid.");
                    }
                } while (alamat.trim().isEmpty());
                pelanggan.setAlamat(alamat);

                String gender;
                do {
                    System.out.print("Gender (L/P): ");
                    gender = obj.nextLine().toUpperCase(); // Ubah input menjadi huruf besar
                    if (!gender.equals("L") && !gender.equals("P")) {
                        System.out.println(
                                "Gender harus berupa 'L' (Laki-laki) atau 'P' (Perempuan). Silakan coba lagi.");
                    }
                } while (!gender.equals("L") && !gender.equals("P"));
                pelanggan.setGender(gender);

                System.out.println("Data pelanggan berhasil diubah!");
                return;
            }
        }
        System.out.println("Pelanggan dengan ID tersebut tidak ditemukan.");
    }

    public void tambahTransaksi() {
    // Ambil tanggal dan waktu dari sistem
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String tanggalWaktu = now.format(formatter);

    System.out.println("===========================================");
    System.out.println("||      Tambah Transaksi Rex's Rent      ||");
    System.out.println("===========================================");

    // Tampilkan daftar pelanggan
    System.out.println(
            "---------------------------------------------------------------------------------------------------------------------");
    System.out.println(Pelanggan.getHeader());
    System.out.println(
            "---------------------------------------------------------------------------------------------------------------------");
    for (Pelanggan pelanggan : daftarPelanggan) {
        System.out.println(pelanggan.getInfo());
    }
    System.out.println(
            "---------------------------------------------------------------------------------------------------------------------");

    // Pilih pelanggan berdasarkan ID
    System.out.print("Masukkan ID pelanggan: ");
    String pelangganId = obj.nextLine();
    Pelanggan pelanggan = null;

    // Cari pelanggan berdasarkan ID
    for (Pelanggan p : daftarPelanggan) {
        if (p.getIdPelanggan().equalsIgnoreCase(pelangganId)) {
            pelanggan = p;
            break;
        }
    }

    if (pelanggan == null) {
        System.out.println("Pelanggan dengan ID tersebut tidak ditemukan.");
        return;
    }

    // Pilih mobil berdasarkan ID
    lihatListMobil();
    System.out.print("Masukkan ID mobil: ");
    String mobilId = obj.nextLine();
    Mobil mobil = null;

    // Cari mobil berdasarkan ID
    for (Mobil m : daftarMobil) {
        if (m.getIdMobil().equalsIgnoreCase(mobilId) && m.isTersedia()) {
            mobil = m;
            break;
        }
    }

    if (mobil == null) {
        System.out.println("Mobil dengan ID tersebut tidak ditemukan atau sedang disewa.");
        return;
    }

    // Input durasi sewa
    System.out.print("Durasi Sewa (hari): ");
    int durasi = -1;
    do {
        try {
            durasi = obj.nextInt();
            if (durasi <= 0) {
                System.out.println("Durasi sewa harus berupa angka positif. Silakan coba lagi.");
            }
        } catch (Exception e) {
            System.out.println("Input tidak valid. Masukkan angka positif.");
            obj.nextLine(); // Membersihkan buffer
            durasi = -1; // Set nilai invalid untuk mengulang
        }
    } while (durasi <= 0);

    // Panggil konstruktor transaksi baru
    Transaksi transaksi = new Transaksi(tanggalWaktu, this, pelanggan, mobil, durasi);
    transaksi.proses();

    // Tandai mobil sebagai disewa
    mobil.setStatus(false);

    // Setelah transaksi dibuat, tampilkan ID transaksi
    System.out.println("Transaksi berhasil dibuat dengan ID: " + transaksi.getIdTransaksi());

    daftarTransaksi.add(transaksi);
    System.out.println("Transaksi berhasil diproses.");
}
    public void kembalikanMobil(Scanner obj) {
    System.out.println("===========================================");
    System.out.println("||      Kembalikan Mobil Rex's Rent      ||");
    System.out.println("===========================================");
    if (daftarMobil.isEmpty()) {
        System.out.println("Tidak ada mobil yang tersedia untuk dikembalikan.");
        return;
    }

    System.out.println("Daftar Mobil yang Sedang Disewa:");
    System.out.println("-------------------------------------------------------------------------------------");
    for (Mobil mobil : daftarMobil) {
        if (!mobil.isTersedia()) { // Hanya tampilkan mobil yang sedang disewa
            System.out.println(mobil.getInfo());
        }
    }
    System.out.println("-------------------------------------------------------------------------------------");

    System.out.print("Masukkan ID mobil yang akan dikembalikan (0 untuk kembali ke menu): ");
    String mobilId = obj.nextLine();

    // Opsi membatalkan
    if (mobilId.equals("0")) {
        System.out.println("Proses pengembalian mobil dibatalkan.");
        return;
    }

    Mobil mobil = null;

    // Cari mobil berdasarkan ID
    for (Mobil m : daftarMobil) {
        if (m.getIdMobil().equalsIgnoreCase(mobilId) && !m.isTersedia()) {
            mobil = m;
            break;
        }
    }

    if (mobil == null) {
        System.out.println("Mobil dengan ID tersebut tidak ditemukan atau sudah tersedia.");
        return;
    }

    // Ubah status mobil menjadi tersedia
    mobil.setStatus(true);

    // Hapus transaksi terkait
    Transaksi transaksiTerkait = null;
    for (Transaksi transaksi : daftarTransaksi) {
        if (transaksi.getMobil().getIdMobil().equalsIgnoreCase(mobilId)) {
            transaksiTerkait = transaksi;
            break;
        }
    }

    if (transaksiTerkait != null) {
        daftarTransaksi.remove(transaksiTerkait);
        System.out.println("Transaksi terkait telah dihapus.");
    }

    System.out.println("Mobil berhasil dikembalikan: " + mobil.getInfo());
}

   public void lihatListMobil() {
    System.out.println("===========================================");
    System.out.println("||         List Mobil Rex's Rent         ||");
    System.out.println("===========================================");
    System.out.println("-----------------------------------------------------------------------------------");
    System.out.println(Mobil.getHeader());
    System.out.println("-----------------------------------------------------------------------------------");
    for (Mobil mobil : daftarMobil) {
        if (mobil.isTersedia()) { // Hanya tampilkan mobil yang tersedia
            System.out.println(mobil.getInfo());
        }
    }
    System.out.println("-----------------------------------------------------------------------------------");
}

    public static ArrayList<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
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
            System.in.read(); // Menunggu input dari pengguna
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menunggu input.");
        }
    }
}
