package fr.ynov.kim.domain;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalDateTime;


public class Message {

    private LocalDateTime time;
    private String msg;
    private List<Message> messages;

    public Message(String msg, List<Message> messages) {
        this.time = java.time.LocalDateTime.now();
        this.msg = msg;
        this.messages = messages;
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
