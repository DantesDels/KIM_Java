package fr.ynov.kim.domain;

import javax.swing.*;

public abstract class Person {

    String username;
    ImageIcon profilePicture;
    String msgBio;

    public Person (String username, ImageIcon profilePicture, String msgBio) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.msgBio = msgBio;
    }

    public String getUsername() {
        return username;
    }

    public ImageIcon getProfilePicture() {
        return profilePicture;
    }

    public String getMsgBio() {
        return msgBio;
    }
}