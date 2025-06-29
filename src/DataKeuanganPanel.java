import java.awt.*;
import java.io.File;
import javax.swing.*;

public class DataKeuanganPanel {
    public static JPanel create() {
        Font poppinsFont;
        try {
            poppinsFont = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Regular.ttf")).deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppinsFont);
        } catch (Exception e) {
            poppinsFont = new Font("Arial", Font.PLAIN, 16); // Fallback font
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.WHITE);

        JLabel profitLabel = Utility.styleLabel("Pelanggan & Profit All Time");
        profitLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));

        // Create top panel (blue)
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        topPanel.setBackground(Color.white);

        // Create and configure innerTopPanel with your size
        JPanel innerTopPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        innerTopPanel.setBackground(new Color(220, 230, 250));
        innerTopPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerTopPanel.setPreferredSize(new Dimension(1300, 300)); // Your original size

        JPanel jumlahPelanggan = new JPanel(new GridBagLayout());
        jumlahPelanggan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        jumlahPelanggan.setBackground(new Color(220, 230, 250));
        JPanel totalTransaksi = new JPanel(new GridBagLayout());
        totalTransaksi.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        totalTransaksi.setBackground(new Color(220, 230, 250));
        JPanel labaKotor = new JPanel(new GridBagLayout());
        labaKotor.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.LIGHT_GRAY));
        labaKotor.setBackground(new Color(220, 230, 250));
        JPanel labaBersih = new JPanel(new GridBagLayout());
        labaBersih.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
        labaBersih.setBackground(new Color(220, 230, 250));

        GridBagConstraints gbcBanyakBanget = new GridBagConstraints();

        JLabel jumlahPelangganLabel = Utility.styleLabel("Total Pelanggan");
        jumlahPelangganLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        jumlahPelangganLabel.setForeground(new Color(35, 47, 89));
        jumlahPelangganLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridx = 0;
        gbcBanyakBanget.insets = new Insets(55, 0, 65, 70); // Add gap between panels
        jumlahPelanggan.add(jumlahPelangganLabel, gbcBanyakBanget);

        JLabel angkaPelangganLabel = Utility.styleLabel(Pelanggan.countPelanggan() + "");
        angkaPelangganLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaPelangganLabel.setForeground(new Color(51, 100, 255));
        angkaPelangganLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridx = 1;
        gbcBanyakBanget.insets = new Insets(55, 60, 65, 0); // Add gap between panels
        jumlahPelanggan.add(angkaPelangganLabel, gbcBanyakBanget);

        JLabel jumlahTransaksiLabel = Utility.styleLabel("Total Transaksi");
        jumlahTransaksiLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        jumlahTransaksiLabel.setForeground(new Color(35, 47, 89));
        jumlahTransaksiLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridx = 0;
        gbcBanyakBanget.insets = new Insets(55, 0, 65, 70); // Add gap between panels
        totalTransaksi.add(jumlahTransaksiLabel, gbcBanyakBanget);

        JLabel angkaTransaksiLabel = Utility.styleLabel(Transaksi.countTransaksi() + "");
        angkaTransaksiLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaTransaksiLabel.setForeground(new Color(51, 100, 255));
        angkaTransaksiLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridx = 1;
        gbcBanyakBanget.insets = new Insets(55, 60, 65, 0); // Add gap between panels
        totalTransaksi.add(angkaTransaksiLabel, gbcBanyakBanget);

        JLabel labaKotorLabel = Utility.styleLabel("Laba Kotor");
        labaKotorLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        labaKotorLabel.setForeground(new Color(35, 47, 89));
        labaKotorLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridy = 0;
        gbcBanyakBanget.insets = new Insets(40, 0, 0, 0); // Add gap between panels
        labaKotor.add(labaKotorLabel, gbcBanyakBanget);

        JLabel angkaKotorLabel = Utility.styleLabel(Transaksi.calculateBruto());
        angkaKotorLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaKotorLabel.setForeground(new Color(51, 100, 255));
        angkaKotorLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridy = 1;
        gbcBanyakBanget.insets = new Insets(20, 0, 40, 0); // Add gap between panels
        labaKotor.add(angkaKotorLabel, gbcBanyakBanget);

        JLabel labaBersihLabel = Utility.styleLabel("Laba Bersih");
        labaBersihLabel.setForeground(new Color(35, 47, 89));
        labaBersihLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        labaBersihLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridy = 0;
        gbcBanyakBanget.insets = new Insets(40, 0, 0, 0); // Add gap between panels
        labaBersih.add(labaBersihLabel, gbcBanyakBanget);

        JLabel angkaBersihLabel = Utility.styleLabel(Transaksi.calculateNetto());
        angkaBersihLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        angkaBersihLabel.setForeground(new Color(51, 100, 255));
        angkaBersihLabel.setBackground(Color.WHITE);
        gbcBanyakBanget.gridy = 1;
        gbcBanyakBanget.insets = new Insets(20, 0, 40, 0); // Add gap between panels
        labaBersih.add(angkaBersihLabel, gbcBanyakBanget);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        innerTopPanel.add(jumlahPelanggan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        innerTopPanel.add(labaKotor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        innerTopPanel.add(totalTransaksi, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        innerTopPanel.add(labaBersih, gbc);

        // tengahin innerTopPanel ke topPanel
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.insets = new Insets(0, 10, 20, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.WEST;
        topPanel.add(profitLabel, innerGbc);
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        innerGbc.insets = new Insets(0, 0, 0, 0); // Add gap between panels
        innerGbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(innerTopPanel, innerGbc);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 92, 30, 92));
        bottomPanel.setBackground(Color.white);

        // Create three panels
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);

        GridBagConstraints dalemgbc = new GridBagConstraints();
        dalemgbc.fill = GridBagConstraints.BOTH;
        dalemgbc.weightx = 1;

        JLabel mobilPopuler = Utility.styleLabel("Mobil Terlaris");
        mobilPopuler.setFont(new Font("poppinsFont", Font.BOLD, 24));
        mobilPopuler.setForeground(new Color(35, 47, 89));
        dalemgbc.anchor = GridBagConstraints.CENTER;

        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        leftPanel.add(mobilPopuler, dalemgbc);

        JPanel mobilPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        mobilPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        mobilPanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        dalemgbc.anchor = GridBagConstraints.CENTER;


        GridBagConstraints fillerGbc = new GridBagConstraints();
        fillerGbc.gridx = 0;
        fillerGbc.gridy = 2;
        fillerGbc.weighty = 1.0; // This row takes up extra vertical space
        fillerGbc.fill = GridBagConstraints.VERTICAL;
        leftPanel.add(Box.createVerticalGlue(), fillerGbc);
        leftPanel.add(mobilPanel, dalemgbc);

        GridBagConstraints gbclagi = new GridBagConstraints();
        gbclagi.fill = GridBagConstraints.BOTH;

        JPanel namaMobilPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        namaMobilPanel.setPreferredSize(new Dimension(160, 120)); // Your original size
        gbclagi.gridy = 0;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        mobilPanel.add(namaMobilPanel, gbclagi);

        // --- Top Car Earnings Integration ---
        java.util.List<String> topCar = Mobil.getTopMobilByEarnings();
        String merk = "-", model = "-", totalEarning = "-";
        if (!topCar.isEmpty()) {
            String[] parts = topCar.get(0).split(",", 3);
            if (parts.length == 3) {
                merk = parts[0].trim();
                model = parts[1].trim();
                totalEarning = parts[2].trim();
            }
        }

        JLabel merkMobilLabel = Utility.styleLabel(merk);
        merkMobilLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        merkMobilLabel.setForeground(new Color(51, 40, 255));
        gbclagi.gridy = 0;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.WEST;
        namaMobilPanel.add(merkMobilLabel, gbclagi);

        JLabel modelMobiLabel = Utility.styleLabel(model);
        modelMobiLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        modelMobiLabel.setForeground(new Color(51, 40, 255));
        gbclagi.gridy = 1;
        gbclagi.gridx = 0;
        gbclagi.weightx = 0;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.WEST;
        namaMobilPanel.add(modelMobiLabel, gbclagi);
        // panell ini jing
        JPanel sewPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        sewPanel.setPreferredSize(new Dimension(130, 120)); // slightly bigger width
        sewPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY));
        gbclagi.gridy = 0;
        gbclagi.gridx = 1;
        gbclagi.weightx = 1;
        gbclagi.weighty = 0;
        gbclagi.insets = new Insets(0, 0, 0, 0);
        gbclagi.anchor = GridBagConstraints.EAST;
        mobilPanel.add(sewPanel, gbclagi);

        GridBagConstraints gbctrus = new GridBagConstraints();

        JLabel penyewaanLabel = Utility.styleLabel("Penyewaan");
        penyewaanLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        penyewaanLabel.setForeground(new Color(35, 47, 89));
        gbctrus.gridy = 0;
        gbctrus.gridx = 0;
        gbctrus.weightx = 1.0;
        gbctrus.weighty = 0;
        gbctrus.insets = new Insets(10, 0, 0, 0);
        gbctrus.anchor = GridBagConstraints.CENTER;
        sewPanel.add(penyewaanLabel, gbctrus);

        JLabel totalsewaLabel = Utility.styleLabel(totalEarning);
        totalsewaLabel.setFont(new Font("poppinsFont", Font.PLAIN, 24));
        totalsewaLabel.setForeground(new Color(51, 100, 255));
        gbctrus.gridy = 1;
        gbctrus.gridx = 0;
        gbctrus.weightx = 1.0;
        gbctrus.weighty = 0.5;
        gbctrus.insets = new Insets(0, 0, 0, 0);
        gbctrus.anchor = GridBagConstraints.CENTER;
        sewPanel.add(totalsewaLabel, gbctrus);

        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBackground(Color.WHITE);

        JLabel totalDendaLabel = Utility.styleLabel("Total Denda");
        totalDendaLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        totalDendaLabel.setForeground(new Color(35, 47, 89));
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        middlePanel.add(totalDendaLabel, dalemgbc);

        JPanel dendaPanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        dendaPanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        dendaPanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        middlePanel.add(Box.createVerticalGlue(), fillerGbc);
        middlePanel.add(dendaPanel, dalemgbc);

        JLabel totalDendaAngka = Utility.styleLabel(Transaksi.calculateDenda());
        totalDendaAngka.setFont(new Font("poppinsFont", Font.BOLD, 40));
        totalDendaAngka.setForeground(new Color(51, 100, 255));
        GridBagConstraints dendaGbc = new GridBagConstraints();
        dendaGbc.gridy = 0;
        dendaGbc.gridx = 0;
        dendaGbc.weightx = 1.0;
        dendaGbc.weighty = 1.0;
        dendaGbc.anchor = GridBagConstraints.CENTER;
        dendaPanel.add(totalDendaAngka, dendaGbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel totalMaintenanceLabel = Utility.styleLabel("Total Maintenance");
        totalMaintenanceLabel.setFont(new Font("poppinsFont", Font.BOLD, 24));
        totalMaintenanceLabel.setForeground(new Color(35, 47, 89));
        dalemgbc.gridy = 0;
        dalemgbc.insets = new Insets(0, 20, 20, 0); // Add gap between panels
        rightPanel.add(totalMaintenanceLabel, dalemgbc);

        JPanel maintenancePanel = Utility.createRoundedPanel(new GridBagLayout(), new Color(220, 230, 250));
        maintenancePanel.setPreferredSize(new Dimension(120, 120)); // Your original size
        dalemgbc.gridy = 1;
        dalemgbc.weighty = 0;
        maintenancePanel.setBackground(new Color(220, 230, 250));
        dalemgbc.insets = new Insets(0, 20, 0, 20);
        rightPanel.add(Box.createVerticalGlue(), fillerGbc);
        rightPanel.add(maintenancePanel, dalemgbc);

        JLabel maintenanceangkaLabel = Utility.styleLabel(Mobil.getTotalMaintenance());
        maintenanceangkaLabel.setFont(new Font("poppinsFont", Font.BOLD, 40));
        maintenanceangkaLabel.setForeground(new Color(51, 100, 255));
        dendaGbc.gridy = 0;
        dendaGbc.gridx = 0;
        dendaGbc.weightx = 1.0;
        dendaGbc.weighty = 1.0;
        dendaGbc.anchor = GridBagConstraints.CENTER;
        maintenancePanel.add(maintenanceangkaLabel, dendaGbc);

        // Create constraints for the three panels
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.fill = GridBagConstraints.BOTH;
        panelGbc.weighty = 1.0;
        panelGbc.insets = new Insets(0, 0, 0, 20); // Add gap between panels

        // Add left panel
        panelGbc.gridx = 0;
        panelGbc.weightx = 1.0;
        bottomPanel.add(leftPanel, panelGbc);

        // Add middle panel
        panelGbc.gridx = 1;
        panelGbc.weightx = 1.0;
        bottomPanel.add(middlePanel, panelGbc);

        // Add right panel
        panelGbc.gridx = 2;
        panelGbc.weightx = 1.0;
        panelGbc.insets = new Insets(0, 0, 0, 0); // Remove right gap for last panel
        bottomPanel.add(rightPanel, panelGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(topPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.5;
        panel.add(bottomPanel, gbc);

        return panel;
    }

}
