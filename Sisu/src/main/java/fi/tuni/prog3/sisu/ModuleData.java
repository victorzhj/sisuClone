package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ModuleData{
    private JsonObject moduleJson;
    private String groupId;
    private String targetCredits;
    private String moduleType;
    // key = language, value = name
    private TreeMap<String, String> name;

    // key = course name, value = courseData
    private TreeMap<String, CourseData> whenSubModuleAreCourses;
    // key = groupingModule id, value = groupingModuleData
    private TreeMap<String, ModuleData> whenSubmoduleAreModules;

    /**
     * Constructor of moduleData class. Construct an module with given json.
     * Can be ether studyModule or groupingModule
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file
     */
    ModuleData(String[] data){
        name = new TreeMap<>();
        whenSubmoduleAreModules = new TreeMap<>();
        
        try {
            JsonElement studyModuleTree = JsonParser.parseString(data[0]);
            if (studyModuleTree.isJsonObject()){
                moduleJson = studyModuleTree.getAsJsonObject();
                this.groupId = data[1];
                setup();
            }
        } catch (JsonParseException e){
            System.out.format("Error reading the studyModule json %s: ", data[1]);
            System.out.println(e);
        }
        
        /*
        try {
            JsonElement studyModuleTree = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(json))));//JsonParser.parseString(json);
            if (studyModuleTree.isJsonObject() || !studyModuleTree.isJsonNull()){
                moduleJson = studyModuleTree.getAsJsonObject();
                this.groupId = studyModulegroupID;
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
        setName();
        setRules();
        setTargetCredits();
    }

    /**
     * @hidden
     */
    private void setTargetCredits(){
        try {
            JsonElement tempType = moduleJson.get("type");
            //System.out.println("TEST " + tempType.getAsString().equals("StudyModule"));
            if (!tempType.isJsonPrimitive() || tempType.isJsonNull() || !tempType.getAsString().equals("StudyModule")) {
                this.moduleType = "GroupingModule";
                return;
            }
        } catch (ClassCastException | IllegalStateException e) {
            System.out.println("Error with getting module type: " + this.groupId + " : " + e);
        }
        this.moduleType = "StudyModule";
        JsonElement targetCreditsElement = moduleJson.get("targetCredits");
        if (!targetCreditsElement.isJsonObject() || targetCreditsElement.isJsonNull()){
            System.out.println("error with studyModule targetCredits: " + this.groupId);
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
                System.out.println("Error with studyModule targetCredits: " + this.groupId + " : " + e);
            }
        }
    }

    /** 
     * @hidden
     */
    private void setName(){
        JsonElement nameElement = moduleJson.get("name");
        if (!nameElement.isJsonObject() || nameElement.isJsonNull()){
            System.out.println("error with studyModule name: " + this.groupId);
            return;
        } else {
            try{
                this.name.put("en", nameElement.getAsJsonObject().get("en").getAsString());
                this.name.put("fi", nameElement.getAsJsonObject().get("fi").getAsString());
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with studyModule name: " + this.groupId + " : " + e);
            }
        }
    }

    /** 
     * @hidden
     */
    private void setRules(){
        JsonElement rule = moduleJson.get("rule");
        if (!rule.isJsonObject() || rule.isJsonNull()){
            System.out.println("error with studyModule rules: " + this.groupId);
            return;
        } else {
            try {
                networkHandler jsonGetter = new networkHandler();
                for (var module : rule.getAsJsonObject().get("rules").getAsJsonArray()){
                    if (module.getAsJsonObject().has("courseUnitGroupId")){
                        String courseGroupId = module.getAsJsonObject().get("courseUnitGroupId").getAsString();
                        CourseData temp = new CourseData(jsonGetter.getCourseByGroupId(courseGroupId));
                        this.whenSubModuleAreCourses.put(temp.getGroupId(), temp);
                    } else {
                        String subModuleGroupId = module.getAsJsonObject().get("moduleGroupId").getAsString();
                        ModuleData temp = new ModuleData(jsonGetter.getModuleByGroupId(subModuleGroupId));
                        this.whenSubmoduleAreModules.put(temp.getGroupId(), temp);
                    }
                }
            } catch (ClassCastException | IllegalStateException e) {
                System.out.println("Error with studyModule rules: " + this.groupId + " : " + e);
            }
        }
    }
    
    /** 
     * Return module's groupId
     * @return String groupId
     */
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
     * key = course groupId, value = courseData object.
     * @return TreeMap<String, courseData> submodules when they are courses
     */
    public TreeMap<String, CourseData> getWhenSubModuleAreCourses() {
        return this.whenSubModuleAreCourses;
    }
    
    /** 
     * Return's module's submodules when they are studyModules or groupingModules. 
     * This method leaves it to the user to check if submodules are studyModules or groupingModules.
     * key = module groupId (can be studyModule or groupingModule), value = moduleData object
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
        return String.format("%s : %s : %s", this.moduleType, this.groupId, this.name);
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