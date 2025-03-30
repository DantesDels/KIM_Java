package fr.ynov.kim.domain;

public abstract class Person {

    String username;
    String profilPicture;
    String msgBio;

    public Person (String username, String profilPicture, String msgBio) {
        this.username = username;
        this.profilPicture = profilPicture;
        this.msgBio = msgBio;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilPicture() {
        return profilPicture;
    }

    public String getMsgBio() {
        return msgBio;
    }
}