package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class DegreeProgrammeData {
    private JsonObject degreeProgrammeObject;
    private String groupId;
    private TreeMap<String, String> name;
    private TreeMap<String, ModuleData> modules;

    /**
     * Constructor of degreeProgrammeData class. Construct a degree progam with given json.
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file
     */
    public DegreeProgrammeData(String[] data){
        name = new TreeMap<>();
        modules = new TreeMap<>();
        try {
            JsonElement degreeProgrammeTree = JsonParser.parseString(data[0]);
            if (degreeProgrammeTree.isJsonObject() || !degreeProgrammeTree.isJsonNull()){
                degreeProgrammeObject = degreeProgrammeTree.getAsJsonObject();
                this.groupId = data[1];
                setup();
            }
        } catch (JsonParseException | IllegalStateException e){
            System.out.format("Error reading the degree programme json %s: ", data[1]);
            System.out.println(e);
        }
    }

    /**
     * @hidden
     */
    private void setup() {
        setName();
        setModules();
    }

    /**
     * @hidden
     */
    private void setName(){
        JsonElement nameElement = degreeProgrammeObject.get("name");
        if (!nameElement.isJsonObject() || nameElement.isJsonNull()){
            System.out.println("error with degree programme name: " + this.groupId);
            return;
        } else {
            try{
                this.name.put("en", nameElement.getAsJsonObject().get("en").getAsString());
                this.name.put("fi", nameElement.getAsJsonObject().get("fi").getAsString());
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with degree programme name: " + this.groupId + " : " + e);
            }
        }
    }

    // TODO
    /**
     * @hidden
     */
    private void setModules(){

    }
  
    /** 
     * Returns the degreeProgramme groupId.
     * @return String groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /** 
     * Return degree programme name where key = language (en, fi), value = name.
     * @return TreeMap<String, String> name
     */
    public TreeMap<String, String> getName() {
        return name;
    }

    /** 
     * Returns the degree programmes submodules, can be ether studyModules or groupingModules.
     * key = module groupId (can be studyModule or groupingModule), value = moduleData object 
     * @return TreeMap<String, moduleData> submoduels. ether are studyModules or groupingModules.
     */
    public TreeMap<String, ModuleData> getModules() {
        return modules;
    }
}
