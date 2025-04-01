package fr.ynov.kim.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * /*
 * id != choices
 * id = conversion name
 * choice = reponse User / fakeUser
 * TRUE choice de User = /Choice
 * start Point = if id != ALL choices[]
 *  TO DO : add start Point
 **/

public class MessageUtils {

    public static Map<String, Map<String, Map<Integer, Message>>> userMessages = new HashMap<String, Map<String, Map<Integer, Message>>>();

    public static void jsonMainReader() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File("./res/discussions_JSON/en.json"));
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                String key = field.getKey(); // fakeUser name on Left
                JsonNode value = field.getValue(); // Value on Right

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

                Map<Integer, JsonNode> dialogueMap = new HashMap<>();

                for (JsonNode node : rootNode) {

                    int id = node.get("id").asInt();

                    ArrayList<Integer> choices = new ArrayList<>();
                    node.get("choices").elements().forEachRemaining(choice -> {
                        choices.add(choice.asInt());
                    });

                    // Stocker dans un tableau intermédiaire les objets de dialogue, pour pouvoir les reparcourir en fin de boucle (une fois qu'on a fini le premier parcours total)
                    // Puis pour chacun des choices, le récupérer par son choiceId, et vérifier si lui même a des choices, et vers quel message il pointe

                     dialogueMap.put(id, node);
                    }

                    // Second reading to parse choices
                    for (Map.Entry<Integer, JsonNode> entry : dialogueMap.entrySet()) {

                        JsonNode node = entry.getValue();
                        String nameNode = node.has("name") ? node.get("name").asText() : null;
                        if (nameNode == null) {
                            continue;
                        }

                        try {
                            String fakeUserName = nameNode.split("/")[4];
                            String conversationObject = nameNode.split("_")[1];

                        JsonNode node2 = entry.getValue();
                        int id2 = entry.getKey();

                        ArrayList<Integer> choices2 = new ArrayList<>();
                        node2.get("choices").elements().forEachRemaining(choice -> {
                            choices2.add(choice.asInt());
                        });

                        try {
                            Message currentMessage = userMessages.get(fakeUserName).get(conversationObject).get(id2);
                            for (int choice : choices2) {
                                currentMessage.getReplies().add(userMessages.get(fakeUserName).get(conversationObject).get(choice));
                            }
                        } catch (NullPointerException e) {}
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                }
                // Indiquer que si il y a un choice, les afficher (si fakeUser parle), la possibilité de les selectionner (si User doit choisir une réponse),
                // Si le key possède une value choice[null], alors c'est un startMessage

            } catch (JsonProcessingException ex) {
        } catch (IOException ex) {
        }
    }

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


        Discussion discussionStart = new Discussion("Start", Test);
        Discussion discussionPlanet = new Discussion("Planets", Test2);


        FakeUser Amir = new FakeUser("Amir", "H16h V0l7463", new ImageIcon("res/img/Amir.png"), "White Grey or Black, I just wear a Hat !", List.of(
                discussionStart,
                discussionPlanet
        ));

        FakeUser Aoi = new FakeUser("Aoi", "xX GLIMMER Xx", new ImageIcon("res/img/Aoi.png"), "On-lyne 4ever ! <3", List.of(
                discussionStart,
                discussionPlanet

        ));

        FakeUser Arthur = new FakeUser("Arthur", "Broadsword", new ImageIcon("res/img/Arthur.png"), "Need a cup of coffee first", List.of(
                discussionStart,
                discussionPlanet
        ));

        FakeUser Eleanor = new FakeUser("Eleanor", "Salem", new ImageIcon("res/img/Eleanor.png"), "Knows what you think, and yes that's a bad idea...", List.of(
                discussionStart,
                discussionPlanet
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

