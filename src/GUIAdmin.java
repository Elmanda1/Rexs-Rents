import java.awt.*;
import javax.swing.*;

public class GUIAdmin extends BaseDashboardUI {
    private JPanel contentPanel;
    private AdminMenuBar menuBarPanel;

    public GUIAdmin() {
        setTitle("Rex's Rents - Dashboard Admin");
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
        JPanel historyPanel = HistoryTransaksiPanel.create();
        JPanel dataMobilPanel = DataMobilPanel.create();
        JPanel editLoginPanel = EditLoginPegawaiPanel.create();
        JPanel dataKeuanganPanel = DataKeuanganPanel.create();
        JPanel statistikPanel = StatistikPanel.create();
        contentPanel.add(historyPanel, "history");
        contentPanel.add(dataMobilPanel, "dataMobil");
        contentPanel.add(editLoginPanel, "editLogin");
        contentPanel.add(dataKeuanganPanel, "dataKeuangan");
        contentPanel.add(statistikPanel, "statistik");

        menuBarPanel = new AdminMenuBar(contentPanel);
        setupBaseUI(menuBarPanel, contentPanel, null);
    }
}