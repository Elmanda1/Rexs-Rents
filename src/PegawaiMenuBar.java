import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.*;

public class PegawaiMenuBar extends JPanel {
    private final Map<String, JButton> buttonMap = new HashMap<>();
    private final JPanel contentPanel;
    private final Consumer<String> onPanelSwitched;

    public PegawaiMenuBar(JPanel contentPanel, Consumer<String> onPanelSwitched) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.contentPanel = contentPanel;
        this.onPanelSwitched = onPanelSwitched;
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JButton tambahTransaksiButton = new JButton("Tambah Transaksi");
        JButton dataPelangganButton = new JButton("Data Pelanggan");
        JButton kembalikanMobilButton = new JButton("Kembalikan Mobil");

        buttonMap.put("NewTransaction", tambahTransaksiButton);
        buttonMap.put("dataPelanggan", dataPelangganButton);
        buttonMap.put("KembalikanMobil", kembalikanMobilButton);

        setupButton(tambahTransaksiButton, "assets/tambahtransaksiw.png", 200);
        setupButton(dataPelangganButton, "assets/customer.png", 180);
        setupButton(kembalikanMobilButton, "assets/return.png", 180);

        // Set default selected button
        setSelectedButton("NewTransaction");

        add(tambahTransaksiButton);
        add(dataPelangganButton);
        add(kembalikanMobilButton);

        Utility.addButtonHoverEffect(tambahTransaksiButton);
        Utility.addButtonHoverEffect(dataPelangganButton);
        Utility.addButtonHoverEffect(kembalikanMobilButton);

        tambahTransaksiButton.addActionListener(e -> switchPanel("NewTransaction"));
        dataPelangganButton.addActionListener(e -> switchPanel("dataPelanggan"));
        kembalikanMobilButton.addActionListener(e -> switchPanel("KembalikanMobil"));
    }

    private void setupButton(JButton button, String iconPath, int width) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setIcon(Utility.createUniformIcon(iconPath, 20, 20));
        button.setIconTextGap(8);
        button.setPreferredSize(new Dimension(width, 40));
        button.setMargin(new Insets(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
    }

    private void setSelectedButton(String panelName) {
        for (Map.Entry<String, JButton> entry : buttonMap.entrySet()) {
            JButton btn = entry.getValue();
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            // Set default icon
            if (entry.getKey().equals("NewTransaction")) {
                btn.setIcon(Utility.createUniformIcon("assets/tambahtransaksi.png", 20, 20));
            } else if (entry.getKey().equals("dataPelanggan")) {
                btn.setIcon(Utility.createUniformIcon("assets/customer.png", 20, 20));
            } else if (entry.getKey().equals("KembalikanMobil")) {
                btn.setIcon(Utility.createUniformIcon("assets/return.png", 20, 20));
            }
        }
        // Set selected button style and icon
        JButton selected = buttonMap.get(panelName);
        if (selected != null) {
            selected.setBackground(new Color(25, 83, 215));
            selected.setForeground(Color.WHITE);
            if (panelName.equals("NewTransaction")) {
                selected.setIcon(Utility.createUniformIcon("assets/tambahtransaksiw.png", 20, 20));
            } else if (panelName.equals("dataPelanggan")) {
                selected.setIcon(Utility.createUniformIcon("assets/customerw.png", 20, 20));
            } else if (panelName.equals("KembalikanMobil")) {
                selected.setIcon(Utility.createUniformIcon("assets/returnw.png", 20, 20));
            }
        }
    }

    public void switchPanel(String panelName) {
        setSelectedButton(panelName);
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
        if (onPanelSwitched != null) onPanelSwitched.accept(panelName);
    }
}
