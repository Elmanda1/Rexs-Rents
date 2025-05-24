import java.awt.*;
import javax.swing.*;

public class AdminMenuBar {
    public static JPanel create(GUIAdmin parent, JPanel contentPanel) {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JButton historyButton = new JButton("Histori Transaksi");
        historyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        historyButton.setIcon(Utility.createUniformIcon("assets/historiw.png", 20, 20));
        historyButton.setIconTextGap(8);
        historyButton.setPreferredSize(new Dimension(200, 40));
        historyButton.setMargin(new Insets(8, 15, 8, 15));
        historyButton.setFocusPainted(false);

        JButton dataMobilButton = new JButton("Data Mobil");
        dataMobilButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dataMobilButton.setIcon(Utility.createUniformIcon("assets/datamobil.png", 24, 20));
        dataMobilButton.setIconTextGap(8);
        dataMobilButton.setPreferredSize(new Dimension(150, 40));
        dataMobilButton.setMargin(new Insets(8, 15, 8, 15));
        dataMobilButton.setFocusPainted(false);

        JButton editLoginButton = new JButton("Edit Informasi Login Pegawai");
        editLoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editLoginButton.setIcon(Utility.createUniformIcon("assets/edit.png", 24, 20));
        editLoginButton.setIconTextGap(8);
        editLoginButton.setPreferredSize(new Dimension(250, 40));
        editLoginButton.setMargin(new Insets(8, 15, 8, 15));
        editLoginButton.setFocusPainted(false);

        JButton dataKeuanganButton = new JButton("Data Keuangan");
        dataKeuanganButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dataKeuanganButton.setIcon(Utility.createUniformIcon("assets/keuangan.png", 20, 20));
        dataKeuanganButton.setIconTextGap(8);
        dataKeuanganButton.setPreferredSize(new Dimension(180, 40));
        dataKeuanganButton.setMargin(new Insets(8, 15, 8, 15));
        dataKeuanganButton.setFocusPainted(false);

        JButton statistikButton = new JButton("Statistik");
        statistikButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        statistikButton.setIcon(Utility.createUniformIcon("assets/datamobil.png", 24, 20));
        statistikButton.setIconTextGap(8);
        statistikButton.setPreferredSize(new Dimension(150, 40));
        statistikButton.setMargin(new Insets(8, 15, 8, 15));
        statistikButton.setFocusPainted(false);

        // Set default selected button
        historyButton.setBackground(new Color(25, 83, 215));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBorderPainted(false);
        dataMobilButton.setBackground(Color.WHITE);
        dataMobilButton.setBorderPainted(false);
        editLoginButton.setBackground(Color.WHITE);
        editLoginButton.setBorderPainted(false);
        dataKeuanganButton.setBackground(Color.WHITE);
        dataKeuanganButton.setBorderPainted(false);
        statistikButton.setBackground(Color.WHITE);
        statistikButton.setBorderPainted(false);

        menuPanel.add(historyButton);
        menuPanel.add(dataMobilButton);
        menuPanel.add(editLoginButton);
        menuPanel.add(dataKeuanganButton);
        menuPanel.add(statistikButton);

        Utility.addButtonHoverEffect(historyButton);
        Utility.addButtonHoverEffect(dataMobilButton);
        Utility.addButtonHoverEffect(editLoginButton);
        Utility.addButtonHoverEffect(dataKeuanganButton);
        Utility.addButtonHoverEffect(statistikButton);

        historyButton.addActionListener(e -> parent.switchPanel("history", historyButton));
        dataMobilButton.addActionListener(e -> parent.switchPanel("dataMobil", dataMobilButton));
        editLoginButton.addActionListener(e -> parent.switchPanel("editLogin", editLoginButton));
        dataKeuanganButton.addActionListener(e -> parent.switchPanel("dataKeuangan", dataKeuanganButton));
        statistikButton.addActionListener(e -> parent.switchPanel("statistik", statistikButton));

        return menuPanel;
    }
}
