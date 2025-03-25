package fr.ynov.kim.domain;

import java.util.Collections;
import java.util.List;

public class MessageUtils {

    public static FakeUser initiliazeUser() {

        Message message = new Message("Choose a start", List.of(
                new Message("Start1", List.of(
                        new Message("1.1", Collections.emptyList()),
                        new Message("1.2", List.of(
                                new Message("1.2.1", Collections.emptyList())
                        ))
                )),
                new Message("Start2", List.of(
                        new Message("2.1", Collections.emptyList()),
                        new Message("2.2", Collections.emptyList())
                ))
        ));

        Message message2 = new Message("Planets", List.of(
                new Message("Saturne", List.of(
                        new Message("Solar", Collections.emptyList()),
                        new Message("Pluton", List.of(
                                new Message("Uranus", Collections.emptyList())
                        ))
                )),
                new Message("Venus", List.of(
                        new Message("Jupiter", Collections.emptyList()),
                        new Message("Mars", Collections.emptyList())
                ))
        ));

        Discussion discussionStart = new Discussion("Start", message);
        Discussion discussionPlanet = new Discussion("Planets", message2);

        FakeUser toto = new FakeUser("toto", null, "bonjour bio", List.of(
                discussionStart,
                discussionPlanet
        ));
        toto.setOnline();
        return toto;
    }
}

