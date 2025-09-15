package kim.domain;

import java.util.List;
import java.util.Map;

public class MessageSetBool extends Message {

    public String boolName;

    public MessageSetBool(String value, String boolName, List<Message> messages, Map<String, Boolean> boolDict) {
        super(value, messages);
        this.boolName = boolName;
        
        boolDict.put(boolName, false);
    }
}
