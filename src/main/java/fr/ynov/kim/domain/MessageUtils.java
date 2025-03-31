package fr.ynov.kim.domain;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class MessageUtils {

    public static Map<String, Map<String, Map<Integer, Message>>> userMessages = new HashMap<String, Map<String, Map<Integer, Message>>>();

    public static String jsonMainReader() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File("./res/discussions_JSON/en.json"));
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                String key = field.getKey();
                JsonNode value = field.getValue();

                if (key.contains("/Messenger")) {
                    System.out.println(value);
                    continue;
                }

                String idString = key.substring(key.lastIndexOf("_") + 1);
                int id = parseInt(idString);
                String fakeUserName = key.split("/")[4];
                String conversationObject = key.split("_")[1];
                System.out.println(fakeUserName + " " + conversationObject + " " + id + " " + value.asText());

                if (!userMessages.containsKey(fakeUserName)) {
                    userMessages.put(fakeUserName, new HashMap<String, Map<Integer, Message>>());
                }
                if (!userMessages.get(fakeUserName).containsKey(conversationObject)) {
                    userMessages.get(fakeUserName).put(conversationObject, new HashMap<Integer, Message>());
                }
                userMessages.get(fakeUserName).get(conversationObject).put(id, new Message(value.textValue(), new ArrayList<Message>()));
            }

            String[] fakeUsers = {"Aoi"};

            for (String fakeUser : fakeUsers) {

                rootNode = mapper.readTree(new File("./res/discussions_JSON/" + fakeUser + "Dialogue_rom.dialogue.json"));

                for (JsonNode node : rootNode) {
                    fields = node.fields();
                    int id = 0 ;
                    ArrayList<Integer> choices = new ArrayList<>();
                    String fakeUserName = "";
                    String conversationObject = "";

                    while (fields.hasNext()) {
                        Map.Entry<String, JsonNode> field = fields.next();
                        String key = field.getKey();
                        JsonNode value = field.getValue();

                        if (key.equals("id")) {
                            id = parseInt(value.asText());
                        }

                        if (key.equals("choices")){
                            for (JsonNode choice : value) {
                                choices.add(parseInt(choice.asText()));
                            }
                        }

                        if (key.equals("name")) {
                            String idString = value.asText().substring(value.asText().lastIndexOf("_") + 1);
                            id = parseInt(idString);
                            fakeUserName = value.asText().split("/")[4];
                            conversationObject = value.asText().split("_")[1];
                        }

                        Message currentMessage = userMessages.get(fakeUserName).get(conversationObject).get(id);

                        for (int choice : choices) {
                            currentMessage.getReplies().add(userMessages.get(fakeUserName).get(conversationObject).get(choice));
                        }
                    }
                }
            }

                /*
                if (key.contains("/Txt")) {
                    System.out.println(key + " Txt "+ id + " " + value.asText());
                }

                if (key.contains("/Choice")) {
                    System.out.println(key + " Choice " + id + " " + value.asText());
                } */


        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return null;
    }


    public static List<FakeUser> initiliazeUser() {

        /*
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
*/


        FakeUser Amir = new FakeUser("Amir", "H16h V0l7463", new ImageIcon("res/img/Amir.png"), "White Grey or Black, I just wear a Hat !", List.of(
                //discussionArhtur
        ));

        FakeUser Aoi = new FakeUser("Aoi", "xX GLIMMER Xx", new ImageIcon("res/img/Aoi.png"), "On-lyne 4ever ! <3", List.of(
                /**
                 * /*
                 * id != choices
                 * id = conversion name
                 * choices = replies from User / fakeUser
                 *
                 * TRUE choice for User = String contains ".../Choice..."
                 *
                 * start Point of Discussion (1st Message) = if id != ALL choices[]
                 *
                 *  TO DO : find & add start Point
                 * */
        ));


        FakeUser Arthur = new FakeUser("Arthur", "Broadsword", new ImageIcon("res/img/Arthur.png"), "Need a cup of coffee first", List.of(
                //discussionArhtur
        ));

        FakeUser Eleanor = new FakeUser("Eleanor", "Salem", new ImageIcon("res/img/Eleanor.png"), "Knows what you think, and yes that's a bad idea...", List.of(
                //discussionArhtur
        ));


        Amir.setOnline();
        Aoi.setOnline();
        Arthur.setOnline();
        Eleanor.setOnline();

        return List.of(
                Amir,
                Aoi,
                Arthur,
                Eleanor
        );
    }
}

