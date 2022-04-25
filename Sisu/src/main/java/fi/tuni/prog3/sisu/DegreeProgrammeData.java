package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class DegreeProgrammeData {
    private JsonObject degreeProgrammeObject;
    private String groupId;
    private String id;
    private TreeMap<String, String> name;
    private TreeMap<String, DegreeProgrammeModules> fieldOfStudy;
    private TreeMap<String, DegreeProgrammeModules> modules;

    /**
     * Constructor of degreeProgrammeData class. Construct a degree progam with given json.
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file
     */
    public DegreeProgrammeData(String[] data){
        this.name = new TreeMap<>();
        this.modules = new TreeMap<>();
        this.fieldOfStudy = new TreeMap<>();
        try {
            JsonElement degreeProgrammeTree = JsonParser.parseString(data[0]);
            if (degreeProgrammeTree == null || degreeProgrammeTree.isJsonObject()){
                degreeProgrammeObject = degreeProgrammeTree.getAsJsonObject();
                setup();
            } else if (degreeProgrammeTree.isJsonArray()) {
                degreeProgrammeObject = degreeProgrammeTree.getAsJsonArray().get(0).getAsJsonObject();
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
        this.id = "No Id";
        this.groupId = "No groupId";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
        setId();
        setGroupId();
        setName();
        setModules();
    }

    /**
     * @hidden
     */
    private void setName(){
        JsonElement nameElement = degreeProgrammeObject.get("name");
        if (nameElement == null || !nameElement.isJsonObject()){
            System.out.println("error with degree programme name: " + this.groupId);
            return;
        } else {
            try{
                JsonElement nameEn = nameElement.getAsJsonObject().get("en");
                JsonElement nameFi = nameElement.getAsJsonObject().get("fi");
                if (nameEn != null) {
                    this.name.put("en", nameEn.getAsString());
                }
                if (nameFi != null) {
                    this.name.put("fi", nameFi.getAsString());
                }
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with setting studyModule groupID: " + e);
            }
        }
    }

    /**
     * @hidden
     */
    private void setGroupId() {
        JsonElement groupIdElement = degreeProgrammeObject.get("groupId");
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

    /**
     * @hidden
     */
    private void setModules(){
        JsonElement rule = degreeProgrammeObject.get("rule");
        if (rule == null || !rule.isJsonObject()){
            System.out.println("error with degree module rules: " + this.groupId);
            return;
        } else if (rule.getAsJsonObject().get("credits") == null) {
            setRuleHelper(rule, true);
        } else {
            setRuleHelper(rule, false);
        }
    }
    
    /**
     * @hidden
     */
    private void setRuleHelper(JsonElement element, boolean hasFieldOfStudy) {
        try{
            if (element == null) {
                return;
            }
            if (element.isJsonObject()) {
                JsonElement whenRule = element.getAsJsonObject().get("rule");
                JsonElement whenRules = element.getAsJsonObject().get("rules");
                JsonElement ifModule = element.getAsJsonObject().get("moduleGroupId");
                if (whenRule != null) {
                    setRuleHelper(whenRule, hasFieldOfStudy);
                } else if (whenRules != null) {
                    setRuleHelper(whenRules, hasFieldOfStudy);
                } else if (ifModule != null && hasFieldOfStudy == false) {
                    String groupId = ifModule.getAsString();
                    networkHandler handler = new networkHandler();
                    DegreeProgrammeModules temp = new DegreeProgrammeModules(handler.getModuleByGroupId(groupId));
                    this.modules.put(groupId, temp);
                }  else if (ifModule != null && hasFieldOfStudy == true) {
                    String groupId = ifModule.getAsString();
                    networkHandler handler = new networkHandler();
                    DegreeProgrammeModules temp = new DegreeProgrammeModules(handler.getModuleByGroupId(groupId));
                    this.fieldOfStudy.put(groupId, temp);
                }
            } else if (element.isJsonArray()) {
                JsonArray whenRules = element.getAsJsonArray();
                for (var arrayElement : whenRules) {
                    setRuleHelper(arrayElement, hasFieldOfStudy);
                }
            }
        } catch(ClassCastException | IllegalStateException e) {
            System.out.println("Error with degree module rules: " + this.groupId + " : " + e);
        }
    }

    /**
     * @hidden
     */
    private void setId() {
        JsonElement IdElement = degreeProgrammeObject.get("id");
        if (IdElement == null || !IdElement.isJsonPrimitive()){
            System.out.println("error with studyModule groupid");
            return;    
        } else {
            try {
                this.id = IdElement.getAsString();
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with setting studyModule groupID: " + e);
            }
        }
    }

    /** 
     * Returns the degreeProgramme id.
     * @return String id
     */
    public String getId() {
        return this.id;
    }

    /** 
     * Returns the degreeProgramme groupId.
     * @return String groupId
     */
    public String getGroupId() {
        return this.groupId;
    }

    /** 
     * Return degree programme name where key = language (en, fi), value = name.
     * @return TreeMap<String, String> name
     */
    public TreeMap<String, String> getName() {
        return this.name;
    }

    /** 
     * Returns the degree programmes submodules, can be ether studyModules or groupingModules. 
     * Example case might be something like tietotekniikka or sähkötekniikka if the degreeProgrammeData object is 
     * tieto- ja sähkötekniikan kanditaattiohjelma.
     * key = module groupId (can be studyModule or groupingModule), value = moduleData object 
     * @return TreeMap<String, moduleData> submoduels. ether are studyModules or groupingModules.
     */
    public TreeMap<String, DegreeProgrammeModules> getModules() {
        return this.modules;
    }

    /** 
     * Returns the degree programmes field of studies.
     * Example case might be something like tietotekniikka or sähkötekniikka if the degreeProgrammeData object is 
     * tieto- ja sähkötekniikan kanditaattiohjelma.
     * key = module groupId (can be studyModule or groupingModule), value = moduleData object 
     * @return TreeMap<String, moduleData> field of studies.
     */
    public TreeMap<String, DegreeProgrammeModules> getFieldOfStudy() {
        return this.fieldOfStudy;
    }
}
