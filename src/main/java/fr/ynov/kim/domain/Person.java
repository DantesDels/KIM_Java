package fr.ynov.kim.domain;

public abstract class Person {

    String username;
    String profilePicture;
    String msgBio;

    public Person (String username, String profilePicture, String msgBio) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.msgBio = msgBio;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getMsgBio() {
        return msgBio;
    }
}