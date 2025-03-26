package fr.ynov.kim.domain;

import java.util.Collections;
import java.util.List;

public class MessageUtils {

    public static List<FakeUser> initiliazeUser() {

        Message Test = new Message("Choose a start", List.of(
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


        Message Test2 = new Message("Planets", List.of(
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



        Message convoStart = new Message("Drifter.", List.of(
                new Message("Arthur.", List.of(
                        new Message("What're your thoughts about On-lyne?", List.of(
                                new Message("Who?", List.of(
                                        new Message("The band that's everywhere you look.", List.of(
                                                new Message("Oh. Them.", List.of(
                                                        new Message("I just wish they'd STOP.", Collections.emptyList()),
                                                        new Message("I love them! We didn’t have anything like that in Duviri.", List.of(
                                                                new Message("We clearly have different tastes in music.", List.of(
                                                                        new Message("Sup?", List.of(
                                                                                new Message("What're your thoughts about On-lyne?", List.of(
                                                                                        new Message("Who?", List.of(
                                                                                                new Message("The band that's everywhere you look.", List.of(
                                                                                                        new Message("Oh. Them.", List.of(
                                                                                                                new Message("Meh.", List.of(
                                                                                                                        new Message("I just wish they'd STOP.", Collections.emptyList())
                                                                                                                )),
                                                                                                                new Message("Do they only have the one song?", List.of(
                                                                                                                        new Message("Exactly! I just wish they'd STOP.", Collections.emptyList()),
                                                                                                                        new Message("I love them! We didn’t have anything like that in Duviri.", List.of(
                                                                                                                                new Message("We clearly have different tastes in music.", Collections.emptyList()),
                                                                                                                                new Message("Heh.", List.of(
                                                                                                                                        new Message("Nevermind", Collections.emptyList())
                                                                                                                                ))
                                                                                                                        ))
                                                                                                                ))
                                                                                                        ))
                                                                                                ))
                                                                                        ))
                                                                                ))
                                                                        ))
                                                                ))
                                                        ))
                                                ))
                                        ))
                                ))
                        ))
                ))
        ));

        Discussion discussionStart = new Discussion("Start", Test);
        Discussion discussionPlanet = new Discussion("Planets", Test2);
        Discussion discussionArhtur = new Discussion("Drifter.", convoStart);


        FakeUser Tester = new FakeUser("Tester", null, "BioTest", List.of(
                discussionStart
                //discussionPlanet,
                //discussionArhtur
        ));

        FakeUser Amir = new FakeUser("H16h V0l7463", null, "White Grey or Black, I just wear a Hat !", List.of(
                discussionArhtur
        ));

        FakeUser Aoi = new FakeUser("xX GLIMMER Xx", null, "On-lyne 4ever ! <3", List.of(
                discussionArhtur
        ));

        FakeUser Arthur = new FakeUser("Broadsword", null, "Need a cup of coffee first", List.of(
                discussionArhtur
        ));

        FakeUser Eleanor = new FakeUser("Salem", null, "Knows what you think, and yes that's a bad idea...", List.of(
                discussionArhtur
        ));

        FakeUser Ash = new FakeUser("Xx_ShadowM4st3r_xX", null, "I play Ash, so I am Ash.", List.of(
                discussionArhtur
        ));

        Tester.setOnline();
        Amir.setOnline();
        Aoi.setOnline();
        Arthur.setOnline();
        Eleanor.setOnline();
        Ash.setOnline();

        return List.of(
                Tester,
                Amir,
                Aoi,
                Arthur,
                Eleanor,
                Ash
        );
    }
}

