package fi.tuni.prog3.sisu;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class courseData {
    private JsonObject courseInObject;
    private String name;
    private String code;
    private String credits;
    private String groupId;

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
     */
    courseData(String json) {
        JsonElement courseTree = JsonParser.parseString(json);
        if (courseTree.isJsonArray()){
            courseInObject = courseTree.getAsJsonArray().get(0).getAsJsonObject();
        }
    }

    public void setName(){
        if (courseInObject.get("name").isJsonNull()){
            this.name = "";
        }
        this.name = courseInObject.get("name").getAsString();
    }

    public void setCode(){
        if (courseInObject.get("code").isJsonNull()){
            this.code =  "";
        }
        this.code = courseInObject.get("code").getAsString();
    }

    public void setCredits(){
        JsonElement creditsAmount = courseInObject.get("credits");
        String minAmount = creditsAmount.getAsJsonObject().get("min").getAsString();
        String maxAmount = creditsAmount.getAsJsonObject().get("max").getAsString();
        if (minAmount.equals(maxAmount)){
            this.credits = minAmount;
        } else {
            this.credits = minAmount + "-" + maxAmount;
        }
    }

    public void setGroupID(){
        if (courseInObject.get("groupId").isJsonNull()){
            this.groupId =  "";
        }
        this.groupId = courseInObject.get("groupId").getAsString();
    }

    public void setDescription(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("No description");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement description = completionMethods.getAsJsonObject().get("description");
        if (!completionMethods.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement descriptionEn = description.getAsJsonObject().get("en");
        if (!descriptionEn.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement descriptionFi = description.getAsJsonObject().get("fi");
        if (!descriptionFi.isJsonObject()){
            System.out.println("No description");
            return;
        }

        this.description.put("en", descriptionEn.getAsString());
        this.description.put("fi", descriptionFi.getAsString());
    }

    public void setEvaluationCriteria(){
        if (courseInObject.get("completionMethods").isJsonNull()){
            System.out.println("No description");
            return;
        }
        
        JsonElement completionMethods = courseInObject.get("completionMethods");
        if (!completionMethods.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement evaluationCriteria = completionMethods.getAsJsonObject().get("evaluationCriteria");
        if (!completionMethods.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement evaluationCriteriaEn = evaluationCriteria.getAsJsonObject().get("en");
        if (!evaluationCriteriaEn.isJsonObject()){
            System.out.println("No description");
            return;
        }

        JsonElement evaluationCriteriaFi = evaluationCriteria.getAsJsonObject().get("fi");
        if (!evaluationCriteriaFi.isJsonObject()){
            System.out.println("No description");
            return;
        }

        this.evaluationCriteria.put("en", evaluationCriteriaEn.getAsString());
        this.evaluationCriteria.put("fi", evaluationCriteriaFi.getAsString());
    }
}