package fr.ynov.kim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
            return new Font("Arial", Font.PLAIN, (int) size);
        }
    }

    static String askUsername(int iconType) {
        String username = JOptionPane.showInputDialog(null,
                "USERNAME:",
                "Kinemantik Instant Messenger",
                iconType);
        if (username.trim().isEmpty()) {
            username = askUsername(JOptionPane.ERROR_MESSAGE);
        }
        return username.trim();
    }

    private static void createAndShowGUI() {

        String username = askUsername(JOptionPane.QUESTION_MESSAGE);
        ImageIcon frameIcon = new ImageIcon("res/img/KIMChat.png");

        JFrame frame = new JFrame("Welcome to KIM!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(frameIcon.getImage());

        // Load custom font
        Font customFont = loadCustomFont("res/fonts/VCR_OSD_MONO_1.001.ttf", 14f);

        // Define common font
        Font usernameFont = customFont.deriveFont(Font.BOLD, 14f);
        Font messageFont = new Font("Comic Sans MS", Font.PLAIN, 12);

        // Top Panel (your Profil and Username)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Profil Picture Panel
        JPanel profilPanel = new JPanel();
        ImageIcon icon = new ImageIcon("res/img/WarframeDEFAULT.png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilPic = new JLabel(icon);
        profilPanel.add(profilPic);
        topPanel.add(profilPanel, BorderLayout.WEST);

        // Username Panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(17, 5, 0, 0));
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
                    "WarframeDEFAULT0",
                    "Hello, this is a long message that might be truncated...",
                    usernameFont,
                    messageFont
            ));
        }
        frame.setVisible(true);
    }

    private static JPanel createContactPanel(String username, String lastMessage, Font usernameFont, Font messageFont) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        ImageIcon iconFrame = new ImageIcon("res/img/KIMChat.png");

        // ProfilPic
        ImageIcon icon = new ImageIcon("res/img/" + username + ".png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilPic = new JLabel(icon);
        profilPic.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        panel.add(profilPic, BorderLayout.WEST);

        // Text Info Panel
        JPanel textPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Username Label
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -60, 0, 0);
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(usernameFont);
        textPanel.add(usernameLabel, gbc);

        // Message Label
        gbc.gridy = 1;
        gbc.insets = new Insets(0, -50, 0, 0);
        JLabel messageLabel = new JLabel(
                "<html><body style='width:150px;'>"
                        + lastMessage +
                "</body></html>");
        messageLabel.setFont(messageFont);
        textPanel.add(messageLabel, gbc);
        messageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                super.mousePressed(e);
                if (e.getClickCount() == 1){
                    Font messageFont2 = new Font("Comic Sans MS", Font.BOLD, 11);
                    messageLabel.setFont(messageFont2);
                }
            }
            public void mouseReleased (MouseEvent e) {
                super.mouseReleased(e);
                if (e.getClickCount() == 1){
                    messageLabel.setFont(messageFont);
                }
            }
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {

                    JFrame chatFrame = new JFrame(username);
                    chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    chatFrame.setIconImage(iconFrame.getImage());
                    chatFrame.setSize(950, 600);
                    chatFrame.setLocationRelativeTo(null);
                    chatFrame.setLayout(new GridBagLayout());
                    chatFrame.setVisible(true);

                    GridBagConstraints gbc = new GridBagConstraints();
                    GridBagConstraints gbc2 = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc2.fill = GridBagConstraints.HORIZONTAL;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc2.insets = new Insets(0, 10, 0, 5 );


                    // Contact Profil
                    ImageIcon iconContact = new ImageIcon("res/img/" + username + ".png");
                    iconContact.setImage(iconContact.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    JLabel profilPicContact = new JLabel(iconContact);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 0.3;
                    gbc.weighty = 0;
                    chatFrame.add(profilPicContact, gbc);

                    // Contact Button
                    JButton profilButton = new JButton(" PROFILE ");
                    profilButton.setFont(messageFont);
                    profilButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TO DO : newFrame that shows bioMsg of fakeUser
                        }
                    });
                    gbc2.gridx = 0;
                    gbc2.gridy = 1;
                    chatFrame.add(profilButton, gbc2);


                    // chatArea
                    JPanel chatArea = new JPanel();
                    chatArea.setAutoscrolls(true);
                    JScrollPane chatScroll = new JScrollPane(chatArea);
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.gridheight = 3;
                    gbc.weightx = 6;
                    gbc.weighty = 1;
                    chatFrame.add(chatScroll, gbc);

                    // User Profil
                    ImageIcon iconUser = new ImageIcon("res/img/WarframeDEFAULT.png");
                    iconUser.setImage(iconUser.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    JLabel profilPicUser = new JLabel(iconUser);
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    gbc.weightx = 0.3;
                    gbc.weighty = 0;
                    chatFrame.add(profilPicUser, gbc);

                    // reply Area
                    JPanel ReplyArea = new JPanel();
                    JScrollPane ReplyScroll = new JScrollPane(ReplyArea);
                    gbc.gridx = 1;
                    gbc.gridy = 3;
                    gbc.weightx = 6;
                    gbc.weighty = 0;
                    chatFrame.add(ReplyScroll, gbc);
                }
            }
        });

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }
}
