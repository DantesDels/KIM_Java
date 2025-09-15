package kim.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toCollection;
import java.util.ArrayList;

public class MessageCheckBool extends Message {

    public String boolName;
    public List<Message> falseChoices;
    public List<Message> trueChoices;

    public MessageCheckBool(String value, String boolName, List<Message> trueChoices, List<Message> falseChoices, Map<String, Boolean> boolDict) {
        super(value, Stream.concat(trueChoices.stream(), falseChoices.stream()).toList().stream().collect(toCollection(ArrayList::new)));
        this.boolName = boolName;
        this.falseChoices = falseChoices;
        this.trueChoices = trueChoices;

        boolDict.put(boolName, false);
 


    }
}
