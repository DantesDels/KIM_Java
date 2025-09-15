package kim.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * /*
 * id en.json != id .dialogue.json
 * id en.json == name .dialogue.json
 * StartDialogueNode = no Message
 **/

public class MessageUtils {

    // Dictionary for userMessages | jsonMainReader Parsing result
    public static Map<String, Map<Integer, Message>> userMessages = new HashMap<>();

    // Dictionary for Chemistry level associated to FakeUser
    public static Map<String, Integer> chemistryDict = new HashMap<>();

    // Dictionary checking the Booleans associated to FakeUser
    public static Map<String, Map<String, Boolean>> booleanDict = new HashMap<>();


    public static void jsonMainReader(List<FakeUser> fakeUsers) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HashMap<String, String> indexMessages = new HashMap<>();
            JsonNode rootNode = mapper.readTree(new File("./res/discussions_JSON/en.json"));
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                String key = field.getKey(); // fakeUser name on Left
                JsonNode value = field.getValue(); // Value on Right
                indexMessages.put(key, value.asText());// Index of all messages

                if (key.contains("/Messenger")) {
                    System.out.println(value);
                    continue;
                }
            }

            // Parsing the choices & linking the messages
            String[] fakeUsersUsername = {"Arthur", "Eleanor", "Aoi", "Lettie", "Amir", "Quincy"};

            for (String fakeUser : fakeUsersUsername) {
                
                chemistryDict.put(fakeUser, 0);
                booleanDict.put(fakeUser, new HashMap<>());

                FakeUser currentUser = null;
                for (FakeUser user : fakeUsers) {
                    if (user.getUsername().equals(fakeUser)) {
                        currentUser = user;
                        break;
                    }
                }

                // First reading to create all messages

                rootNode = mapper.readTree(new File("./res/discussions_JSON/" + fakeUser + "Dialogue_rom.dialogue.json"));
                Map<Integer, JsonNode> dialogueMap = new HashMap<>();
                ArrayList<Integer> allChoices = new ArrayList<>();
                
                for (JsonNode node : rootNode) {

                    int id = node.get("id").asInt();
                
                String name = node.has("name") ? node.get("name").asText() : null;
                if (name == null) {
                    continue;
                }

                //System.out.println(fakeUserName + " | " + conversationObject + " | " + id + " | " + indexMessages.get(name));

                if (!userMessages.containsKey(fakeUser)) {
                    userMessages.put(fakeUser, new HashMap<Integer, Message>());
                }

                if (name.indexOf("/") == -1) {

                // System.out.println("found for " + name);

                    userMessages.get(fakeUser).put(id, new Message(name, new ArrayList<Message>()));
                } else {
                    userMessages.get(fakeUser).put(id, new Message(indexMessages.get(name), new ArrayList<Message>()));
                }
                
                    ArrayList<Integer> choices = new ArrayList<>();
                    node.get("choices").elements().forEachRemaining(choice -> {
                        choices.add(choice.asInt());
                        allChoices.add(choice.asInt());
                    });
                   
                     dialogueMap.put(id, node);
                    }

                    // Second reading to parse choices
                    for (Map.Entry<Integer, JsonNode> entry : dialogueMap.entrySet()) {

                        JsonNode node = entry.getValue();
                        String nameNode = node.has("name") ? node.get("name").asText() : null;
                        if (nameNode == null) {
                            continue;
                        }
                        
                        String conversationObject = nameNode.split("_").length > 1 ? nameNode.split("_")[1] : nameNode;

                        JsonNode node2 = entry.getValue();
                        int id2 = entry.getKey();
                        Message currentMessage = userMessages.get(fakeUser).get(id2);
                        ArrayList<Integer> choices2 = new ArrayList<>();

                        node2.get("choices").elements().forEachRemaining(choice -> {
                            choices2.add(choice.asInt());
                        }); 

                        if (node2.has("false_choices")) {
                            currentMessage.setMsg("boolean");
                            node2.get("false_choices").elements().forEachRemaining(choice -> {
                                choices2.add(choice.asInt());
                            });
                        }

                        if (node2.has("type") && node2.get("type").asText().indexOf("CheckBool") >= 0) {
                            String boolName = node2.get("name").asText();
                            List<Message> trueChoices = new ArrayList<>();
                            List<Message> falseChoices = new ArrayList<>();

                            node2.get("true_choices").elements().forEachRemaining(choice -> {
                                trueChoices.add(userMessages.get(fakeUser).get(choice.asInt()));
                            });
                            node2.get("false_choices").elements().forEachRemaining(choice -> {
                                falseChoices.add(userMessages.get(fakeUser).get(choice.asInt()));
                            });

                            currentMessage = new MessageCheckBool("boolean", boolName, trueChoices, falseChoices, booleanDict.get(fakeUser));
                        }

                        if (node2.get("type").asText().indexOf("SetBool") >= 0) {
                            String boolName = node2.get("name").asText();
                            currentMessage = new MessageSetBool("boolean", boolName, currentMessage.getReplies(), booleanDict.get(fakeUser));
                        }


                            String typeNode = node2.has("type") ? node2.get("type").asText() : null;
                            for (int choice : choices2) {
                                System.out.println("Linking " + id2 + " to " + choice + " for " + fakeUser);
                            if (typeNode.indexOf("CheckBool") == -1 && typeNode.indexOf("SetBool") == -1) {    
                                currentMessage.getReplies().add(userMessages.get(fakeUser).get(choice));
                            }
                        }
                            if (typeNode.indexOf("Start") >= 0) {
                                Discussion d = new Discussion(conversationObject, currentMessage);
                                currentUser.getScript().add(d);
                            }    
                    }
                }
                // Indiquer que s'il y a un choice, les afficher (si fakeUser parle), la possibilité de les selectionner (si User doit choisir une réponse),
                // Si le key possède une value choice[null], alors c'est un startMessage

            } catch (JsonProcessingException ex) {
        } catch (IOException ex) {
            System.out.println("File not found ! " + ex.getMessage());
        }
    }

    /**
     * Initializes the HardCoded discussion & the fake users.
     *
     * @return the list of fake users
     */

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


        FakeUser Arthur = new FakeUser("Arthur", "Broadsword", new ImageIcon("res/img/Arthur.png"), "Need a cup of coffee first", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet
        )));

        FakeUser Eleanor = new FakeUser("Eleanor", "Salem", new ImageIcon("res/img/Eleanor.png"), "Knows what you think, and yes that's a bad idea...", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet
        )));

        FakeUser Lettie = new FakeUser("Lettie", "Belladona ~{@", new ImageIcon("res/img/Lettie.png"), "Bring me Coffee or Die.", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet
        )));

        FakeUser Amir = new FakeUser("Amir", "H16h V0l7463", new ImageIcon("res/img/Amir.png"), "White Grey or Black, I just wear a Hat !", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet
        )));

        FakeUser Aoi = new FakeUser("Aoi", "xX GLIMMER Xx", new ImageIcon("res/img/Aoi.png"), "On-lyne 4ever ! <3", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet

        )));

        FakeUser Quincy = new FakeUser("Quincy", "Soldja1Shot1kil", new ImageIcon("res/img/Quincy.png"), "Saw you comin' !", new ArrayList<>(List.of(
                discussionStart,
                discussionPlanet
        )));

        Arthur.setOnline();
        Eleanor.setOnline();
        Lettie.setOnline();
        Amir.setOnline();
        Aoi.setOnline();
        Quincy.setOnline();

        return List.of(
                Arthur,
                Eleanor,
                Lettie,
                Amir,
                Aoi,
                Quincy
        );
    }
}
