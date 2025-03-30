
package fr.ynov.kim.domain;

import javax.swing.*;
import java.util.*;
import java.util.Timer;


public class FakeUser extends Person {

    private boolean connectionStatus;
    private String pseudo ;
    private List<Discussion> script;
    private Timer timerUntilNextConnection;
    private ImageIcon profilePicture;

    public FakeUser(String username, String pseudo, ImageIcon profilePicture, String msgBio, List<Discussion> script) {
        super(username, profilePicture, msgBio);

        this.profilePicture = profilePicture;
        this.script = script;
        this.connectionStatus = true;
        this.timerUntilNextConnection = new Timer();
        this.pseudo = pseudo ;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setOnline () {
        connectionStatus = true;
        System.out.println(getUsername() + " is online");
    }

    public List<Discussion> getScript() {
        return script;
    }

    private void reply () {
        while (!script.isEmpty()) {
            Message message = script.get(0).startMessage;
            int timeBetweenMessages = message.getMsg().length() * 1000;
            this.displayTypingEffect();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(getUsername() + ": " + message.getMsg());
                    if (message.getReplies().isEmpty()){
                        setOffline();
                    }
                }
            }, timeBetweenMessages);
        }
    }

    public ImageIcon getProfilePicture() {
        return this.profilePicture;
    }

    private void displayTypingEffect() {
        System.out.println(getUsername() + " is typing...");
    }

    public void setOffline() {
        connectionStatus = false;
        System.out.println(getUsername() + " went offline");
    }

    public void setTimeToNextConnection() {
        timerUntilNextConnection.schedule(new TimerTask() {
            @Override
            public void run() {
                setOnline();

            }
        }, 50); // set on 5sec for testing purposes
    }
}

