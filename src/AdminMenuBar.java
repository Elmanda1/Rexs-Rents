import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AdminMenuBar extends JPanel {
    private final Map<String, JButton> buttonMap = new HashMap<>();
    private final JPanel contentPanel;

    public AdminMenuBar(JPanel contentPanel) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.contentPanel = contentPanel;
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

        JButton historyButton = new JButton("Histori Transaksi");
        JButton dataMobilButton = new JButton("Data Mobil");
        JButton editLoginButton = new JButton("Edit Informasi Login Pegawai");
        JButton dataKeuanganButton = new JButton("Data Keuangan");
        JButton statistikButton = new JButton("Statistik");

        buttonMap.put("history", historyButton);
        buttonMap.put("dataMobil", dataMobilButton);
        buttonMap.put("editLogin", editLoginButton);
        buttonMap.put("dataKeuangan", dataKeuanganButton);
        buttonMap.put("statistik", statistikButton);

        setupButton(historyButton, "assets/historiw.png", 200);
        setupButton(dataMobilButton, "assets/datamobil.png", 150);
        setupButton(editLoginButton, "assets/edit.png", 250);
        setupButton(dataKeuanganButton, "assets/keuangan.png", 180);
        setupButton(statistikButton, "assets/datamobil.png", 150);

        setSelectedButton("history");

        add(historyButton);
        add(dataMobilButton);
        add(editLoginButton);
        add(dataKeuanganButton);
        add(statistikButton);

        Utility.addButtonHoverEffect(historyButton);
        Utility.addButtonHoverEffect(dataMobilButton);
        Utility.addButtonHoverEffect(editLoginButton);
        Utility.addButtonHoverEffect(dataKeuanganButton);
        Utility.addButtonHoverEffect(statistikButton);

        historyButton.addActionListener(e -> switchPanel("history"));
        dataMobilButton.addActionListener(e -> switchPanel("dataMobil"));
        editLoginButton.addActionListener(e -> switchPanel("editLogin"));
        dataKeuanganButton.addActionListener(e -> switchPanel("dataKeuangan"));
        statistikButton.addActionListener(e -> switchPanel("statistik"));
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
            switch (entry.getKey()) {
                case "history":
                    btn.setIcon(Utility.createUniformIcon("assets/histori.png", 20, 20));
                    break;
                case "dataMobil":
                    btn.setIcon(Utility.createUniformIcon("assets/datamobil.png", 24, 20));
                    break;
                case "editLogin":
                    btn.setIcon(Utility.createUniformIcon("assets/edit.png", 24, 20));
                    break;
                case "dataKeuangan":
                    btn.setIcon(Utility.createUniformIcon("assets/keuangan.png", 20, 20));
                    break;
                case "statistik":
                    btn.setIcon(Utility.createUniformIcon("assets/statistik.png", 20, 20));
                    break;
            }
        }
        JButton selected = buttonMap.get(panelName);
        if (selected != null) {
            selected.setBackground(new Color(25, 83, 215));
            selected.setForeground(Color.WHITE);
            switch (panelName) {
                case "history":
                    selected.setIcon(Utility.createUniformIcon("assets/historiw.png", 20, 20));
                    break;
                case "dataMobil":
                    selected.setIcon(Utility.createUniformIcon("assets/datamobilw.png", 24, 20));
                    break;
                case "editLogin":
                    selected.setIcon(Utility.createUniformIcon("assets/editw.png", 24, 20));
                    break;
                case "dataKeuangan":
                    selected.setIcon(Utility.createUniformIcon("assets/keuanganw.png", 20, 20));
                    break;
                case "statistik":
                    selected.setIcon(Utility.createUniformIcon("assets/statistikw.png", 20, 20));
                    break;
            }
        }
    }

    public void switchPanel(String panelName) {
        setSelectedButton(panelName);
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, panelName);
    }
}
