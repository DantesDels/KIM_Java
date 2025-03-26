package fr.ynov.kim.domain;

import java.util.Scanner;

public class User extends Person {

    public User(String username, String profilePicture, String msgBio) {
        super(username, profilePicture, msgBio);

    }

    public void setUsername() {
        Scanner scanUsername = new Scanner(System.in);
        System.out.println("Set Username: ");;
        this.username = scanUsername.nextLine() ;
    }

    public void setProfilePicture(String profilePicture) {
        Scanner scanProfilePicture = new Scanner(System.in);
        // Display all ProfilePictures
        System.out.println("Choose your ProfilePicture: ");
        this.profilePicture = scanProfilePicture.nextLine();
    }

    public void setMsgBio() {
        Scanner scanMsgBio = new Scanner(System.in);
        System.out.println("Change your Bio : ");
        this.msgBio = scanMsgBio.nextLine();
    }
}
