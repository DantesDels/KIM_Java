package fr.ynov.kim.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Message {

    public int id;
    public String type;
    public String name;
    public int choice;
    private LocalDateTime time;
    private String msg;
    private List<Message> messages;

    public Message(String msg, List<Message> messages) {
        this.time = java.time.LocalDateTime.now();
        this.msg = msg;
        this.messages = messages;

        this.id = id;
        this.type = type;
        this.name = name;
        this.choice = choice;
    }

    public void jsonMainReader() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File("./res/discussions_JSON/en.json"));
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();

                if (key.contains("/Txt")) {
                    System.out.println("Txt : " + value.asText());
                }

                if (key.contains("/Choice")) {
                    System.out.println("Choice : " + value.asText());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int extractIdFromJsonKey(String key) {
        int id = parseInt(key.substring(key.lastIndexOf("_") + 1));
        return id;
    }

    public String getTxtFromFakeUser() {
        return id;
    }

    public String getChoiceFromUser() {
        return choice;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

    public List<Message> getReplies() {
        return messages;
    }

    public Message getReply(int index) {
        return messages.get(index);
    }

    @Override
    public String toString() {
        return "Message [time = " + time.format(DateTimeFormatter.ofPattern("HH:mm")) + ", msg = " + msg + "]";
    }
}
