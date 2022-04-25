package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import fi.tuni.prog3.sisu.networkHandler;

public class DegreeProgrammeModules {
    private JsonObject moduleJson;
    private String id;
    private String groupId;
    private TreeMap<String, String> name;

    public DegreeProgrammeModules(String[] data) {
        name = new TreeMap<>();
        
        try {
            JsonElement studyModuleTree = JsonParser.parseString(data[0]);
            if (studyModuleTree.isJsonObject()){
                moduleJson = studyModuleTree.getAsJsonObject();
                setup();
            } else if (studyModuleTree.isJsonArray()) {
                moduleJson = studyModuleTree.getAsJsonArray().get(0).getAsJsonObject();
                setup();
            }
            
        } catch (JsonParseException | IllegalStateException e) {
            System.out.format("Error reading the studyModule json %s: ", data[1]);
            System.out.println(e);
        }
    }

    private void setup(){
        this.id = "No Id";
        this.groupId = "No groupId";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
        setId();
        setGroupId();
        setName();
    }

        /**
     * @hidden
     */
    private void setId() {
        JsonElement groupIdElement = moduleJson.get("id");
        if (groupIdElement == null || !groupIdElement.isJsonPrimitive()){
            System.out.println("error with studyModule groupid");
            return;    
        } else {
            try {
                this.id = groupIdElement.getAsString();
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with setting studyModule groupID: " + e);
            }
        }
    }
    
    /**
     * @hidden
     */
    private void setGroupId() {
        JsonElement groupIdElement = moduleJson.get("groupId");
        if (groupIdElement == null || !groupIdElement.isJsonPrimitive()){
            System.out.println("error with studyModule groupid");
            return;    
        } else {
            try {
                this.groupId = groupIdElement.getAsString();
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with setting studyModule groupID: " + e);
            }
        }
    }

    private void setName(){
        JsonElement nameElement = moduleJson.get("name");
        if (nameElement == null || !nameElement.isJsonObject()){
            System.out.println("error with studyModule name: " + this.id);
            return;
        } else {
            try{
                JsonElement nameEn = nameElement.getAsJsonObject().get("en");
                JsonElement nameFi = nameElement.getAsJsonObject().get("fi");
                if (nameEn != null && !nameEn.isJsonNull()) {
                    this.name.put("en", nameEn.getAsString());
                }
                if (nameFi != null && !nameFi.isJsonNull()) {
                    this.name.put("fi", nameFi.getAsString());
                }
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with studyModule name: " + this.id + " : " + e);
            }
        }
    }
}
