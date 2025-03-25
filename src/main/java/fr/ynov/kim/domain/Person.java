package fr.ynov.kim.domain;

public abstract class Person {

    private String username;
    private String profilPicture;
    private String msgBio;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilPicture(String profilPicture) {
        this.profilPicture = profilPicture;
    }

    public void setMsgBio(String msgBio) {
        this.msgBio = msgBio;
    }
}