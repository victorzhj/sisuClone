package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import fi.tuni.prog3.sisu.networkHandler;

public class ModuleData{
    private JsonObject moduleJson;
    private String id;
    private String groupId;
    private String targetCredits;
    private String moduleType;
    // key = language, value = name
    private TreeMap<String, String> name;

    // key = groupid, value = courseData
    private TreeMap<String, CourseData> whenSubModuleAreCourses;
    // key = module id, value = module
    private TreeMap<String, ModuleData> whenSubmoduleAreModules;

    /**
     * Constructor of moduleData class. Construct an module with given json.
     * Can be ether studyModule or groupingModule
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file or getting information out of it
     */
    public ModuleData(String[] data) {
        name = new TreeMap<>();
        whenSubmoduleAreModules = new TreeMap<>();
        whenSubModuleAreCourses = new TreeMap<>();
        
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
        
        /*
        try {
            JsonElement studyModuleTree = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(json))));//JsonParser.parseString(json);
            if (studyModuleTree.isJsonObject() || !studyModuleTree.isJsonNull()){
                moduleJson = studyModuleTree.getAsJsonObject();
                this.id = studyModulegroupID;
                setup();
            } else {
                System.out.format("Error reading the studyModule json %s: ", studyModulegroupID);
            }
        } catch (JsonParseException | IOException e){
            System.out.format("Error reading the studyModule json %s: ", studyModulegroupID);
            System.out.println(e);
        }
        */
    }

    /**
     * @hidden
     */
    private void setup(){
        this.id = "No Id";
        this.groupId = "No groupId";
        this.targetCredits = "No target credits";
        this.moduleType = "No module type";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
        setId();
        setGroupId();
        setName();
        setRules();
        setTargetCredits();
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

    /**
     * @hidden
     */
    private void setTargetCredits(){
        try {
            JsonElement tempType = moduleJson.get("type");
            //System.out.println("TEST " + tempType.getAsString().equals("StudyModule"));
            if (tempType == null || !tempType.isJsonPrimitive() || !tempType.getAsString().equals("StudyModule")) {
                this.moduleType = "GroupingModule";
                return;
            }
        } catch (ClassCastException | IllegalStateException e) {
            System.out.println("Error with getting module type: " + this.id + " : " + e);
        }
        this.moduleType = "StudyModule";
        JsonElement targetCreditsElement = moduleJson.get("targetCredits");
        if (targetCreditsElement == null || !targetCreditsElement.isJsonObject()){
            System.out.println("error with studyModule targetCredits: " + this.id);
            return;    
        } else {
            try {
                String min = targetCreditsElement.getAsJsonObject().get("min").getAsString();
                if (targetCreditsElement.getAsJsonObject().get("max").isJsonNull()) {
                    this.targetCredits = min + "-"; 
                } else {
                    String max = targetCreditsElement.getAsJsonObject().get("max").getAsString();
                    this.targetCredits = min + "-" + max; 
                }
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with studyModule targetCredits: " + this.id + " : " + e);
            }
        }
    }

    /** 
     * @hidden
     */
    private void setName(){
        JsonElement nameElement = moduleJson.get("name");
        if (nameElement == null || !nameElement.isJsonObject()){
            System.out.println("error with studyModule name: " + this.id);
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
                System.out.println("Error with studyModule name: " + this.id + " : " + e);
            }
        }
    }

    /** 
     * @hidden
     */
    private void setRules(){
        JsonElement rule = moduleJson.get("rule");
        if (rule == null || !rule.isJsonObject()){
            System.out.println("error with module rules: " + this.id);
            return;
        } else {
            try {
                setRuleHelper(rule);
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with module rules: " + this.id + " : " + e);
            }
        }
    }
    
