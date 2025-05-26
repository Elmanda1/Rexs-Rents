import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class EditLoginPegawaiPanel {
    public static JPanel create() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIcon = new JLabel();
        ImageIcon originalIcon = new ImageIcon("assets/logolandingpage.png"); // Path
        Image scaledImage = originalIcon.getImage().getScaledInstance(
                (int) (originalIcon.getIconWidth() * 1), // 80% of original width
                (int) (originalIcon.getIconHeight() * 1), // 80% of original height
                Image.SCALE_SMOOTH);
        userIcon.setIcon(new ImageIcon(scaledImage));
        userIcon.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Let the logo take two columns
        panel.add(userIcon, gbc);

        JLabel usernameLabel = Utility.styleLabel("Username Pegawai:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = Utility.styleTextField(true);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        gbc.ipady = 10;

        panel.add(usernameField, gbc);

        JLabel passwordLabel = Utility.styleLabel("Password Pegawai:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new Utility.RoundedPasswordField(20);
        passwordField.setBackground(new Color(220, 230, 250));

        JPanel passwordPanel = Utility.createPasswordTogglePanel(passwordField);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        panel.add(passwordPanel, gbc);

        ImageIcon simpanicon = Utility.createUniformIcon("assets/save.png", 20, 20);

        JButton saveButton = Utility.styleButton("Simpan", new Color(255, 87, 51));
        saveButton.setIcon(simpanicon);
        saveButton.setIconTextGap(5);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Simpan informasi login ke database
            String result = updateLoginPegawai(username, password);
            if (result.equals("success")) {
                JOptionPane.showMessageDialog(null, "Data login pegawai berhasil diubah!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal mengubah data login: " + result,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private static String updateLoginPegawai(String username, String password) {
        String result = "";
        try (Connection con = Utility.connectDB()) {
            String query = "UPDATE tb_akun SET username = ?, password = ? WHERE role = 'Employee'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                result = "success";
            } else {
                result = "Username tidak ditemukan.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Terjadi kesalahan: " + e.getMessage();
        }
        return result;
    }
}
