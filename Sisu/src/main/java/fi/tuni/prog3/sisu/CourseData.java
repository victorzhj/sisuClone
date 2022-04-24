package fi.tuni.prog3.sisu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CourseData {
    private JsonObject courseInObject;
    private String code;
    private String credits;
    private String groupId;

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
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file
     */
    public CourseData(String[] data) {
        createMapInstances();
        /*
        try{
            JsonElement courseTree = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(json))));
            if (courseTree.isJsonArray()){
                courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
                setup();
            } else {
                System.out.println("not array");
            }
        } catch (IOException e){
            System.out.println("test " + e);
        }
        */
        try{
        JsonElement courseTree = JsonParser.parseString(data[0]);
        // check if the json is in correct format
            if (courseTree.isJsonArray()){
                courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
                this.groupId = data[1];
                setup();
            } else if (courseTree.isJsonObject()){
                courseInObject = courseTree.getAsJsonObject();
                this.groupId = data[1];
                setup();
            }
        } catch (IllegalStateException e) {
            System.out.format("Error reading the course json %s: ", groupId);
        }
    }

    /**
     * @hidden
     */
    private void setup(){
        this.credits = "0";
        this.code = "No code";
        this.name.put("en", "No name");
        this.name.put("fi", "Ei nimeä");
        this.outcomes.put("en", "No outcomes text");
        this.outcomes.put("fi", "Ei tulos tekstiä");
        this.additional.put("en", "No additional text");
        this.additional.put("fi", "Ei lisättävää tekstiä");
        this.content.put("en", "No content text");
        this.content.put("fi", "Ei sisältö tekstiä");
        
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
            if (nameEn != null) {
                this.name.put("en", nameEn.getAsString());
            }
            if (nameFi != null) {
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
     * @return TreeMap<String, String> Key = Language(en or fi), value = name. Returns the course name. 
     */
    public TreeMap<String, String> getName() {
        return name;
    }

    /** 
     * @return String Returns the course code.
     */
    public String getCode() {
        return code;
    }

    
    /** 
     * @return String Returns the amount of credits you can acquire from the course. Format might be e.g 2-3 or 5
     */
    public String getCredits() {
        return credits;
    }

    
    /** 
     * @return String Returns the course groupId
     */
    public String getGroupId() {
        return groupId;
    }

    
    /** 
     * @return TreeMap<String, String> Key = Language(en or fi), value = outcome text. Returns the course outcome text.
     */
    public TreeMap<String, String> getOutcomes() {
        return outcomes;
    }


    /**
     * @return TreeMap<String, String> Key = Language(en or fi), value = content text. Returns the course content text.
     */
    public TreeMap<String, String> getContent() {
        return content;
    }

    /**
     * @return TreeMap<String, String> Key = Language(en or fi), value = additional text. Returns the course additional text.
     */
    public TreeMap<String, String> getAdditional() {
        return additional;
    }

    /**
     * @return Object in String format
     */
    @Override
    public String toString(){
        return String.format("%s : %s : %s", this.code, this.groupId, this.name.get("fi"));
    }
}