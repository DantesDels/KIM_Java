package fr.ynov.kim.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Application {

    public static void launch() {

        User yourself = new User("Default", null, "change_msgBio");
        yourself.setUsername();
        yourself.setMsgBio();

        List<FakeUser> fakeUsers = MessageUtils.initiliazeUser();

        Map<FakeUser, Discussion> history = new HashMap<>();
        for (FakeUser fakeUser : fakeUsers) {
            history.put(fakeUser, fakeUser.getScript().get((int) (Math.random() * fakeUser.getScript().size())));
        }

        int fakeUserId = 0;
        Scanner scanDiscussion = new Scanner(System.in);

        while (fakeUserId != -1) {
            try {
                int i = 0;
                for(FakeUser fakeUser : fakeUsers) {
                    System.out.println(i + " : " + fakeUser.getUsername() + "\n" + history.get(fakeUser).startMessage.getMsg());
                    i++;
                }
                fakeUserId = scanDiscussion.nextInt();
                Discussion d = history.get(fakeUsers.get(fakeUserId));
                d.start();
                fakeUsers.get(fakeUserId).setTimeToNextConnection();
            } catch (IndexOutOfBoundsException e) {}
        }
        System.out.println("Sortie de programme");
    }
}