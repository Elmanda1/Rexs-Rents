import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws IOException, InterruptedException {
        Scanner obj = new Scanner(System.in);

        // Buat objek Pegawai dan Admin
        Pegawai pegawai = new Pegawai("pegawai", "12345");
        Admin admin = new Admin("admin", "admin123", pegawai);

        while (true) {
            try {
                clearScreen();
            } catch (IOException | InterruptedException e) {
                System.out.println("Gagal membersihkan layar: " + e.getMessage());
            }

            System.out.println("====================================");
            System.out.println("||    Landing Page Rex's Rent     ||");
            System.out.println("====================================");
            System.out.println("|| 1. Login sebagai Admin         ||");
            System.out.println("|| 2. Login sebagai Pegawai       ||");
            System.out.println("|| 3. Logout                      ||");
            System.out.println("====================================");
            System.out.print("Pilih Menu: ");

            try {
                int pilihan = obj.nextInt();
                obj.nextLine(); // Membersihkan buffer

                switch (pilihan) {
                    case 1: // Login sebagai Admin
                        clearScreen();
                        System.out.println("====================================");
                        System.out.println("||     Login Admin Rex's Rent     ||");
                        System.out.println("====================================");
                        System.out.print("Username: ");
                        String adminUname = obj.nextLine();
                        System.out.print("Password: ");
                        String adminPass = obj.nextLine();

                        if (admin.login(adminUname, adminPass)) {
                            tekanEnter();
                            clearScreen();
                            admin.tampilkanMenu();
                        } else {
                            System.out.println("Login Admin gagal. Username atau password salah.");
                        }
                        tekanEnter();
                        break;

                    case 2: // Login sebagai Pegawai
                        clearScreen();
                        System.out.println("====================================");
                        System.out.println("||    Login Pegawai Rex's Rent    ||");
                        System.out.println("====================================");
                        System.out.print("Username: ");
                        String pegawaiUname = obj.nextLine();
                        System.out.print("Password: ");
                        String pegawaiPass = obj.nextLine();

                        if (pegawai.login(pegawaiUname, pegawaiPass)) {
                            tekanEnter();
                            clearScreen();
                            pegawai.tampilkanMenu();
                        } else {
                            System.out.println("Login Pegawai gagal. Username atau password salah.");
                        }
                        tekanEnter();
                        break;

                    case 3: // Keluar
                        System.out.println("Keluar dari aplikasi.");
                        obj.close();
                        return;

                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        tekanEnter();
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid. Masukkan angka sesuai menu.");
                obj.nextLine(); // Membersihkan buffer
                tekanEnter();
            }
        }
    }

    // Clear screen
    public static void clearScreen() throws IOException, InterruptedException {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            throw e; // Lempar kembali exception agar bisa ditangani di main
        }
    }

    // Tekan enter untuk melanjutkan
    public static void tekanEnter() {
        System.out.print("\nTekan enter untuk melanjutkan...");
        try {
            System.in.read(); // Menunggu input dari pengguna
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menunggu input: " + e.getMessage());
        }
    }
}