    private void setRuleHelper(JsonElement element) {
        try{
            if (element.isJsonObject()) {
                JsonElement whenRule = element.getAsJsonObject().get("rule");
                JsonElement whenRules = element.getAsJsonObject().get("rules");
                JsonElement ifCourse = element.getAsJsonObject().get("courseUnitGroupId");
                JsonElement ifModule = element.getAsJsonObject().get("moduleGroupId");
                if (whenRule != null) {
                    setRuleHelper(whenRule);
                } else if (whenRules != null) {
                    setRuleHelper(whenRules);
                } else if (ifCourse != null) {
                    String groupId = ifCourse.getAsString();
                    networkHandler handler = new networkHandler();
                    CourseData temp = new CourseData(handler.getCourseByGroupId(groupId));
                    this.whenSubModuleAreCourses.put(groupId, temp);
                } else if (ifModule != null) {
                    String groupId = ifModule.getAsString();
                    networkHandler handler = new networkHandler();
                    ModuleData temp = new ModuleData(handler.getModuleByGroupId(groupId));
                    this.whenSubmoduleAreModules.put(groupId, temp);
                }
            } else if (element.isJsonArray()) {
                JsonArray whenRules = element.getAsJsonArray();
                for (var arrayElement : whenRules) {
                    setRuleHelper(arrayElement);
                }
            }
        } catch(ClassCastException | IllegalStateException e) {
            System.out.println("Error with module rules: " + this.id + " : " + e);
        }
    }

    /** 
     * Return module's id
     * @return String id
     */
    public String getId() {
        return this.id;
    }

    public String getGroupId() {
        return this.groupId;
    }

    /** 
     * Return module's target credits in format of min-max, if no max then only in min-
     * @return String Target credits 
     */
    public String getTargetCredits() {
        if (this.moduleType.equals("StudyModule")) {
            return this.targetCredits;
        }
        else {
            return "";
        }
    }
    
    /** 
     * Return module's name where key = language (en, fi), value = name
     * @return TreeMap<String, String> name
     */
    public TreeMap<String, String> getName() {
        return this.name;
    }

    
    /** 
     * Return module's Courses. 
     * This method leaves it to the user to check if submodules are courses. 
     * key = course id, value = courseData object.
     * @return TreeMap<String, courseData> submodules when they are courses
     */
    public TreeMap<String, CourseData> getWhenSubModuleAreCourses() {
        return this.whenSubModuleAreCourses;
    }
    
    /** 
     * Return's module's submodules when they are studyModules or groupingModules. 
     * This method leaves it to the user to check if submodules are studyModules or groupingModules.
     * key = module id (can be studyModule or groupingModule), value = moduleData object
     * @return TreeMap<String, courseData> submoduels when they are studyModules or groupingModules.
     */
    public TreeMap<String, ModuleData> getWhenSubModuleAreModules() {
        return this.whenSubmoduleAreModules;
    }

    /**
     * Return this module's type, ether studyModule or groupingModule.
     * @return String moduleType
     */
    public String getModuleType() {
        return this.moduleType;
    }

    /**
     * Overridden toString method
     * @return String module in readable mode.
     */
    @Override
    public String toString(){
        return String.format("%s : %s : %s", this.moduleType, this.groupId, this.name.get("fi"));
    }

    /*
    public static void main(String[] args) {
        moduleData testGroup = new moduleData("otm-6b575bfa-e488-4ee0-a8d9-877608ce64e9.json", "otm-6b575bfa-e488-4ee0-a8d9-877608ce64e9");
        System.out.println(testGroup.getGroupId());
        System.out.println(testGroup.getName().get("en"));
        System.out.println(testGroup.getName().get("fi"));
        System.out.println(testGroup.getWhenSubModuleAreCourses());
        System.out.println(testGroup.getWhenSubModuleAreGroupingModules());
        System.out.println(testGroup.getModuleType());
    
        System.out.println();
        moduleData testStudy = new moduleData("otm-010acb27-0e5a-47d1-89dc-0f19a43a5dca.json", "otm-010acb27-0e5a-47d1-89dc-0f19a43a5dca");
        System.out.println(testStudy.getGroupId());
        System.out.println(testStudy.getName());
        System.out.println(testStudy.getWhenSubModuleAreCourses());
        System.out.println(testStudy.getWhenSubModuleAreGroupingModules());
        System.out.println(testStudy.getModuleType());
        System.out.println(testStudy.getTargetCredits());
    }
    */
}