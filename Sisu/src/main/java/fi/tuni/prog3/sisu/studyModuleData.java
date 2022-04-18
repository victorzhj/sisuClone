package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class studyModuleData{
    private JsonObject studyModuleJson;
    private String groupId;
    // Will only have two values. First one is the lower bound 
    // and second one is the upper bound
    private ArrayList<String> targetCredits;
    // key = language, value = description
    private TreeMap<String, String> name;
    // key = course name, value = courseData
    private TreeMap<String, courseData> rulesWhenCourses;
    // key = groupingModule id, value = groupingModuleData
    private TreeMap<String, groupingModuleData> rulesWhenGroupingModule;

    studyModuleData(String json){
        JsonElement courseTree = JsonParser.parseString(json);
        if (courseTree.isJsonObject()){
            studyModuleJson = courseTree.getAsJsonObject();
        }
    }
}