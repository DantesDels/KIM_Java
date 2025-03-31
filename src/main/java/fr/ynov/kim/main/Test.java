package fr.ynov.kim.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Test {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File("./res/discussions_JSON/en.json"));
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                JsonNode value = field.getValue();

                if (key.contains("/Txt")) {
                    String idString = key.substring(key.lastIndexOf("_") + 1);
                    int id = parseInt(idString);
                    //System.out.println("Txt : " + value.asText());
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
}