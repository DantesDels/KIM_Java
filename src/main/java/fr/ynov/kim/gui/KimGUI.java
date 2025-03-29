package fr.ynov.kim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

public class KimGUI extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KimGUI::createAndShowGUI);
    }

    private static Font loadCustomFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) size); // Fallback to Arial if loading fails
        }
    }

    static String askUsername(int iconType) {
        String username = JOptionPane.showInputDialog(null,
                "USERNAME:",
                "Welcome to Kim !",
                iconType);
        if (username.trim().isEmpty()) {
            username = askUsername(JOptionPane.ERROR_MESSAGE);
        }
        return username.trim();
    }

    private static void createAndShowGUI() {

        String username = askUsername(JOptionPane.QUESTION_MESSAGE);

        JFrame frame = new JFrame("Chat Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        // Load custom font
        Font customFont = loadCustomFont("res/fonts/VCR_OSD_MONO_1.001.ttf", 14f);

        // Define common font
        Font usernameFont = customFont.deriveFont(Font.BOLD, 14f);
        Font messageFont = new Font("Comic Sans MS", Font.PLAIN, 12);

        // Top Panel (your Profile and Username)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Profile Picture Panel
        JPanel profilePanel = new JPanel();
        ImageIcon icon = new ImageIcon("res/img/WarframeDEFAULT.png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(icon); // Placeholder image
        profilePanel.add(profilePic);
        topPanel.add(profilePanel, BorderLayout.WEST);

        // Username Panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(17, 5, 0, 0)); // Adjust spacing
        JLabel usernameLabel = new JLabel("USERNAME : " + username);
        usernameLabel.setFont(usernameFont);
        usernamePanel.add(usernameLabel);
        topPanel.add(usernamePanel, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // Contacts Panel
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(contactsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Sample contacts
        for (int i = 0; i < 10; i++) {
            contactsPanel.add(createContactPanel(
                    "WarframeDEFAULT0", "Hello, this is a long message that might be truncated...",
                    usernameFont,
                    messageFont
            ));
        }

        frame.setVisible(true);
    }

    private static JPanel createContactPanel(String username, String lastMessage, Font usernameFont, Font messageFont) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Réduction de l'espace global

        // Profile Pic avec Border pour meilleure alignement
        ImageIcon icon = new ImageIcon("res/img/" + username + ".png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(icon);
        profilePic.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0)); // Ajout d'un padding pour alignement
        panel.add(profilePic, BorderLayout.WEST);

        // Text Info Panel (Better Spacing)
        JPanel textPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alignement gauche

        // Username Label
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -60, 0, 0); // Réduction de l'espace à gauche
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(usernameFont);
        textPanel.add(usernameLabel, gbc);

        // Message Label
        gbc.gridy = 1;
        gbc.insets = new Insets(0, -50, 0, 0); // Réduction de l'espace entre username et message
        JLabel messageLabel = new JLabel("<html><body style='width:150px;'>" + lastMessage + "</body></html>");
        messageLabel.setFont(messageFont);
        textPanel.add(messageLabel, gbc);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }
}
