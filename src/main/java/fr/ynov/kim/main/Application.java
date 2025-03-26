package fr.ynov.kim.main;

import fr.ynov.kim.domain.FakeUser;
import fr.ynov.kim.domain.MessageUtils;
import fr.ynov.kim.domain.Discussion;
import fr.ynov.kim.domain.User;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        User yourself = new User("Default", "insert_profilePicture", "change_msgBio");
        yourself.setUsername();
        yourself.setMsgBio();

        List<FakeUser> fakeUsers =  MessageUtils.initiliazeUser();
        fakeUsers.forEach(fakeUser -> fakeUser.getScript().get((int)(Math.random() * fakeUser.getScript().size())));

        List<Discussion> discuss = fakeUsers.get((int)(Math.random() * fakeUsers.size())).getScript();

        System.out.println("Chat with : " + fakeUser.getUsername());
        discuss.start();
        fakeUser.setTimeToNextConnection();
    }
}
