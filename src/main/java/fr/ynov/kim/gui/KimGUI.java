package fr.ynov.kim.gui;
/*
public class KimGUI {

    public static void main(String[] args) {


        TextField username = new TextField(15);

        Frame frameLogin = new Frame("Welcome to KIM!");
        Frame frameApplication = new Frame("KIM Contacts");
        Frame frameChat = new Frame("ChatBox");
        //Frame params


        Label labelApplication0 = new Label("Hello World");
        //Label params

        GridLayout layout = new GridLayout(5,1);

        Panel panelApplication0 = new Panel(layout);

        frameApplication.add(panelApplication0);
        layout.addLayoutComponent("USERNAME:", username);

        //label.setAlignment(Label.CENTER);
        labelApplication0.setText("USERNAME:");

        panelApplication0.add(labelApplication0);
        frameApplication.setSize(500, 500);
        panelApplication0.add(username);
        frameApplication.setVisible(true);

        frameApplication.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    }

     */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class KimGUI extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(KimGUI::createAndShowGUI);
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

        // Top Panel (Profile and Username)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Profile Picture Panel
        JPanel profilePanel = new JPanel();
        ImageIcon icon = new ImageIcon("res/img/WarframeDEFAULT0.png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(icon); // Placeholder image
        profilePanel.add(profilePic);
        topPanel.add(profilePanel, BorderLayout.WEST);

        // Username Input with Placeholder
        JPanel usernamePanel = new JPanel(new GridLayout(2, 1));
        JLabel usernameLabel = new JLabel("USERNAME : " + username);

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
            contactsPanel.add(createContactPanel("WarframeDEFAULT0", "Hello, this is a long message that might be truncated..."));
        }

        frame.setVisible(true);
    }

    private static JPanel createContactPanel(String username, String lastMessage) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Profile Pic
        ImageIcon icon = new ImageIcon("res/img/" + username + ".png");
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(icon);
        panel.add(profilePic, BorderLayout.WEST);

        // Text Info
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        JLabel usernameLabel = new JLabel(username);
        JLabel messageLabel = new JLabel("<html><body style='width:150px;'>" + lastMessage + "</body></html>");
        textPanel.add(usernameLabel);
        textPanel.add(messageLabel);
        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }
}
