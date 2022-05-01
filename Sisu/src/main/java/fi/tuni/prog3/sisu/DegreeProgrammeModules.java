package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


/**
 * A class representing a Tampere University degree's sub modules. Only used to store first layers data.
 * You should use ModuleData if you want to store all the submodule layers and courses.
 */
public class DegreeProgrammeModules {
    private JsonObject moduleJson;
    private String id;
    private String groupId;
    private TreeMap<String, String> name;

    /**
     * Constructor that takes a string array where first value is json in String formation and second value is the groupId. 
     * Constructs a degreeProgrammeModules object that stores the data of one module without its submodules.
     * @param data String array where first element is the json in string format and the second value is groupId.
     * @throws JsonParseException when there is a error reading the json file.
     * @throws IllegalStateException when there is a error reading the json file.
     */
    public DegreeProgrammeModules(String[] data) {
        name = new TreeMap<>();
        setUpVariables();
        try {
            JsonElement studyModuleTree = JsonParser.parseString(data[0]);
            if (studyModuleTree.isJsonObject()){
                moduleJson = studyModuleTree.getAsJsonObject();
                if (moduleJson.get("name") != null) {
                    setupMethods();
                }
                setupMethods();
            } else if (studyModuleTree.isJsonArray() && !studyModuleTree.getAsJsonArray().isEmpty()) {
                moduleJson = studyModuleTree.getAsJsonArray().get(0).getAsJsonObject();
                setupMethods();
            }
            
        } catch (JsonParseException | IllegalStateException e) {
            System.out.format("Error reading the studyModule json %s: ", data[1]);
            System.out.println(e);
        }
    }

    private void setUpVariables() {
        this.id = "No Id";
        this.groupId = "No groupId";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
    }


    /**
     * @hidden
     */
    private void setupMethods(){
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

    /** 
     * Return module's id.
     * @return String The module id. Returns "No Id" if module has no id.
     */
    public String getId() {
        return this.id;
    }

    /** 
     * Return module's groudId.
     * @return String The module groudId. Returns "No groupId" if module has no groupId.
     */
    public String getGroupId() {
        return this.groupId;
    }
    
    /** 
     * Return a treeMap containing the modules name in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = name.
     * @return TreeMap<String, String> module name in finnish and english. Returns "No name" if there are no name.
     */
    public TreeMap<String, String> getName() {
        return this.name;
    }
}
