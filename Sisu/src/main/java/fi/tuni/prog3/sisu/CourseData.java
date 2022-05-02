package fi.tuni.prog3.sisu;

import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.scene.control.TreeItem;

/**
 * A class representing a Tampere university course. 
 */
public class CourseData {
    private JsonObject courseInObject;
    private String code;
    private String credits;
    private String groupId;
    private boolean completed;
    private TreeItem<treeItems> branch;

    // key = language, value = name
    private TreeMap<String, String> name;
    // key = language, value = outcome
    private TreeMap<String, String> outcomes;
    // key = language, value = content
    private TreeMap<String, String> content;
    // key = language, value = additional
    private TreeMap<String, String> additional;

    /**
     * Constructor that takes a string array where first value is json in String formation and second value is the groupId. 
     * Constructs a CourseData object that stores the data of one course.
     * @param data[] String array where first element is the json in string format and the second value is groupId.
     * @throws IllegalStateException When there is error reading the file.
     */
    public CourseData(String[] data) {
        createMapInstances();
        setUpVariables();
        try{
            JsonElement courseTree = JsonParser.parseString(data[0]);
            // check if the json is in correct format
            if (courseTree.isJsonArray() && !courseTree.getAsJsonArray().isEmpty()){
                courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
                this.groupId = data[1];
                setupMethods();
            } else if (courseTree.isJsonObject()){
                courseInObject = courseTree.getAsJsonObject();
                if (courseInObject.get("name") != null) {
                    this.groupId = data[1];
                    setupMethods();
                }
            }
        } catch (IllegalStateException e) {
            System.out.format("Error reading the course json %s: ", groupId);
        }
    }

    /**
     * @hidden
     */
    private void setUpVariables() {
        this.credits = "0";
        this.code = "No code";
        this.name.put("en", "No name");
        this.name.put("fi", "No name");
        this.outcomes.put("en", "No outcomes text");
        this.outcomes.put("fi", "No outcomes text");
        this.additional.put("en", "No additional text");
        this.additional.put("fi", "No additional text");
        this.content.put("en", "No content text");
        this.content.put("fi", "No content text");
        this.completed = false;
    }

    /**
     * @hidden
     */
    private void setupMethods(){
        setName();
        setCode();
        setCredits();
        setOutcomes();
        setAdditional();
        setContent();
    }

    /**
     * @hidden
     */
    private void createMapInstances(){
        this.name = new TreeMap<>();
        this.outcomes = new TreeMap<>();
        this.content = new TreeMap<>();
        this.additional = new TreeMap<>();
    }

