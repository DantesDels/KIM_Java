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

    public Message(String value, List<Message> messages) {
        this.time = java.time.LocalDateTime.now();
        this.msg = value;
        this.messages = messages;

        this.id = id;
        this.type = type;
        this.name = name;
        this.choice = choice;
    }

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

                if (key.contains("/Txt")) {
                    System.out.println(key + " Txt "+ id + " " + value.asText());
                }

                if (key.contains("/Choice")) {
                    System.out.println(key + " Choice " + id + " " + value.asText());
                }
            }
        } catch (IOException e) {
           System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return null;
    }

    public static int extractIdFromJsonKey(String key) {
        jsonMainReader();
        int id = parseInt(key.substring(key.lastIndexOf("_") + 1));
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMsg() {
        jsonMainReader();
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
