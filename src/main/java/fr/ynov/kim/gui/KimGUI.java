package fr.ynov.kim.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class KimGUI {

    public static void main(String[] args) {

        TextField username = new TextField(15);


        Frame frame = new Frame("Welcome to KIM!");
        //Frame params

        Label label = new Label("Hello World");
        //Label params

        GridLayout layout = new GridLayout(5,1);

        Panel panel = new Panel(layout);


        frame.add(panel);
        layout.addLayoutComponent("USERNAME:", username);

        //label.setAlignment(Label.CENTER);
        label.setText("USERNAME:");

        panel.add(label);
        frame.setSize(500, 500);
        panel.add(username);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}
