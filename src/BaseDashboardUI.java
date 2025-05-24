import java.awt.*;
import javax.swing.*;

public class BaseDashboardUI extends JFrame {
    protected JPanel mainPanel;
    protected JPanel headerPanel;
    protected JButton signOutButton;

    /**
     * Sets up the base dashboard UI with a header, menu bar, and content panel.
     * @param menuBarPanel The menu bar panel (from AdminMenuBar or PegawaiMenuBar)
     * @param contentPanel The main content panel (with CardLayout or other)
     * @param signOutAction The action to perform on sign out (can be null for default)
     */
    protected void setupBaseUI(JPanel menuBarPanel, JPanel contentPanel, Runnable signOutAction) {
        mainPanel = new JPanel(new BorderLayout());

        // Header Panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JPanel clockPanel = Utility.createClockPanel();
        headerPanel.add(clockPanel, BorderLayout.CENTER);

        JPanel userPanel = new JPanel(new GridBagLayout());
        userPanel.setOpaque(false);

        JPanel logoutPanel = new JPanel(new GridBagLayout());
        logoutPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel userIcon = new JLabel(Utility.createUniformIcon("assets/logo.png", 32, 32));
        userIcon.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(userIcon, gbc);

        ImageIcon logoutIcon = Utility.createUniformIcon("assets/logout.png", 20, 20);
        signOutButton = Utility.styleButton("Logout", Color.WHITE);
        signOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutButton.setIcon(logoutIcon);
        signOutButton.setIconTextGap(8);
        signOutButton.setPreferredSize(new Dimension(130, 40));
        signOutButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        signOutButton.setVerticalTextPosition(SwingConstants.CENTER);
        signOutButton.setForeground(Color.RED);
        signOutButton.setContentAreaFilled(true);
        signOutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        signOutButton.setFocusPainted(false);
        if (signOutAction != null) {
            signOutButton.addActionListener(e -> signOutAction.run());
        } else {
            signOutButton.addActionListener(e -> {
                dispose();
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.initialize();
            });
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        logoutPanel.add(signOutButton, gbc);

        headerPanel.add(userPanel, BorderLayout.WEST);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        // Top Panel (header + menu)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(menuBarPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
}