    /**
     * @hidden
     */
    private void setName(){
        JsonElement name = courseInObject.get("name");
        if (name == null || !name.isJsonObject()){
            return;
        }
        try {
            JsonElement nameEn = name.getAsJsonObject().get("en");
            JsonElement nameFi = name.getAsJsonObject().get("fi");
            if (nameEn != null && !nameEn.isJsonNull()) {
                this.name.put("en", nameEn.getAsString());
            }
            if (nameFi != null && !nameFi.isJsonNull()) {
                this.name.put("fi", nameFi.getAsString());
            }
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course name: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setCode(){
        JsonElement codeElement = courseInObject.get("code");
        if (codeElement == null || !codeElement.isJsonPrimitive()){
            return;
        } else {
            try{
                this.code = courseInObject.get("code").getAsString();
            } catch (IllegalStateException | ClassCastException e) {
                System.out.println("Error with course code: " + this.groupId + " : " + e);
            }
        }
    }

    /**
     * @hidden
     */
    private void setCredits(){
        JsonElement creditsAmount = courseInObject.get("credits");
        if (creditsAmount == null || !creditsAmount.isJsonObject()) {
            return;
        } else {
            try {
                JsonElement minCredits = creditsAmount.getAsJsonObject().get("min");
                JsonElement maxCredits = creditsAmount.getAsJsonObject().get("max");
                if (minCredits.isJsonNull()) {
                    return;
                }
                if (!maxCredits.isJsonNull()) {
                    if (minCredits.getAsString().equals(maxCredits.getAsString())){
                        this.credits = minCredits.getAsString();
                    } else {
                        this.credits = minCredits.getAsString() + "-" + maxCredits.getAsString();
                    }
                } else {
                    this.credits = minCredits.getAsString();
                }
            } catch (IllegalStateException | ClassCastException e) {
                System.out.println("Error with course code: " + this.groupId + " : " + e);
            }
        }
    }


    /**
     * @hidden
     */
    private void setOutcomes(){
        JsonElement outcomes = courseInObject.get("outcomes");
        if (outcomes == null || !outcomes.isJsonObject()){
            return;
        }
        try {
            JsonElement outcomeEn = outcomes.getAsJsonObject().get("en");
            JsonElement outcomeFi = outcomes.getAsJsonObject().get("fi");
            if (outcomeEn != null) {
                this.outcomes.put("en", outcomeEn.getAsString());
            }
            if (outcomeFi != null) {
                this.outcomes.put("fi", outcomeFi.getAsString());
            }
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course outcomes: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setContent(){
        JsonElement content = courseInObject.get("content");
        if (content == null || !content.isJsonObject()){
            return;
        }
        try {
            JsonElement contentEn = content.getAsJsonObject().get("en");
            JsonElement contentFi = content.getAsJsonObject().get("fi");
            if (contentEn != null) {
                this.content.put("en", contentEn.getAsString());
            }
            if (contentFi != null) {
                this.content.put("fi", contentFi.getAsString());
            }
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course content: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setAdditional(){
        JsonElement additional = courseInObject.get("additional");
        if (additional == null || !additional.isJsonObject()){
            return;
        }
        try {
            JsonElement additionalEn = additional.getAsJsonObject().get("en");
            JsonElement additionalFi = additional.getAsJsonObject().get("fi");
            if (additionalEn != null) {
                this.additional.put("en", additionalEn.getAsString());
            }
            if (additionalFi != null) {
                this.additional.put("fi", additionalFi.getAsString());
            }
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course additional: " + this.groupId + " : " + e);
        }
    }

    /**
     * Method is used to set the course as completed if it is.
     * @param completed boolean value of if completed. True if completed and False if not.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Method used to check if the course has been complited.
     * @return Return True if completed and False if hasn't been complited.
     */
    public boolean getComplited() {
        return this.completed;
    }

    /**
     * Method used to store the TreeItem that represents this course.
     * @param branch The TreeItem that represents this course.
     */
    public void setTreeItem(TreeItem<treeItems> branch) {
        this.branch = branch;
    }

    /**
     * Methods returns the TreeItem that represents this course.
     * @return TreeItem<treeItems> The TreeItem that represents this course. Returns null if no TreeItem
     */
    public TreeItem<treeItems> getTreeItem() {
        return this.branch;
    }

    /** 
     * Return a treeMap containing the course name in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = name.
     * @return TreeMap<String, String> Course name in finnish and english. Returns "No name" if there are no name.
     */
    public TreeMap<String, String> getName() {
        return name;
    }

    /** 
     * Returns course's code
     * @return String The course code. Returns "No code" if no code.
     */
    public String getCode() {
        return code;
    }

    
    /** 
     * Returns the amount of credits you can get from the course. 
     * If the credits have lowerbound and upperbound the format is going to be "lowebound-upperbound" e.g "2-3".
     * Else the format is going to be "credits amount", e.g "5"
     * @return String The amount of credits you can acquire from the course. Retruns "0" if no credits.
     */
    public String getCredits() {
        return credits;
    }

    
    /** 
     * Returns the groupId of the course.
     * @return String The course groupId.
     */
    public String getGroupId() {
        return groupId;
    }

    
    /** 
     * Return a treeMap containing the course outcome texts in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = outcome text
     * @return TreeMap<String, String> Course outcome texts in finnish and english. Returns "No outcomes text" if no outcomes.
     */
    public TreeMap<String, String> getOutcomes() {
        return outcomes;
    }


    /**
     * Return a treeMap containing the course content texts in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = content text
     * @return TreeMap<String, String> containing the content texts. Returns "No content text" if no outcomes.
     */
    public TreeMap<String, String> getContent() {
        return content;
    }

    /**
     * Return a treeMap containing the course additional texts in finnish and english. The only key values are "en" and "fi".
     * Key = Language(en or fi), value = additional text
     * @return TreeMap<String, String> containing the additional texts. Returns "No additional text" if no outcomes.
     */
    public TreeMap<String, String> getAdditional() {
        return additional;
    }

    /**
     * Returns a string representation of this course.
     * @return String Object in String format.
     */
    @Override
    public String toString(){
        return String.format("%s : %s : %s", this.code, this.groupId, this.name.get("fi"));
    }
}