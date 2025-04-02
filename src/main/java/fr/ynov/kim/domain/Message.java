package fr.ynov.kim.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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


    public static int extractIdFromJsonKey(String key) {
        int id = parseInt(key.substring(key.lastIndexOf("_") + 1));
        return id;
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
