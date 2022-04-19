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

public class courseData {
    private JsonObject courseInObject;
    private String code;
    private String credits;
    private String groupId;

    // key = language, value = description
    private TreeMap<String, String> name;
    // key = language, value = description
    private TreeMap<String, String> description;
    // MAYBE key = course name, value = substitution course
    private TreeMap<String, courseData> substitution;
    // key = language, value = description
    private TreeMap<String, String> evaluationCriteria;
    // key = language, value = description
    private TreeMap<String, String> outcomes;
    // key = language, value = description
    private TreeMap<String, String> content;
    // key = language, value = description
    private TreeMap<String, String> additional;

    /**
     * Constructor that takes a json file and parses it
     * @param fileName Json file name
     * @throws IOException
     * @throws JsonSyntaxException
     */
    courseData(String json) {
        createMapInstances();
        /*
        try{
            JsonElement courseTree = JsonParser.parseString(new String(Files.readAllBytes(Paths.get(json))));
            if (courseTree.isJsonArray()){
                courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
                setName();
                setCode();
                setCredits();
                setGroupID();
                setDescription();
                setEvaluationCriteria();
                setOutcomes();
                setAdditional();
                setContent();
            } else {
                System.out.println("not array");
            }
        } catch (IOException e){
            System.out.println("test " + e);
        }
        */

        JsonElement courseTree = JsonParser.parseString(json);
        // check if the json is in correct format
        if (courseTree.isJsonArray()){
            courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
            setup();
        }
        
    }

    
    private void setup(){
        setName();
        setCode();
        setCredits();
        setGroupID();
        setDescription();
        setEvaluationCriteria();
        setOutcomes();
        setAdditional();
        setContent();
    }

    private void createMapInstances(){
        this.name = new TreeMap<>();
        this.description = new TreeMap<>();
        this.substitution = new TreeMap<>();
        this.evaluationCriteria = new TreeMap<>();
        this.outcomes = new TreeMap<>();
        this.content = new TreeMap<>();
        this.additional = new TreeMap<>();
    }

    private void setName(){
        JsonElement name = courseInObject.get("name");
        if (!name.isJsonObject()){
            System.out.println("No name1");
            return;
        }

        JsonElement nameEn = name.getAsJsonObject().get("en");
        JsonElement nameFi = name.getAsJsonObject().get("fi");
        
        if (!nameEn.isJsonPrimitive() || !nameFi.isJsonPrimitive()){
            System.out.println("No name2");
            return;
        }

        this.name.put("en", nameEn.getAsString());
        this.name.put("fi", nameFi.getAsString());
    }

    private void setCode(){
        if (courseInObject.get("code").isJsonNull()){
            this.code =  "";
        }
        JsonElement test = courseInObject.get("code");
        this.code = test.getAsString();
    }

    private void setCredits(){
        JsonElement creditsAmount = courseInObject.get("credits");
        String minAmount = creditsAmount.getAsJsonObject().get("min").getAsString();
        String maxAmount = creditsAmount.getAsJsonObject().get("max").getAsString();
        if (minAmount.equals(maxAmount)){
            this.credits = minAmount;
        } else {
            this.credits = minAmount + "-" + maxAmount;
        }
    }

    private void setGroupID(){
        if (courseInObject.get("groupId").isJsonNull()){
            this.groupId =  "";
        }
        this.groupId = courseInObject.get("groupId").getAsString();
    }

    private void setDescription(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("No description1");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonArray()){
            System.out.println("No description2");
            return;
        }

        JsonElement completionMethotsFirst = completionMethods.getAsJsonArray().get(0);
        if (!completionMethotsFirst.isJsonObject()){
            System.out.println("No description3");
            return;
        }

        JsonElement description = completionMethotsFirst.getAsJsonObject().get("description");
        if (!description.isJsonObject()){
            System.out.println("No description4");
            return;
        }

        JsonElement descriptionEn = description.getAsJsonObject().get("en");
        if (!descriptionEn.isJsonPrimitive()){
            System.out.println("No description4");
            return;
        }

        JsonElement descriptionFi = description.getAsJsonObject().get("fi");
        if (!descriptionFi.isJsonPrimitive()){
            System.out.println("No description5");
            return;
        }

        this.description.put("en", descriptionEn.getAsString());
        this.description.put("fi", descriptionFi.getAsString());
    }

    private void setEvaluationCriteria(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("No EvaluationCriteria1");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonArray()){
            System.out.println("No EvaluationCriteria2");
            return;
        }

        JsonElement evaluationCriteriaFirst = completionMethods.getAsJsonArray().get(0);
        if (!evaluationCriteriaFirst.isJsonObject()){
            System.out.println("No EvaluationCriteria3");
            return;
        }

        JsonElement evaluationCriteria = evaluationCriteriaFirst.getAsJsonObject().get("description");
        if (!evaluationCriteria.isJsonObject()){
            System.out.println("No description4");
            return;
        }

        JsonElement evaluationCriteriaEn = evaluationCriteria.getAsJsonObject().get("en");
        if (!evaluationCriteriaEn.isJsonPrimitive()){
            System.out.println("No EvaluationCriteria4");
            return;
        }

        JsonElement evaluationCriteriaFi = evaluationCriteria.getAsJsonObject().get("fi");
        if (!evaluationCriteriaFi.isJsonPrimitive()){
            System.out.println("No EvaluationCriteria5");
            return;
        }

        this.evaluationCriteria.put("en", evaluationCriteriaEn.getAsString());
        this.evaluationCriteria.put("fi", evaluationCriteriaFi.getAsString());
    }

    private void setOutcomes(){
        JsonElement outcomes = courseInObject.get("outcomes");
        if (!outcomes.isJsonObject()){
            System.out.println("No outcomes1");
            return;
        }

        JsonElement outcomeEn = outcomes.getAsJsonObject().get("en");
        JsonElement outcomeFi = outcomes.getAsJsonObject().get("fi");
        if (!outcomeEn.isJsonPrimitive() || !outcomeFi.isJsonPrimitive()){
            System.out.println("No outcomes2");
            return;
        }

        this.outcomes.put("en", outcomeEn.getAsString());
        this.outcomes.put("fi", outcomeFi.getAsString());
    }

    private void setContent(){
        JsonElement content = courseInObject.get("content");
        if (!content.isJsonObject()){
            System.out.println("No content3");
            return;
        }

        JsonElement contentEn = content.getAsJsonObject().get("en");
        JsonElement contentFi = content.getAsJsonObject().get("fi");
        if (!contentEn.isJsonPrimitive() || !contentFi.isJsonPrimitive()){
            System.out.println("No content4");
            return;
        }

        this.content.put("en", contentEn.getAsString());
        this.content.put("fi", contentFi.getAsString());
    }

    private void setAdditional(){
        JsonElement additional = courseInObject.get("additional");
        if (!additional.isJsonObject()){
            System.out.println("No additional");
            return;
        }

        JsonElement additionalEn = additional.getAsJsonObject().get("en");
        JsonElement additionalFi = additional.getAsJsonObject().get("fi");
        if (!additionalEn.isJsonPrimitive() || !additionalFi.isJsonPrimitive()){
            System.out.println("No additional");
            return;
        }

        this.additional.put("en", additionalEn.getAsString());
        this.additional.put("fi", additionalFi.getAsString());
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
}