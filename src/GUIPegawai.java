import java.awt.*;
import javax.swing.*;

public class GUIPegawai extends BaseDashboardUI {
    private JPanel contentPanel;
    private JTextField idPelangganField;
    private PegawaiMenuBar menuBarPanel;

    public GUIPegawai() {
        setTitle("Rex's Rents - Dashboard Pegawai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            setIconImage(new ImageIcon("assets/icon.png").getImage());
        } catch (Exception e) {
            // Optionally log or ignore
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Optionally log or ignore
        }

        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        contentPanel = new JPanel(new CardLayout());
        JPanel tambahTransaksi = TambahTransaksiPanel.create(contentPanel);
        JPanel dataPelanggan = DataPelangganPanel.create(contentPanel);
        JPanel kembalikanMobil = KembalikanMobilPanel.create();
        contentPanel.add(tambahTransaksi, "NewTransaction");
        contentPanel.add(dataPelanggan, "dataPelanggan");
        contentPanel.add(kembalikanMobil, "KembalikanMobil");

        menuBarPanel = new PegawaiMenuBar(contentPanel, this::onPanelSwitched);
        setupBaseUI(menuBarPanel, contentPanel, null);
    }

    // Called by PegawaiMenuBar when a panel is switched
    public void onPanelSwitched(String panelName) {
        // Business logic for when a panel is switched (e.g., refresh data)
        if (panelName.equals("dataPelanggan")) {
            if (idPelangganField != null) {
                String nextIdPelanggan = PegawaiUtility.generateNextIdPelanggan();
                idPelangganField.setText(nextIdPelanggan);
            }
        }
    }
}