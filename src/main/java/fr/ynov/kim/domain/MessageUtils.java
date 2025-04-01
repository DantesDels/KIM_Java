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

                String key = field.getKey(); // Nom à gauche
                JsonNode value = field.getValue(); // Valeur à droite

                if (key.contains("/Messenger")) {
                    System.out.println(value);
                    continue;
                }

                String idString = key.substring(key.lastIndexOf("_") + 1);
                Integer id = null;
                try {
                    id = parseInt(idString);
                } catch (NumberFormatException e) {
                    continue;
                }
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

                Map<Integer, JsonNode> discussionMap = null;
                for (JsonNode node : rootNode) {
                    fields = node.fields();
                    String nameNode = node.has("name") ? node.get("name").asText() : null;
                    if (nameNode == null) {
                        continue;
                    }

                    int id = node.get("id").asInt();
                    String idString = nameNode.substring(nameNode.lastIndexOf("_") + 1);
                    int idMessage = parseInt(idString);
                    String fakeUserName = nameNode.split("/")[4];
                    String conversationObject = nameNode.split("_")[1];

                    ArrayList<Integer> choices = new ArrayList<>();
                    node.get("choices").elements().forEachRemaining(choice -> {
                        choices.add(choice.asInt());
                    });

                    // Stocker dans un tableau intermédiaire les objets de dialogue, pour pouvoir les reparcourir en fin de boucle (une fois qu'on a fini le premier parcours total)
                    // Puis pour chacun des choices, le récupérer par son choiceId, et vérifier si lui même a des choices, et vers quel message il pointe

                    discussionMap = new HashMap<>();
                    discussionMap.put(id, node);
                }

                assert discussionMap != null;
                for (Map.Entry<Integer, JsonNode> entry : discussionMap.entrySet()) {
                    JsonNode node = entry.getValue();
                    int id = entry.getKey();
                    ArrayList<Integer> choices = new ArrayList<>();

                });

                // Map<Integer, Dialogue>
                // Dialogue: type, name, choices (int[])
                try {
                    Message currentMessage = userMessages.get(fakeUserName).get(conversationObject).get(idMessage);
                    for (int choice : choices) {
                        currentMessage.getReplies().add(userMessages.get(fakeUserName).get(conversationObject).get(choice));
                    }

                } catch (NullPointerException e) {
                    continue;
                }
            }

            // Reparcourir une 2e fois le tableau qu'on vient de créer avec key = id et value = type, name, choices[]
            // Indiquer que si il y a un choice, les afficher (si fakeUser parle), la possibilité de les selectionner (si User doit choisir une réponse),
            // Si le key possède une value choice[null], alors c'est un startMessage
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
                 * choice = reponse User / fakeUser
                 * TRUE choice de User = /Choice
                 * start Point = if id != ALL choices[]
                 *
                 *  TO DO : add start Point
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

