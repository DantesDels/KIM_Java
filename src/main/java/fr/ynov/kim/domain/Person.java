package fr.ynov.kim.domain;

import javax.swing.*;
import java.awt.*;

public abstract class Person {

    String username;
    ImageIcon profilPicture;
    String msgBio;

    public Person (String username, ImageIcon profilPicture, String msgBio) {
        this.username = username;
        this.profilPicture = profilPicture;
        this.msgBio = msgBio;
    }

    public String getUsername() {
        return username;
    }

    public ImageIcon getProfilPicture() {
        return profilPicture;
    }

    public String getMsgBio() {
        return msgBio;
    }
}