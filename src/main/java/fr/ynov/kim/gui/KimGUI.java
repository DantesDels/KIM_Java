package fr.ynov.kim.gui;

import fr.ynov.kim.domain.FakeUser;
import fr.ynov.kim.domain.MessageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class KimGUI extends JFrame {

    public static void main(String[] args) {
        List<FakeUser> fakeUsers = MessageUtils.initiliazeUser();
        SwingUtilities.invokeLater(() -> createAndShowGUI(fakeUsers));
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

    private static void createAndShowGUI(List<FakeUser> fakeUsers) {

        String username = askUsername(JOptionPane.QUESTION_MESSAGE);
        ImageIcon frameIcon = new ImageIcon("res/img/KIMChat.png");

        JFrame frame = new JFrame("Welcome to KIM!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(frameIcon.getImage());
        frame.setResizable(false);

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
        JLabel profilePic = new JLabel(icon);
        profilePanel.add(profilePic);
        topPanel.add(profilePanel, BorderLayout.WEST);

        // Username Panel
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, -200, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Ajouter usernameLabel
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("USERNAME : ");
        usernameLabel.setFont(messageFont);
        usernamePanel.add(usernameLabel, gbc);

        // Ajouter userNameLabel
        gbc.gridy = 1;
        JLabel userNameLabel = new JLabel(username);
        userNameLabel.setFont(usernameFont);
        usernamePanel.add(userNameLabel, gbc);

        topPanel.add(usernamePanel, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        // Contacts Panel
        JPanel contactsPanel = new JPanel();
        contactsPanel.setLayout(new BoxLayout(contactsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contactsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display fake users
        for (FakeUser fakeUser : fakeUsers) {
            contactsPanel.add(createContactPanel(
                    fakeUser.getUsername(),
                    fakeUser.getPseudo(),
                    fakeUser.getScript().get(0).startMessage.getMsg(),
                    usernameFont,
                    messageFont,
                    fakeUser.getProfilePicture()
            ));
        }
        frame.setVisible(true);
    }

    private static JPanel createContactPanel(String username, String pseudo, String lastMessage, Font usernameFont, Font messageFont, ImageIcon profileIcon) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        ImageIcon iconFrame = new ImageIcon("res/img/KIMChat.png");

        // ProfilePic
        profileIcon.setImage(profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(profileIcon);
        profilePic.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        panel.add(profilePic, BorderLayout.WEST);

        // Text Info Panel
        JPanel textPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Username Label
        gbc.gridy = 0;
        gbc.insets = new Insets(0, -60, 0, 0);
        JLabel usernameLabel = new JLabel(pseudo);
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

                    JFrame chatFrame = new JFrame(pseudo);
                    // chatFrame.setFont(usernameFont); doesn't apply
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
                    gbc2.insets = new Insets(0, 25, 0, 25);


                    // Contact Profile
                    ImageIcon iconContact = new ImageIcon("res/img/" + username + ".png");;
                    iconContact.setImage(iconContact.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    JLabel profilePicContact = new JLabel(iconContact);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 0.3;
                    gbc.weighty = 0;
                    chatFrame.add(profilePicContact, gbc);

                    // Contact Button
                    JButton profileButton = new JButton(" PROFILE ");
                    profileButton.setFont(messageFont);
                    profileButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // TO DO : newFrame that shows bioMsg of fakeUser
                        }
                    });
                    gbc2.gridx = 0;
                    gbc2.gridy = 1;
                    chatFrame.add(profileButton, gbc2);


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

                    // User Profile
                    ImageIcon iconUser = new ImageIcon("res/img/WarframeDEFAULT.png");
                    iconUser.setImage(iconUser.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    JLabel profilePicUser = new JLabel(iconUser);
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    gbc.weightx = 0.3;
                    gbc.weighty = 0;
                    chatFrame.add(profilePicUser, gbc);

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