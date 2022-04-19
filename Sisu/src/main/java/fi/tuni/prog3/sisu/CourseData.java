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

    // key = language, value = description
    private TreeMap<String, String> name;
    // key = language, value = description
    private TreeMap<String, String> description;
    // MAYBE key = course name, value = substitution course
    private TreeMap<String, CourseData> substitution;
    // key = language, value = description
    private TreeMap<String, String> evaluationCriteria;
    // key = language, value = description
    private TreeMap<String, String> outcomes;
    // key = language, value = description
    private TreeMap<String, String> content;
    // key = language, value = description
    private TreeMap<String, String> additional;

    /**
     * Constructor that takes a string array where first value is json in String formation and second value is the groupId.
     * @param data[] String array
     * @throws IllegalStateException When there is error reading the file
     */
    CourseData(String[] data) {
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
        
        JsonElement courseTree = JsonParser.parseString(data[0]);
        // check if the json is in correct format
        if (courseTree.isJsonArray()){
            try{
                courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
                setup();
                this.groupId = data[1];
            } catch (IllegalStateException e) {
                System.out.format("Error reading the course json %s: ", groupId);
            }
        }
    }

    /**
     * @hidden
     */
    private void setup(){
        setName();
        setCode();
        setCredits();
        setDescription();
        setEvaluationCriteria();
        setOutcomes();
        setAdditional();
        setContent();
    }

    /**
     * @hidden
     */
    private void createMapInstances(){
        this.name = new TreeMap<>();
        this.description = new TreeMap<>();
        this.substitution = new TreeMap<>();
        this.evaluationCriteria = new TreeMap<>();
        this.outcomes = new TreeMap<>();
        this.content = new TreeMap<>();
        this.additional = new TreeMap<>();
    }

    /**
     * @hidden
     */
    private void setName(){
        JsonElement name = courseInObject.get("name");
        if (!name.isJsonObject()){
            System.out.println("Error with course name: " + this.groupId);
            return;
        }

        try {
            JsonElement nameEn = name.getAsJsonObject().get("en");
            JsonElement nameFi = name.getAsJsonObject().get("fi");
            
            if (!nameEn.isJsonPrimitive() || !nameFi.isJsonPrimitive()){
                System.out.println("Error with course name: " + this.groupId);
                return;
            }

            this.name.put("en", nameEn.getAsString());
            this.name.put("fi", nameFi.getAsString());
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course name: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setCode(){
        if (courseInObject.get("code").isJsonNull()){
            this.code =  "";
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
        if (!creditsAmount.isJsonObject()) {
            System.out.println("Error with course credits: " + this.groupId);
            return;
        } else {
            try {
                String minAmount = creditsAmount.getAsJsonObject().get("min").getAsString();
                String maxAmount = creditsAmount.getAsJsonObject().get("max").getAsString();
                if (minAmount.equals(maxAmount)){
                    this.credits = minAmount;
                } else {
                    this.credits = minAmount + "-" + maxAmount;
                }
            } catch (IllegalStateException | ClassCastException e) {
                System.out.println("Error with course code: " + this.groupId + " : " + e);
            }
        }
    }

    /**
     * @hidden
     */
    private void setDescription(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("Error with course description: " + this.groupId + " : ");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonArray()){
            System.out.println("Error with course code: " + this.groupId + " : ");
            return;
        }

        try {
            JsonElement completionMethotsFirst = completionMethods.getAsJsonArray().get(0);
            if (!completionMethotsFirst.isJsonObject()){
                System.out.println("Error with course description: " + this.groupId + " : ");
                return;
            }

            JsonElement description = completionMethotsFirst.getAsJsonObject().get("description");
            if (!description.isJsonObject()){
                System.out.println("Error with course description: " + this.groupId + " : ");
                return;
            }

            JsonElement descriptionEn = description.getAsJsonObject().get("en");
            if (!descriptionEn.isJsonPrimitive()){
                System.out.println("Error with course description: " + this.groupId + " : ");
                return;
            }

            JsonElement descriptionFi = description.getAsJsonObject().get("fi");
            if (!descriptionFi.isJsonPrimitive()){
                System.out.println("Error with course description: " + this.groupId + " : ");
                return;
            }

            this.description.put("en", descriptionEn.getAsString());
            this.description.put("fi", descriptionFi.getAsString());
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course description: " + this.groupId + " : " + e);
        }

        
    }

    /**
     * @hidden
     */
    private void setEvaluationCriteria(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("Error with course evalutation criteria: " + this.groupId + " : ");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonArray()){
            System.out.println("Error with course description: " + this.groupId);
            return;
        }

        try {
            JsonElement evaluationCriteriaFirst = completionMethods.getAsJsonArray().get(0);
            if (!evaluationCriteriaFirst.isJsonObject()){
                System.out.println("Error with course description: " + this.groupId);
                return;
            }
    
            JsonElement evaluationCriteria = evaluationCriteriaFirst.getAsJsonObject().get("description");
            if (!evaluationCriteria.isJsonObject()){
                System.out.println("Error with course description: " + this.groupId);
                return;
            }
    
            JsonElement evaluationCriteriaEn = evaluationCriteria.getAsJsonObject().get("en");
            if (!evaluationCriteriaEn.isJsonPrimitive()){
                System.out.println("Error with course description: " + this.groupId);
                return;
            }
    
            JsonElement evaluationCriteriaFi = evaluationCriteria.getAsJsonObject().get("fi");
            if (!evaluationCriteriaFi.isJsonPrimitive()){
                System.out.println("Error with course description: " + this.groupId);
                return;
            }
    
            this.evaluationCriteria.put("en", evaluationCriteriaEn.getAsString());
            this.evaluationCriteria.put("fi", evaluationCriteriaFi.getAsString());
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course description: " + this.groupId + " : " + e);
        }
    }

    /**
     * @hidden
     */
    private void setOutcomes(){
        JsonElement outcomes = courseInObject.get("outcomes");
        if (!outcomes.isJsonObject()){
            System.out.println("Error with course outcomes: " + this.groupId);
            return;
        }
        try {
            JsonElement outcomeEn = outcomes.getAsJsonObject().get("en");
            JsonElement outcomeFi = outcomes.getAsJsonObject().get("fi");
            if (!outcomeEn.isJsonPrimitive() || !outcomeFi.isJsonPrimitive()){
                System.out.println("Error with course outcomes: " + this.groupId);
                return;
            }
            this.outcomes.put("en", outcomeEn.getAsString());
            this.outcomes.put("fi", outcomeFi.getAsString());
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course outcomes: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setContent(){
        JsonElement content = courseInObject.get("content");
        if (!content.isJsonObject()){
            System.out.println("Error with course content: " + this.groupId);
            return;
        }
        try {
            JsonElement contentEn = content.getAsJsonObject().get("en");
            JsonElement contentFi = content.getAsJsonObject().get("fi");
            if (!contentEn.isJsonPrimitive() || !contentFi.isJsonPrimitive()){
                System.out.println("Error with course content: " + this.groupId);
                return;
            }
    
            this.content.put("en", contentEn.getAsString());
            this.content.put("fi", contentFi.getAsString());
        } catch (IllegalStateException | ClassCastException e) {
            System.out.println("Error with course content: " + this.groupId + " : " + e);
        }
        
    }

    /**
     * @hidden
     */
    private void setAdditional(){
        JsonElement additional = courseInObject.get("additional");
        if (!additional.isJsonObject()){
            System.out.println("Error with course additional: " + this.groupId);
            return;
        }
        try {
            JsonElement additionalEn = additional.getAsJsonObject().get("en");
            JsonElement additionalFi = additional.getAsJsonObject().get("fi");
            if (!additionalEn.isJsonPrimitive() || !additionalFi.isJsonPrimitive()){
                System.out.println("Error with course additional: " + this.groupId);
                return;
            }
    
            this.additional.put("en", additionalEn.getAsString());
            this.additional.put("fi", additionalFi.getAsString());
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
     * @return TreeMap<String, String> Key = Language(en or fi), value = description. Returns the course description.
     */
    public TreeMap<String, String> getDescription() {
        return description;
    }

    
    /** 
     * @return TreeMap<String, String> Key = Language(en or fi), value = evaluationCriteria. Returns the course evaluation criteria.
     */
    public TreeMap<String, String> getEvaluationCriteria() {
        return evaluationCriteria;
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
        return String.format("%s : %s : %s", this.code, this.groupId, this.name);
    }
}