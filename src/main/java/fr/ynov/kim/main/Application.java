package fr.ynov.kim.main;

import fr.ynov.kim.domain.FakeUser;
import fr.ynov.kim.domain.MessageUtils;
import fr.ynov.kim.domain.Discussion;

public class Application {

    public static void main(String[] args) {
        FakeUser f = MessageUtils.initiliazeUser();
        Discussion d = f.getScript().get((int)(Math.random() * f.getScript().size()));
        System.out.println("Chat with : " + f.getUsername());
        d.start();

    }
}
