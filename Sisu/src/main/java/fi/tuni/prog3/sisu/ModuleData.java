package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * A class representing a Tampere University degree's sub modules. Can be quite slow if there are lots of layers.
 * If you just want one layer you should use DegreeProgrammeModules class.
 */

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
    // key = groupid, value = module
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

    /**
     * @hidden
     */
    private void setUpVariables() {
        this.id = "No Id";
        this.groupId = "No groupId";
        this.targetCredits = "No target credits";
        this.moduleType = "No module type";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
    }

    /**
     * @hidden
     */
    private void setupMethods(){
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
                JsonElement minCredits = targetCreditsElement.getAsJsonObject().get("min");
                JsonElement maxCredits = targetCreditsElement.getAsJsonObject().get("max");
                if (minCredits.isJsonNull()) {
                    return;
                }
                if (maxCredits.isJsonNull()) {
                    this.targetCredits = minCredits + "-"; 
                } else {
                    this.targetCredits = minCredits + "-" + maxCredits; 
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
    
    /**
     * @hidden
     * @param element
     */
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
     * Return module's id.
     * @return String The module id.
     */
    public String getId() {
        return this.id;
    }

    /** 
     * Return module's groudId.
     * @return String The module groudId.
     */
    public String getGroupId() {
        return this.groupId;
    }

    /** 
     * Return module's target credits in format of min-max, e.g, "3-5". 
     * if no max then only in min-, e.g "3-".
     * This is only when the module is studyModule type.
     * If it's groupingModule type it will return empty string.
     * This is why it's recommended to first check the module type with getModuleType method.
     * @return String The module's target credits if module is studyModule.
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
     * Return a treeMap containing the modules name in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = name.
     * @return TreeMap<String, String> module name in finnish and english.
     */
    public TreeMap<String, String> getName() {
        return this.name;
    }

    
    /** 
     * Return module's Courses. 
     * This method leaves it to the user to check if submodules are courses. This can be done by checking if the TreeMap is empty.
     * key = course id, value = courseData object.
     * @return TreeMap<String, courseData> module's submodules when they are courses.
     */
    public TreeMap<String, CourseData> getWhenSubModuleAreCourses() {
        return this.whenSubModuleAreCourses;
    }
    
    /** 
     * Return's module's submodules when they are studyModules or groupingModules. 
     * This method leaves it to the user to check if submodules are studyModules or groupingModules. This can be done by checking if the TreeMap is empty.
     * key = module id (can be studyModule or groupingModule), value = moduleData object
     * @return TreeMap<String, courseData> module's submoduels when they are studyModules or groupingModules.
     */
    public TreeMap<String, ModuleData> getWhenSubModuleAreModules() {
        return this.whenSubmoduleAreModules;
    }

    /**
     * Return this module's type, ether studyModule or groupingModule.
     * @return String module's moduleType.
     */
    public String getModuleType() {
        return this.moduleType;
    }

    /**
     * Returns a string representation of this module.
     * @return String Object in String format.
     */
    @Override
    public String toString(){
        return String.format("%s : %s : %s", this.moduleType, this.groupId, this.name.get("fi"));
    }
}
