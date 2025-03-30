package fr.ynov.kim.domain;

import java.util.Scanner;

public class User extends Person {

    public User(String username, String profilPicture, String msgBio) {
        super(username, profilPicture, msgBio);

    }

    public void setUsername() {
        Scanner scanUsername = new Scanner(System.in);
        System.out.println("Set Username: ");
        this.username = scanUsername.nextLine() ;
    }

    public void setProfilPicture(String profilPicture) {
        Scanner scanProfilPicture = new Scanner(System.in);
        // Display all ProfilPictures
        System.out.println("Choose your ProfilPicture: ");
        this.profilPicture = scanProfilPicture.nextLine();
    }

    public void setMsgBio() {
        Scanner scanMsgBio = new Scanner(System.in);
        System.out.println("Change your Bio : ");
        this.msgBio = scanMsgBio.nextLine();
    }
}
